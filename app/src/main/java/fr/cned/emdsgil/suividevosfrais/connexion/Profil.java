package fr.cned.emdsgil.suividevosfrais.connexion;

import android.util.Log;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour créer un profil de connexion
 */
public class Profil {

    // propriétés
    private String success ;
    private String status ;
    private String username ;
    private String mdp ;
    private List data ;

    /**
     * Constructeur pour créer un profil de connexion
     *
     * @param success la nature de la connexion
     * @param status message renvoyé par le serveur
     * @param username usename utilisé et confimé pour la connexion
     * @param mdp mot de passe utilisé et confimé pour la connexion
     * @param data données pour la connexion
     */
    public Profil(String success, String status, String username, String mdp, List data) {


        Log.d("success : ", "************" + success);
        Log.d("status : ", "************" + status);
        Log.d("username : ", "************" + username);
        Log.d("mdp : ", "************" + mdp);
        Log.d("data : ", "************" +  new JSONArray(data));

        this.success = success ;
        this.status = status ;
        this.username = username ;
        this.mdp = mdp ;
        this.data = data ;
    }


    /**
     * Conversion du profil au format json
     *
     * @return un objet JSON d'un profil de connexion
     */
    public JSONArray convertToJSONArray(){
        List liste = new ArrayList() ;
        liste.add(success) ;
        liste.add(status) ;
        liste.add(username) ;
        liste.add(mdp) ;
        liste.add(data) ;

        Log.d("data : ", "************" + liste.get(4).getClass()) ;
        Log.d("JSONArray : ", "************" + new JSONArray(liste)) ;
        return new JSONArray(liste) ;

    }

    /**
     * Récupération du succès
     *
     * @return la valeur de la propriété succes
     */
    public String getSuccess() {
        return success;
    }

    /**
     * Récupération du statut de la connexion
     *
     * @return la valeur de la propriété status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Récupération de l'username
     *
     * @return la valeur de la propriété username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Récupération du mdp
     *
     * @return la valeur de la propriété mdp
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Récupération des données de connexion
     *
     * @return la valeur de la propriété data
     */
    public List getData() {
        return data;
    }

}
