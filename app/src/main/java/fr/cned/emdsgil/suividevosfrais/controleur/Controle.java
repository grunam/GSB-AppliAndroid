package fr.cned.emdsgil.suividevosfrais.controleur;

import android.content.Context;

import fr.cned.emdsgil.suividevosfrais.modele.AccesDistant;
//import fr.cned.emdsgil.suividevosfrais.modele.AccesLocal;
import fr.cned.emdsgil.suividevosfrais.modele.Profil;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;
import fr.cned.emdsgil.suividevosfrais.vue.MainActivity;

import android.util.Log;


import fr.cned.emdsgil.suividevosfrais.controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.outils.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;
import fr.cned.emdsgil.suividevosfrais.vue.TransfertActivity;


import org.json.JSONArray;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by emds on 08/01/2017.
 */

public final class Controle {

    // propriétés
    private static Controle instance = null ;
    private static Profil profil ;
    private static String nomFic = "saveprofil" ;
    //private static AccesLocal accesLocal ;
    private static AccesDistant accesDistant;
    private static Context contexte ;

    /**
     * Constructeur
     */
    private Controle() {
        super();
    }

    /**
     * Création de l'instance unique
     * @return
     */
    public static final Controle getInstance(Context contexte) {
        if (Controle.instance == null) {
            Controle.contexte = contexte;
            Controle.instance = new Controle() ;
            //accesLocal = new AccesLocal(contexte);
            /*
            accesDistant = new AccesDistant() ;
            */
            //profil = accesLocal.recupDernier();
            /*
            accesDistant.envoi("connexion", new JSONArray());
            */
            // recupSerialize(contexte);
        }
        return Controle.instance ;
    }

    /**
     * Création du profil
     * @param success
     * @param status
     * @param username
     * @param mdp
     */
    public void creerProfil(String success, String status, String username, String mdp) {
        profil = new Profil(success, status, username, mdp);
        // Serializer.serialize(nomFic, profil, contexte);
        // accesLocal.ajout(profil);
        accesDistant.envoi("connexion", profil.convertToJSONArray());
    }

    /**
     * Récupération d'un profil sérialisé
     * @param contexte
     */
    private static void recupSerialize(Context contexte) {
        profil = (Profil)Serializer.deSerialize(contexte) ;
    }


    /**
     * valorisation du profil
     * @param profil
     */
    public void setProfil(Profil profil){
        Controle.profil = profil;
        ((TransfertActivity)contexte).recupProfil();
    }


    /**
     * Récupération du succès
     * @return
     */
    public String getSuccess() {
        if (profil==null) {
            return null ;
        }else {
            return profil.getSuccess() ;
        }
    }

    /**
     * Récupération de l'erreur
     * @return
     */
    public String getStatus() {
        if (profil==null) {
            return null ;
        }else {
            return profil.getStatus() ;
        }
    }

    /**
     * Récupération de l'username
     * @return
     */
    public String getUsername() {
        if (profil==null) {
            return null ;
        }else {
            return profil.getUsername() ;
        }
    }

    /**
     * Récupération du mdp
     * @return
     */
    public String getMdp() {
        if (profil==null) {
            return null ;
        }else {
            return profil.getMdp() ;
        }
    }



}
