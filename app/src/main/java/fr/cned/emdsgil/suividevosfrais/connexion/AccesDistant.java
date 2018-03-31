package fr.cned.emdsgil.suividevosfrais.connexion;

import android.util.Log;

import fr.cned.emdsgil.suividevosfrais.outils.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by emds on 12/01/2017.
 */

public class AccesDistant implements AsyncResponse {

    // constante
    private static final String SERVERADDR = "http://192.168.1.40/Suividevosfrais/serveurfrais.php";
    private ControleAcces controle ;

    /**
     * Constructeur
     */

    public AccesDistant(){
        //super();
        controle = ControleAcces.getInstance(null);
    }


    /**
     * Traitement des informations qui viennent du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        // contenu du retour du serveur, pour contrôle dans la console
        Log.d("serveur", "************" + output) ;
        // découpage du message reçu
        String[] message = output.split("%") ;
        // contrôle si le serveur a retourné une information
        //Log.d("msg", "************" + message[1]);

        if(message.length>1){
            //if(message[0].equals("enreg") || message[0].equals("connexion")){

                // retour suite à un enregistrement distant d'un profil
                Log.d("retour", "************status="+message[1]) ;
                try {
                    JSONObject info = new JSONObject(message[1]);
                    // récupération de chaque information du profil
                    String success = info.getString("success");
                    String status = info.getString("status");
                    String username = info.getString("username");
                    String mdp = info.getString("mdp");
                    List data = new ArrayList();

                    Profil profil = new Profil(success, status, username, mdp, data);
                    // enregistrement du profil dans le controle
                    controle.setProfil(profil);

                } catch (JSONException e) {

                    e.printStackTrace() ;

                }


            /*
            } else if(message[0].equals("Erreur !")){
                // retour suite à une erreur
                Log.d("retour", "************erreur="+message[1]);

            }*/
        }

    }

    /**
     * Envoi d'informations vers le serveur distant
     * @param operation
     * @param lesDonneesJSON
     */
    public void envoi(String operation, JSONArray lesDonneesJSON){

        Log.d("opération : ", "************"+operation);
        Log.d("Json : ", "************"+lesDonneesJSON.toString());

        AccesHTTP accesDonnees = new AccesHTTP();
        // permet de faire le lien asynchrone avec AccesHTTP
        accesDonnees.delegate = this;
        // paramètres POST pour l'envoi vers le serveur distant
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        // appel du serveur


        accesDonnees.execute(SERVERADDR);
    }
}