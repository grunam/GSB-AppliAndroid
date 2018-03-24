package fr.cned.emdsgil.suividevosfrais.modele;

import android.util.Log;

import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.outils.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by emds on 12/01/2017.
 */

public class AccesDistant implements AsyncResponse {

    // constante
    private static final String SERVERADDR = "http://192.168.1.37/Suividevosfrais/serveurfrais.php";
    private Controle controle ;

    /**
     * Constructeur
     */
    public AccesDistant(){
        //super();
        controle = Controle.getInstance(null);
    }


    /**
     * Traitement des informations qui viennent du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        // contenu du retour du serveur, pour contrôle dans la console
        Log.d("serveur", "************" + output);
        // découpage du message reçu
        String[] message = output.split("%");
        // contrôle si le serveur a retourné une information
        //Log.d("msg", "************" + message[1]);

        if(message.length>1){
            if(message[0].equals("enreg")){
                // retour suite à un enregistrement distant d'un profil
                Log.d("retour", "************enreg="+message[1]);
            }else if(message[0].equals("connexion")){

                // retour suite à la récupération du dernier profil
                //Log.d("retour", "************dernier="+message[1]);
                try {
                    JSONObject info = new JSONObject(message[1]);
                    // récupération de chaque information du profil
                    String success = info.getString("success");
                    String status = info.getString("status");
                    String username = info.getString("username");
                    String mdp = info.getString("mdp");

                    Profil profil = new Profil(success, status, username, mdp);
                    // enregistrement du profil dans le controle
                    controle.setProfil(profil);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(message[0].equals("Erreur !")){
                // retour suite à une erreur
                Log.d("retour", "************erreur="+message[1]);
            }
        }

    }

    /**
     * Envoi d'informations vers le serveur distant
     * @param operation
     * @param lesDonneesJSON
     */
    public void envoi(String operation, JSONArray lesDonneesJSON){

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
