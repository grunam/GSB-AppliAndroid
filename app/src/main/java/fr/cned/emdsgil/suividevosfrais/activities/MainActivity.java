package fr.cned.emdsgil.suividevosfrais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;

/**
 * Classe pour gérer l'activity "MainActivity"
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Initialisation de l'activity
     *
     * @param savedInstanceState l'activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("GSB : Suivi des frais");
        // récupération des informations sérialisées
        recupSerialize();
        // chargement des méthodes événementielles
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdKm)), KmActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdRepas)), RepasActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdNuitee)), NuiteeActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdEtape)), EtapeActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHf)), HfActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHfRecap)), HfRecapActivity.class);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdTransfert)), TransfertActivity.class);
        //cmdTransfert_clic();
    }

    /**
     * Création du menu avec des items
     *
     * @param menu objet menu à remplir
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Récupère la sérialisation si elle existe
     */
    private void recupSerialize() {
        /* Pour éviter le warning "Unchecked cast from Object to Hash" produit par un casting direct :
         * Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MainActivity.this);
         * On créé un Hashtable générique <?,?> dans lequel on récupère l'Object retourné par la méthode deSerialize, puis
         * on cast chaque valeur dans le type attendu.
         * Seulement ensuite on affecte cet Hashtable à Global.listFraisMois.
        */
        Hashtable<?, ?> monHash = (Hashtable<?, ?>) Serializer.deSerialize(MainActivity.this);
        if (monHash != null) {
            Hashtable<Integer, FraisMois> monHashCast = new Hashtable<>();
            for (Hashtable.Entry<?, ?> entry : monHash.entrySet()) {
                monHashCast.put((Integer) entry.getKey(), (FraisMois) entry.getValue());
            }
            Global.listFraisMois = monHashCast;

            //System.out.println("*************** listFraisMois : "+Global.listFraisMois);
            //Log.d("listFraisMois : ", "************" + new JSONArray(Global.listFraisMois));

        }
        // si rien n'a été récupéré, il faut créer la liste
        if (Global.listFraisMois == null) {
            Global.listFraisMois = new Hashtable<>();
            /* Retrait du type de l'HashTable (Optimisation Android Studio)
			 * Original : Typage explicit =
			 * Global.listFraisMois = new Hashtable<Integer, FraisMois>();
			*/

        }
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(ImageButton button, final Class classe) {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // ouvre l'activité
                Intent intent = new Intent(MainActivity.this, classe);
                startActivity(intent);
            }
        });
    }

}
