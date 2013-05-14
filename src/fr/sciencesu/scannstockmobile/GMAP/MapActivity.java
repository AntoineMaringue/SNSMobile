package fr.sciencesu.scannstockmobile.GMAP;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

public class MapActivity extends Activity
{
	private Geolocalisation geolocalisation;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmap);
		
		// les définitions de type mime et de l'encodage 
		//final String mimeType = "text/html"; 
		//final String encoding = "utf-8";   
		
		//geolocalisation = new Geolocalisation();
		
		//geolocalisation.searchGeoLoc(LocalisationActivity.choiceDepartement);
		
		//Chargement de la page google map construite
		//String mapage= geolocalisation.getFileContent();
		
		WebView objetview= (WebView)findViewById(R.id.mapSites); 
		
		String url = "http://www.restosducoeur.org/francemap?dep="+ LocalisationActivity.choiceDepartement+ "#regions-liste-restos";
				//geolocalisation.getUrl(LocalisationActivity.choiceDepartement);
		
		//objetview.setWebViewClient(new WebViewClient());
		objetview.getSettings().setJavaScriptEnabled(true);
		
		objetview.getSettings().setAllowFileAccess(true);
		objetview.getSettings().setBlockNetworkImage(false);
		objetview.getSettings().setLoadsImagesAutomatically(true);
		
		objetview.loadUrl(url);
		
		//on charge mon code html dans ma webview 
		//objetview.loadData(mapage, mimeType, encoding);
		
		
	}
}
