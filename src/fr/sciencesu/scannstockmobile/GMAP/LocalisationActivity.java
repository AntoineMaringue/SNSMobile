package fr.sciencesu.scannstockmobile.GMAP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sciencesu.scannstockmobile.SCANNEUR.R;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class LocalisationActivity extends Activity implements OnClickListener, LocationListener{
	
	public static String choiceDepartement;
	private LocationManager lManager;
    private Location location;
    private String choix_source = "";
	private String cp = "";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //On spécifie que l'on va avoir besoin de gérer l'affichage du cercle de chargement
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
 
        setContentView(R.layout.localisation);
 
        //On récupère le service de localisation
        lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
        //Initialisation de l'écran
        reinitialisationEcran();
 
        //On affecte un écouteur d'évènement aux boutons
        findViewById(R.id.choix_source).setOnClickListener(this);
        findViewById(R.id.obtenir_position).setOnClickListener(this);
        findViewById(R.id.afficherAdresse).setOnClickListener(this);
    }
 
        //Méthode déclencher au clique sur un bouton
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choix_source:
			choisirSource();
			break;
		case R.id.obtenir_position:
			obtenirPosition();
			break;
		case R.id.afficherAdresse:
			afficherAdresse();			
			choiceDepartement = cp.substring(0, 2);
			
			Toast.makeText(getApplicationContext(), "departement = " + choiceDepartement, Toast.LENGTH_LONG).show();
			
			if(choiceDepartement != null){
				//On envoi un évènement pour afficher la carte
				Intent i = new Intent(LocalisationActivity.this,MapActivity.class);
				startActivity(i);
			}
			break;
		default:
			break;
		}
	}
 
	//Réinitialisation de l'écran
	private void reinitialisationEcran(){
		((TextView)findViewById(R.id.latitude)).setText("0.0");
		((TextView)findViewById(R.id.longitude)).setText("0.0");
		((TextView)findViewById(R.id.altitude)).setText("0.0");
		((TextView)findViewById(R.id.adresse)).setText("");
 
		findViewById(R.id.obtenir_position).setEnabled(false);
		findViewById(R.id.afficherAdresse).setEnabled(false);
	}
 
	private void choisirSource() {
		reinitialisationEcran();
 
		//On demande au service la liste des sources disponibles.
		List <String> providers = lManager.getProviders(true);
		final String[] sources = new String[providers.size()];
		int i =0;
		//on stock le nom de ces source dans un tableau de string
		for(String provider : providers)
			sources[i++] = provider;
 
		//On affiche la liste des sources dans une fenêtre de dialog
		//Pour plus d'infos sur AlertDialog, vous pouvez suivre le guide
		//http://developer.android.com/guide/topics/ui/dialogs.html
		new AlertDialog.Builder(LocalisationActivity.this)
		.setItems(sources, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				
				if(sources[which].equalsIgnoreCase("passive"))
				{
					ListView lv = (ListView)findViewById(R.id.listView1);
					ArrayList<String> variants = new ArrayList<String>();
					for(int i = 1; i < 100 ; i++)
					{
						variants.add(i<10?"0"+i:i+"");
					}
			        

					 final StableArrayAdapter adapter = new StableArrayAdapter(LocalisationActivity.this,android.R.layout.simple_list_item_1, variants);
					    lv.setAdapter(adapter);

					    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					      @Override
					      public void onItemClick(AdapterView<?> parent, final View view,
					          final int position, long id) {
					        final String item = (String) parent.getItemAtPosition(position);
					        view.animate().setDuration(2000).alpha(0)
					            .withEndAction(new Runnable() {
					              @Override
					              public void run() {
					               
					                adapter.notifyDataSetChanged();
					                view.setAlpha(1);
					               
					                Toast.makeText(getApplicationContext(), "departement = " + position+1, Toast.LENGTH_LONG).show();
					                choiceDepartement = (position+1<10)?"0"+position+1:position+1+"";			    			
					    			
					    			if(choiceDepartement != null){
					    				//On envoi un évènement pour afficher la carte
					    				Intent i = new Intent(LocalisationActivity.this,MapActivity.class);
					    				startActivity(i);
					    			}
					              }
					            });
					      }

					    });
					  

					  
				}
				else
				{
					findViewById(R.id.obtenir_position).setEnabled(true);
					//on stock le choix de la source choisi
					choix_source = sources[which];
					//on ajoute dans la barre de titre de l'application le nom de la source utilisé
					setTitle(String.format("%s - %s", getString(R.string.app_name),
						choix_source));
				}
			}
		})
		.create().show();
	}
 
	private void obtenirPosition() {
		//on démarre le cercle de chargement
		setProgressBarIndeterminateVisibility(true);
 
		//On demande au service de localisation de nous notifier tout changement de position
		//sur la source (le provider) choisie, toute les minutes (60000millisecondes).
		//Le paramètre this spécifie que notre classe implémente LocationListener et recevra
		//les notifications.
		lManager.requestLocationUpdates(choix_source, 60000, 0, this);
	}
 
	private void afficherLocation() {
		//On affiche les informations de la position a l'écran
		((TextView)findViewById(R.id.latitude)).setText(String.valueOf(location.getLatitude()));
		((TextView)findViewById(R.id.longitude)).setText(String.valueOf(location.getLongitude()));
		((TextView)findViewById(R.id.altitude)).setText(String.valueOf(location.getAltitude()));
	}
 
	private void afficherAdresse() {
		setProgressBarIndeterminateVisibility(true);
 
		//Le geocoder permet de récupérer ou chercher des adresses
		//gràce à un mot clé ou une position
		Geocoder geo = new Geocoder(LocalisationActivity.this);
		try {
			//Ici on récupère la premiere adresse trouvé gràce à la position que l'on a récupéré
			List
<Address> adresses = geo.getFromLocation(location.getLatitude(),
					location.getLongitude(),1);
 
			if(adresses != null && adresses.size() == 1){
				Address adresse = adresses.get(0);
				
				cp = adresse.getPostalCode();
				//Si le geocoder a trouver une adresse, alors on l'affiche
				((TextView)findViewById(R.id.adresse)).setText(String.format("%s, %s %s",
						adresse.getAddressLine(0),
						adresse.getPostalCode(),
						adresse.getLocality()));
			}
			else {
				//sinon on affiche un message d'erreur
				((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
			}
		} catch (IOException e) {
			e.printStackTrace();
			((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
		}
		//on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 
	public void onLocationChanged(Location location) {
		//Lorsque la position change...
		Log.i("Tuto géolocalisation", "La position a changé.");
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
		//... on active le bouton pour afficher l'adresse
		findViewById(R.id.afficherAdresse).setEnabled(true);
		//... on sauvegarde la position
		this.location = location;
		//... on l'affiche
		afficherLocation();
		//... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
		lManager.removeUpdates(this);
	}
 
	public void onProviderDisabled(String provider) {
		//Lorsque la source (GSP ou réseau GSM) est désactivé
		Log.i("Tuto géolocalisation", "La source a été désactivé");
		//...on affiche un Toast pour le signaler à l'utilisateur
		Toast.makeText(LocalisationActivity.this,
				String.format("La source \"%s\" a été désactivé", provider),
				Toast.LENGTH_SHORT).show();
		//... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
		lManager.removeUpdates(this);
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 
	public void onProviderEnabled(String provider) {
		Log.i("Tuto géolocalisation", "La source a été activé.");
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("Tuto géolocalisation", "Le statut de la source a changé.");
	}

}

class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects) {
      super(context, textViewResourceId, objects);
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }

    @Override
    public long getItemId(int position) {
      String item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

  }