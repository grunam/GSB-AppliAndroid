package fr.cned.emdsgil.suividevosfrais.activities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.*;

public class FraisMoisTest {

    private Integer annee = 2018;
    private Integer mois = 04;
    private Integer key = (annee*100)+mois ;
    private Integer etape = 0;
    private Integer km = 0;
    private Integer nuitee = 0;
    private Integer repas = 0;
    private ArrayList lesFraisHf = new ArrayList<>();

    private FraisMois unFraisMois = new FraisMois(annee, mois);


    @Test
    public void setMois() {
        unFraisMois.setMois(mois);
        assertEquals(unFraisMois.getMois(), mois);
    }

    @Test
    public void getMois() {
        assertEquals(unFraisMois.getMois(), mois);
    }

    @Test
    public void setAnnee() {
        unFraisMois.setAnnee(annee);
        assertEquals(unFraisMois.getAnnee(), annee);
    }

    @Test
    public void getAnnee() {
        assertEquals(unFraisMois.getAnnee(), annee);
    }

    @Test
    public void setEtape() {
        unFraisMois.setEtape(etape);
        assertEquals(unFraisMois.getEtape(), etape);
    }

    @Test
    public void getEtape() {
        assertEquals(unFraisMois.getEtape(), etape);
    }

    @Test
    public void setKm() {
        unFraisMois.setKm(km);
        assertEquals(unFraisMois.getKm(), km);
    }

    @Test
    public void getKm() {
        assertEquals(unFraisMois.getKm(), km);
    }

    @Test
    public void setNuitee() {
        unFraisMois.setNuitee(nuitee);
        assertEquals(unFraisMois.getNuitee(), nuitee);
    }

    @Test
    public void getNuitee() {
        assertEquals(unFraisMois.getNuitee(), nuitee);
    }

    @Test
    public void setRepas() {
        unFraisMois.setRepas(repas);
        assertEquals(unFraisMois.getRepas(), repas);
    }

    @Test
    public void getRepas() {
        assertEquals(unFraisMois.getRepas(), repas);
    }

    @Test
    public void getLesFraisHf() {
        assertArrayEquals(unFraisMois.getLesFraisHf().toArray(), lesFraisHf.toArray());
    }
}