package fr.cned.emdsgil.suividevosfrais.connexion;

import android.content.Context;

//import fr.cned.emdsgil.suividevosfrais.modele.AccesLocal;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;

import android.util.Log;


import fr.cned.emdsgil.suividevosfrais.activities.TransfertActivity;


import java.util.List;

/**
 * Created by emds on 08/01/2017.
 */

public final class ControleAcces {

    // propriétés
    private static ControleAcces instance = null ;
    private static Profil profil ;
    private static String nomFic = "saveprofil" ;
    //private static AccesLocal accesLocal ;
    private static AccesDistant accesDistant;
    private static Context contexte ;

    /**
     * Constructeur
     */
    private ControleAcces() {
        super();
    }

    /**
     * Création de l'instance unique
     * @return
     */
    public static final ControleAcces getInstance(Context contexte) {
        if (ControleAcces.instance == null) {
            ControleAcces.contexte = contexte;
            ControleAcces.instance = new ControleAcces() ;
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
        return ControleAcces.instance ;
    }

    /**
     * Création du profil
     * @param success
     * @param status
     * @param username
     * @param mdp
     */
    public void creerProfil(String success, String status, String username, String mdp, List data) {
        profil = new Profil(success, status, username, mdp, data);
        // Serializer.serialize(nomFic, profil, contexte);
        // accesLocal.ajout(profil);
        accesDistant = new AccesDistant() ;
        String operation = (success == "0")?"connexion":"enreg";
        accesDistant.envoi(operation, profil.convertToJSONArray()) ;
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
        ControleAcces.profil = profil;
        Log.d("Profil : ", "************" + profil);
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
