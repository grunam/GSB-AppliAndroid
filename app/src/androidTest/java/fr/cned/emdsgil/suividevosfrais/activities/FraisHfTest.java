package fr.cned.emdsgil.suividevosfrais.activities;

import org.junit.Test;

import static org.junit.Assert.*;

public class FraisHfTest {

    private float montant = 45;
    private String motif = "taxi";
    private Integer jour = 10;
    private FraisHf unFraisHf = new FraisHf(montant, motif, jour);

    @Test
    public void getMontant() {
        assertEquals(montant, unFraisHf.getMontant(), (float)0.01);
    }

    @Test
    public void getMotif() {
        assertEquals(motif, unFraisHf.getMotif());
    }

    @Test
    public void getJour() {
        assertEquals(jour, unFraisHf.getJour());
    }
}