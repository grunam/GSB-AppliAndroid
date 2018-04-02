package fr.cned.emdsgil.suividevosfrais.outils;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import fr.cned.emdsgil.suividevosfrais.outils.AsyncResponse;

/**
 * Classe technique de connexion distante HTTP
 */
public class AccesHTTP extends AsyncTask<String, Integer, Long> {
	public String ret="";
	private ArrayList<NameValuePair> parametres;
	public AsyncResponse delegate=null;

	/**
	 * Constructeur affecte à la propriété parametres un nouvel ArrayList
	 *
	 */
	public AccesHTTP(){
		parametres = new ArrayList<NameValuePair>();
	}

	/**
	 * Ajoute des paramètres à la propriété parametres
	 *
	 * @param nom nom du paramètre
	 * @param valeur valeur du paramètre
	 */
	public void addParam(String nom, String valeur){
		parametres.add(new BasicNameValuePair(nom,valeur));
	}

	/**
	 * Gère la connexion au serveur en tâche de fond
	 *
	 * @param urls url du script du serveur
	 * @return rien (null)
	 */
	@Override
	protected Long doInBackground(String... urls) {

		HttpClient cnxHttp = new DefaultHttpClient();
		HttpPost paramCnx = new HttpPost(urls[0]);
		
		try {
		    paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
		    HttpResponse reponse = cnxHttp.execute(paramCnx);
		    ret = EntityUtils.toString(reponse.getEntity());

		} catch (ClientProtocolException e) {
		    Log.d("Erreur ClientProtocol", e.toString()) ;
		} catch (IOException e) {
            Log.d("Erreur IOException", e.toString()) ;
		}
        return null;
	}

	/**
	 * Exécuté dès qu'une réponse est reçu de la fonction doInBackground, appel la fonction processFinish
	 *
	 * @param result resultat reçu
	 */
	@Override
	protected void onPostExecute(Long result) {
		// ret contient l'information récupérée
		delegate.processFinish(this.ret.toString());
    }
	
}
