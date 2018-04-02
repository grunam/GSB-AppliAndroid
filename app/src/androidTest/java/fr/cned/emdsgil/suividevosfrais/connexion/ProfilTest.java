package fr.cned.emdsgil.suividevosfrais.connexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;



import static org.junit.Assert.*;

public class ProfilTest {


    private String success = "0";
    private String status = "Erreur !";
    private String username = "dede";
    private String mdp = "9999";
    private List data = new ArrayList() ;
    private Profil unProfil;


    @Before
    public void setUp() {

        data.add("10") ;
        data.add("20") ;
        data.add("06") ;
        data.add("13") ;
        List listeFraisHf = new ArrayList() ;
        listeFraisHf.add("06");
        listeFraisHf.add("taxi");
        listeFraisHf.add("28");
        data.add(listeFraisHf) ;

        unProfil = new Profil(success, status, username, mdp, data);
    }

    @Test
    public void convertToJSONArray() {
        String expected  = "[\"0\",\"Erreur !\",\"dede\",\"9999\",[\"10\",\"20\",\"06\",\"13\",[\"06\",\"taxi\",\"28\"]]]";
        JSONArray actual = unProfil.convertToJSONArray();
        try {
            JSONAssert.assertEquals(expected, actual, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSuccess() {
        assertEquals(unProfil.getSuccess(), success);
    }

    @Test
    public void getStatus() {
        assertEquals(unProfil.getStatus(), status);
    }

    @Test
    public void getUsername() {
        assertEquals(unProfil.getUsername(), username);
    }

    @Test
    public void getMdp() {
        assertEquals(unProfil.getMdp(), mdp);
    }

    @Test
    public void getData() {
        assertArrayEquals(data.toArray(), unProfil.getData().toArray());
    }
}