package fr.cned.emdsgil.suividevosfrais.activities;

import java.io.Serializable;

/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
class FraisHf implements Serializable {

	private final Float montant ;
	private final String motif ;
	private final Integer jour ;

	/**
	 * Constructeur de la classe de description d'un frais hors forfait
	 *
	 * @param montant en euros du frais hors forfait
	 * @param motif justification du frais hors forfait
	 * @param jour jour du frais hors forfait
	 */
	public FraisHf(Float montant, String motif, Integer jour) {
		this.montant = montant ;
		this.motif = motif ;
		this.jour = jour ;
	}

	/**
	 * retourne le montant du frais hors forfait
	 *
	 * @return le montant du frais hors forfait
	 */
	public Float getMontant() {
		return montant;
	}

	/**
	 * retourne le motif du frais hors forfait
	 *
	 * @return le motif du frais hors forfait
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * retourne le jour du frais hors forfait
	 *
	 * @return le jour du frais hors forfait
	 */
	public Integer getJour() {
		return jour;
	}

}
