package fr.cned.emdsgil.suividevosfrais.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.outils.Serializer;

/**
 * Classe pour gérer les éléments de la liste des frais hors forfait
 */
class FraisHfAdapter extends BaseAdapter {

	private final ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	private final LayoutInflater inflater ;
	//private final Context myContext ;
	//private static final String TAG = "activity_hf_recap";

    /**
	 * Constructeur de l'adapter pour valoriser les propriétés
     * @param context Accès au contexte de l'application
     * @param lesFrais Liste des frais hors forfait
     */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais) {
		//myContext = context ;
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais ;
    }
	
	/**
	 * retourne le nombre d'éléments de la listview
	 *
	 * @return le nombre d'éléments
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview à un index précis
	 *
	 * @return l'item
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 *
	 * @return l'index
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton cmdSuppHf;
	}
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(final int index, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, parent, false) ;
			View convertContainerView = inflater.inflate(R.layout.activity_hf_recap, null) ;
			holder.txtListJour = convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
			holder.cmdSuppHf = convertView.findViewById(R.id.cmdSuppHf);
			Integer annee = ((DatePicker)convertContainerView.findViewById(R.id.datHfRecap)).getYear() ;
			Integer mois = ((DatePicker)convertContainerView.findViewById(R.id.datHfRecap)).getMonth() + 1 ;
			final Integer key = annee*100+mois ;
			holder.cmdSuppHf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					lesFrais.remove(lesFrais.get(index));
					Global.listFraisMois.get(key).supprFraisHf(index) ;
					Serializer.serialize(Global.listFraisMois, inflater.getContext()) ;
					notifyDataSetChanged() ;
				}
			});
			convertView.setTag(holder) ;
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.txtListJour.setText(String.format(Locale.FRANCE, "%d", lesFrais.get(index).getJour()));
		holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", lesFrais.get(index).getMontant())) ;
		holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;
		return convertView ;
	}

}
