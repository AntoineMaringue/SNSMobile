package fr.sciencesu.scannstockmobile.SCANNEUR;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewCustomActivity extends Activity
{
 
	private ListView maListViewPerso;
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);
 
        //Récupération de la listview créée dans le fichier main.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
 
        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>(); 
        
        listItem.add(createMap("Scan","Scanner un code à barres",String.valueOf(R.drawable.ic_scan)));
 
        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView
        listItem.add(createMap("Paramètres","Paramètres de l'application",String.valueOf(R.drawable.ic_parametres)));
        listItem.add(createMap("Aide","Aide",String.valueOf(R.drawable.ic_create)));
        listItem.add(createMap("Contact","Nous contacter",String.valueOf(R.drawable.ic_contact)));
        listItem.add(createMap("Sortir","Quitter l'application",String.valueOf(R.drawable.ic_exit)));
 
        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
 
        //On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);
 
        //Enfin on met un écouteur d'évènement sur notre listView
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			@Override
        	@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
        		//on créer une boite de dialogue
        		//AlertDialog.Builder adb = new AlertDialog.Builder(ListViewCustomActivity.this);
        		//on attribut un titre à notre boite de dialogue
        		//adb.setTitle("Sélection Item");
        		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
        		//adb.setMessage("Votre choix : "+map.get("titre"));
        		//on indique que l'on veut le bouton ok à notre boite de dialogue
        		//adb.setPositiveButton("Ok", null);
        		//on affiche la boite de dialogue
        		//adb.show();
        		
        		if(map.get("titre").equalsIgnoreCase("scan"))
        		{
        			Intent intent = new Intent(ListViewCustomActivity.this, CaptureActivity.class);
                    startActivity(intent);
        		}
        		
        		else if(map.get("titre").equalsIgnoreCase("Paramètres"))
        		{
        			Intent intent = new Intent(ListViewCustomActivity.this, ParametresActivity.class);
                    startActivity(intent);
        		}
        		
        		else if(map.get("titre").equalsIgnoreCase("Contact"))
        		{
        			AlertDialog.Builder adb = new AlertDialog.Builder(ListViewCustomActivity.this);
            		//on attribut un titre à notre boite de dialogue
            		adb.setTitle("Contact");
            		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
            		adb.setMessage("CONTACT DE L'APPLICATION : ");
            		//on indique que l'on veut le bouton ok à notre boite de dialogue
            		adb.setPositiveButton("Ok", null);
            		//on affiche la boite de dialogue
            		adb.show();
        		}
        		
        		else if(map.get("titre").equalsIgnoreCase("Sortir"))
        		{
        			System.exit(1);
        		}
        		
        		else if(map.get("titre").equalsIgnoreCase("Aide"))
        		{
        			AlertDialog.Builder adb = new AlertDialog.Builder(ListViewCustomActivity.this);
            		//on attribut un titre à notre boite de dialogue
            		adb.setTitle("Aide");
            		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
            		adb.setMessage("AIDE DE L'APPLICATION : ");
            		//on indique que l'on veut le bouton ok à notre boite de dialogue
            		adb.setPositiveButton("Ok", null);
            		//on affiche la boite de dialogue
            		adb.show();
        		}
        	}
         });
 
    }


	private HashMap<String, String> createMap(String string, String string2,String valueOf) {
		//Création d'une HashMap pour insérer les informations du premier item de notre listView
		HashMap<String, String> map = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        map.put("titre", string);
        //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
        map.put("description", string2);
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        map.put("img", valueOf);
        return map;
	}
}