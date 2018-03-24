package fr.cned.emdsgil.suividevosfrais.modele;

//import com.example.emds.coach.outils.MesOutils;

import android.util.Log;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

/**
 * Created by emds on 08/01/2017.
 */

public class Profil implements Serializable {

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


    /**
     * Constructeur
     * @param success
     * @param status
     * @param username
     * @param mdp
     */
    public Profil(String success, String status, String username, String mdp) {


        Log.d("success : ", "************" + success);
        Log.d("status : ", "************" + status);
        Log.d("username : ", "************" + username);
        Log.d("mdp : ", "************" + mdp);

        this.success = success;
        this.status = status;
        this.username = username;
        this.mdp = mdp;
    }


    /**
     * Conversion du profil au format json
     * @return
     */
    public JSONArray convertToJSONArray(){
        List liste = new ArrayList();
        liste.add(success);
        liste.add(status);
        liste.add(username);
        liste.add(mdp);
        Log.d("JSONArray : ", "************" + new JSONArray(liste));
        return new JSONArray(liste);

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


}
