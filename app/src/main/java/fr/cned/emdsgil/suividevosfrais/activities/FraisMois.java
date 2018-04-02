package fr.cned.emdsgil.suividevosfrais.activities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe métier contenant les informations des frais d'un mois
 */
class FraisMois implements Serializable {

    private Integer mois; // mois concerné
    private Integer annee; // année concernée
    private Integer etape; // nombre d'étapes du mois
    private Integer km; // nombre de km du mois
    private Integer nuitee; // nombre de nuitées du mois
    private Integer repas; // nombre de repas du mois
    private final ArrayList<FraisHf> lesFraisHf; // liste des frais hors forfait du mois

    /**
     * Constructeur de la classe contenant les informations des frais d'un mois
     *
     * @param annee année du frais
     * @param mois mois du frais
     */
    public FraisMois(Integer annee, Integer mois) {
        this.annee = annee;
        this.mois = mois;
        this.etape = 0;
        this.km = 0;
        this.nuitee = 0;
        this.repas = 0;
        lesFraisHf = new ArrayList<>();
        /* Retrait du type de l'ArrayList (Optimisation Android Studio)
		 * Original : Typage explicit =
		 * lesFraisHf = new ArrayList<FraisHf>() ;
		*/
    }

    /**
     * Ajout d'un frais hors forfait
     *
     * @param montant Montant en euros du frais hors forfait
     * @param motif Justification du frais hors forfait
     */
    public void addFraisHf(Float montant, String motif, Integer jour) {
        lesFraisHf.add(new FraisHf(montant, motif, jour));
    }

    /**
     * Suppression d'un frais hors forfait
     *
     * @param index Indice du frais hors forfait à supprimer
     */
    public void supprFraisHf(Integer index) {
        lesFraisHf.remove(index);
    }

    /**
     * retourne le mois du frais
     *
     * @return mois du frais
     */
    public Integer getMois() {
        return mois;
    }

    /**
     * affecte le mois du frais
     *
     * @param mois paramètre mois affectée à la propriété du même nom
     */
    public void setMois(Integer mois) {
        this.mois = mois;
    }

    /**
     * retourne l'année du frais
     *
     * @return l'année du frais
     */
    public Integer getAnnee() {
        return annee;
    }

    /**
     * affecte l'année du frais
     *
     * @param annee paramètre annee affectée à la propriété du même nom
     */
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    /**
     * retourne le nombre d'étapes du mois
     *
     * @return le nombre d'étapes du mois
     */
    public Integer getEtape() {
        return etape;
    }

    /**
     * affecte le nombre d'étapes du mois
     *
     * @param etape paramètre etape affectée à la propriété du même nom
     */
    public void setEtape(Integer etape) {
        this.etape = etape;
    }

    /**
     * retourne le nombre de km du mois
     *
     * @return le nombre de km du mois
     */
    public Integer getKm() {
        return km;
    }

    /**
     * affecte le nombre de km du mois
     *
     * @param km paramètre km affectée à la propriété du même nom
     */
    public void setKm(Integer km) {
        this.km = km;
    }

    /**
     * retourne le nombre de nuitées du mois
     *
     * @return le nombre de nuitéess du mois
     */
    public Integer getNuitee() {
        return nuitee;
    }

    /**
     * affecte le nombre de nuitées du mois
     *
     * @param nuitee paramètre nuitee affectée à la propriété du même nom
     */
    public void setNuitee(Integer nuitee) {
        this.nuitee = nuitee;
    }

    /**
     * retourne le nombre de repas du mois
     *
     * @return le nombre de repas du mois
     */
    public Integer getRepas() {
        return repas;
    }

    /**
     * affecte le nombre de repas du mois
     *
     * @param repas paramètre repas affectée à la propriété du même nom
     */
    public void setRepas(Integer repas) {
        this.repas = repas;
    }

    /**
     * retourne la liste ArrayList des frais hors forfait du mois
     *
     * @return la liste ArrayList des frais hors forfait du mois
     */
    public ArrayList<FraisHf> getLesFraisHf() {
        return lesFraisHf;
    }

}
