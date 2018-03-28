package fr.cned.emdsgil.suividevosfrais.connexion;

import android.util.Log;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emds on 08/01/2017.
 */

public class Profil {

    // constantes
   /*
    private static final Integer minFemme = 15; // maigre si en dessous
    private static final Integer maxFemme = 30; // gros si au dessus
    private static final Integer minHomme = 10; // maigre si en dessous
    private static final Integer maxHomme = 25; // gros si au dessus
    */

    // propriétés
    private String success ;
    private String status ;
    private String username ;
    private String mdp ;
    private List data ;


    /**
     * Constructeur
     * @param success
     * @param status
     * @param username
     * @param mdp
     * @param data
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
     * @return
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

    public String getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getMdp() {
        return mdp;
    }

    public List getData() {
        return data;
    }

}
