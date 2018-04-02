package fr.cned.emdsgil.suividevosfrais.activities;

import android.content.Intent;
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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.connexion.ControleAcces;

/**
 * Classe pour gérer l'activity "TransfertActivity"
 */
public class TransfertActivity extends AppCompatActivity {

    // propriétés
    private EditText txtUsername ;
    private EditText txtPassword ;
    private TextView txtStatus ;
    private ControleAcces controleAcces ;

    /**
     * Initialisation de l'activity
     *
     * @param savedInstanceState l'activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert);
        setTitle("GSB : Transfert des données");
        txtUsername = findViewById(R.id.txtUsername) ;
        txtPassword = findViewById(R.id.txtPassword) ;
        txtStatus = findViewById(R.id.txtStatus) ;
        controleAcces = ControleAcces.getInstance(this);
        cmdTransfertTransferer_clic() ;
    }

    /*
    @Override
    protected void onStart()
    {
        super.onStart();
        String msg = "Statut : attente de transfert";
        txtStatus = findViewById(R.id.txtStatus) ;
        txtStatus.setText(msg);
    }
    */

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
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    /**
     * Définitiion des actions sur le menu
     *
     * @param item item du menu
     * @return l'appel à la fonction du même nom de la classe mère
     */
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
                String success = "0";
                String status = "0";
                String username = "" ;
                String mdp = "" ;
                List data = new ArrayList() ;
                try {
                    username = txtUsername.getText().toString() ;
                    mdp = txtPassword.getText().toString();
                }catch(Exception e){}

                // contrôle si tous les champs sont remplis
                if (username.length() == 0 || mdp.length() == 0) {
                    Toast.makeText(TransfertActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    afficheResult(success, status, username, mdp, data) ;
                }
            }
        }) ;
    }

    /**
     * Affiche le résultat de la connexion
     * @param success la nature de la connexion
     * @param status message renvoyé par le serveur
     * @param username usename utilisé et confimé pour la connexion
     * @param mdp mot de passe utilisé et confimé pour la connexion
     * @param data données pour la connexion
     */
    private void afficheResult(String success, String status, String username, String mdp, List data) {
        // envoi des informations au contrôleur pouir créer le profil
        controleAcces.creerProfil(success, status, username, mdp, data);
    }

    /**
     * Récupération des frais (forfaitaires et hors forfait) du mois courant
     */
    public List recupFrais() {
        Calendar cal = Calendar.getInstance() ;
        Integer currentMonth = cal.get(Calendar.MONTH)+1 ;
        Integer currentYear = cal.get(Calendar.YEAR) ;
        Integer key = currentYear*100+currentMonth ;
        ArrayList<FraisHf> lesFraisHF ;
        List listeFrais = new ArrayList() ;
        Log.d("date", "************date = "+key) ;
        //[nbEtape, nbKm, nbNuitee, nbRepas,[]]
        if (Global.listFraisMois.containsKey(key)) {
            // frais forfaitaires
            listeFrais.add(key) ;
            Integer nbEtape = Global.listFraisMois.get(key).getEtape() ;
            listeFrais.add(nbEtape) ;
            Integer nbKm = Global.listFraisMois.get(key).getKm() ;
            listeFrais.add(nbKm) ;
            Integer nbNuitee = Global.listFraisMois.get(key).getNuitee() ;
            listeFrais.add(nbNuitee) ;
            Integer nbRepas = Global.listFraisMois.get(key).getRepas() ;
            listeFrais.add(nbRepas) ;
            lesFraisHF = Global.listFraisMois.get(key).getLesFraisHf() ;
            List listeFraisHF = new ArrayList() ;
            for ( FraisHf unFraisHF : lesFraisHF ) {
                List listeUnFraisHF = new ArrayList() ;
                Integer jourFraisHF = unFraisHF.getJour() ;
                listeUnFraisHF.add(jourFraisHF) ;
                Float montantFraisHF = unFraisHF.getMontant() ;
                listeUnFraisHF.add(montantFraisHF) ;
                String motifFraisHF = unFraisHF.getMotif() ;
                listeUnFraisHF.add(motifFraisHF) ;
                listeFraisHF.add(listeUnFraisHF) ;
            }
            //[mois, nbEtape, nbKm, nbNuitee, nbRepas, [[jourFraisHF, montantFraisHF, motifFraisHF], [jourFraisHF, montantFraisHF, motifFraisHF]]]
            Log.d("listeFraisHF", "************listeFraisHF = "+new JSONArray(listeFraisHF)) ;
            Log.d("fraisHF", "************fraisHF = "+new JSONArray(Global.listFraisMois.get(key).getLesFraisHf())) ;
            listeFrais.add(listeFraisHF) ;
        }
        Log.d("listeFrais", "************listeFrais = "+new JSONArray(listeFrais)) ;
        return listeFrais ;
    }


    /**
     * Récupération d'un profil de connexion
     */
    public void recupProfil() {
        Log.d("controleAcces.getSuccess()", "************controleAcces.getSuccess() = "+controleAcces.getSuccess());
        Log.d("controleAcces.getUsername()", "************controleAcces.getUsername() = "+controleAcces.getUsername());
        Log.d("controleAcces.getMdp()", "************controleAcces.getMdp() = "+controleAcces.getMdp());
        Log.d("controleAcces.getStatus()", "************controleAcces.getStatus() = "+controleAcces.getStatus());
        //txtStatus = findViewById(R.id.txtStatus) ;
        if(controleAcces.getSuccess().toString().equals("1")) {
            String msg = "Statut : "+controleAcces.getStatus();
            txtStatus.setText(msg);
            String success = "2";
            String status = "0";
            String username = controleAcces.getUsername() ;
            String mdp = controleAcces.getMdp() ;
            List data =  recupFrais() ;
            afficheResult(success, status, username, mdp, data) ;
        } else {

            String msg = "Statut : "+controleAcces.getStatus();
            Log.d("test final", "************ Test Final = "+msg);
            //txtStatus = findViewById(R.id.txtStatus) ;
            txtStatus.setText(msg);
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
