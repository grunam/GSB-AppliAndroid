package fr.cned.emdsgil.suividevosfrais;

import org.junit.Test;

/**
 * Created by TIAB on 16/03/2018.
 */
public class KmActivityTest_OLD {


    private KmActivity myKmActivity = new KmActivity();
    private Integer annee = 2018;
    private Integer mois = 03;
    private Integer qte = 120;

    @Test
    public void getAnnee() throws Exception {
            assertEquals(annee, myKmActivity.getAnnee());
    }

    @Test
    public void getMois() throws Exception {
        assertEquals(mois, myKmActivity.getMois());
    }

    @Test
    public void getQte() throws Exception {
        assertEquals(qte, myKmActivity.getQte());
    }

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void onCreateOptionsMenu() throws Exception {
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
    }

}