package fr.cned.emdsgil.suividevosfrais.vue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;
import fr.cned.emdsgil.suividevosfrais.controleur.Controle;



// documentation technique pdf TP coach p29
// tests unitaires pdf TP coach p25

public class TransfertActivity extends AppCompatActivity {


    // propriétés
    private EditText txtUsername ;
    private EditText txtPassword ;
    private TextView txtStatus ;
    private Controle controle ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert);
        setTitle("GSB : Transfert des données");

        txtUsername = (EditText) findViewById(R.id.txtUsername) ;
        txtPassword = (EditText) findViewById(R.id.txtPassword) ;
        txtStatus = (TextView) findViewById(R.id.txtStatus) ;
        controle = Controle.getInstance(this);

        cmdTransfertTransferer_clic() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale() ;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste et sérialisation
     */
    private void cmdTransfertTransferer_clic() {

        findViewById(R.id.cmdTransfertTransferer).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();

                String success = "1";
                String status = "";
                String username = "" ;
                String mdp = "" ;

                try {
                    username = txtUsername.getText().toString() ;
                    mdp = txtPassword.getText().toString();
                }catch(Exception e){}

                // contrôle si tous les champs sont remplis
                if (username.length() == 0 || mdp.length() == 0) {
                    Toast.makeText(TransfertActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    afficheResult(success, status, username, mdp) ;
                }

            }
        }) ;
    }


    /**
     * Affiche le résultat des mesures (image et img)
     * @param success
     * @param status
     * @param username
     * @param mdp
     */
    private void afficheResult(String success, String status, String username, String mdp) {
        // envoi des informations au contrôleur pouir créer le profil
        controle.creerProfil(success, status, username, mdp);
    }

    /**
     * Récupération d'un profil sérialisé
     */
    public void recupProfil() {
        if(controle.getSuccess() == "1") {
            txtUsername.setText(""+ controle.getUsername());
            txtPassword.setText(""+controle.getMdp());
            txtStatus.setText("Statut : "+controle.getStatus());
        } else {
             txtStatus.setText("Statut : "+controle.getStatus());
        }
    }





    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgTransfertReturn).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }


    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(TransfertActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }
}
