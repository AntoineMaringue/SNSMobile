package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.MainActivity;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnexionActivity extends Activity{
    private final Context context = ConnexionActivity.this;   
    private ArrayList<Produit> produits;
    private int num_col;
    
    private Button btn_connexion, btn_config_server,btn_validation,btn_test_product;
    private EditText edt_mdp;
    private EditText edt_user;
    private Spinner lstSite,lstStock;
    private String isbn = "";
    public static Client c;
    private AlertDialog dialog;    
    private GridView gvConnex;
    private Boolean validation_connexion = false;
    
    Handler m_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final String infosServeur = msg.getData().getString("msg");            
            // On peut faire toute action qui met à jour l'IHM
            if(msg.obj.toString().equalsIgnoreCase("associations"))
            {
                
                generateLstAssociations(c.getAssociations());
            }
            else if(msg.obj.toString().equalsIgnoreCase("produits"))
            {
                try {
                    Thread.sleep(1000);
                    generateLstProducts(c.getProducts());
                } 
                catch (InterruptedException ex) {
                    Logger.getLogger(ConnexionActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
              
            }
            else if(msg.obj.toString().equalsIgnoreCase("serverID"))
            {
                
               
              
            }
             
        }

        
        private void generateLstProducts(ArrayList<String> products) {
           
            produits = new ArrayList<Produit>();
            num_col = products.size()%5;
            int x = 0;
            int y = 0;
            while(x < num_col)
            {
                Produit p = new Produit();                
                p.setID(products.get(y+0));
                p.setMarque(products.get(y+1));
                p.setPrix(products.get(y+2));
                p.setContenance(products.get(y+3));
                p.setName(products.get(y+4));                
                p.setImgSrc(products.get(y+5));
                y += 6;
                x++;
                produits.add(p);
            }
           showDialog(1);
         }
    };
    
    public static Bitmap getBitmapFromURL(String src) {
    try {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
   

    void setLayoutParams(GridView view){
		//view.setLayoutParams(new LayoutParams((int)getResources().getDimension(R.dimen.gv_width), 
		//		(int)getResources().getDimension(R.dimen.gv_height)));
		view.setNumColumns(2);
		view.setColumnWidth((int)getResources().getDimension(R.dimen.gv_column_width));
		view.setVerticalSpacing((int)getResources().getDimension(R.dimen.gv_ver_spacing));
		view.setHorizontalSpacing((int)getResources().getDimension(R.dimen.gv_hor_spacing));
		view.setGravity(Gravity.CENTER);
		view.setStretchMode(GridView.NO_STRETCH);
		view.setPadding((int)getResources().getDimension(R.dimen.gv_padding_left), 
				(int)getResources().getDimension(R.dimen.gv_padding_top), 
				(int)getResources().getDimension(R.dimen.gv_padding_right), 
				(int)getResources().getDimension(R.dimen.gv_padding_bottom));
		

    }

    private void initializeMenu() {
        
    //R�cup�ration de la listview cr��e dans le fichier main.xml
        gvConnex = (GridView) findViewById(R.id.gridviewperso);
        
         setLayoutParams(gvConnex);
        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>(); 
        
        listItem.add(createMap("Configuration",String.valueOf(R.drawable.ic_preferences_logo)));
        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre gridview
        listItem.add(createMap("Connexion",String.valueOf(R.drawable.ic_connexion)));
        listItem.add(createMap("Choix association",String.valueOf(R.drawable.ic_choix)));
        listItem.add(createMap("Validation",String.valueOf(R.drawable.ic_validation)));
        
        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.item_product,
               new String[] {"img", "titre"}, new int[] {R.id.product_img, R.id.product_infos});
 
        //On attribut � notre listView l'adapter que l'on vient de cr�er
        gvConnex.setAdapter(mSchedule);
 
        
        //Enfin on met un �couteur d'�v�nement sur notre listView
        gvConnex.setOnItemClickListener(new OnItemClickListener() {
			@Override
        	@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        
				//on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) gvConnex.getItemAtPosition(position);        		
        		
                        if(map.get("titre").equalsIgnoreCase("configuration"))
        		{
                            Intent i = new Intent(ConnexionActivity.this, ParametresActivity.class);
                            startActivity(i);
        		}
                        else if(map.get("titre").equalsIgnoreCase("Connexion"))
        		{
                           new GetAssociationTask("Connexion serveur et génération des associations","recherche des associations en cours").execute();
                            if(!validation_connexion)
                            {
                                Toast.makeText(context, "Connexion impossible vérifier que le serveur est lancé ou que vous avez une connexion internet", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(map.get("titre").equalsIgnoreCase("Choix association"))
        		{
                           if(!validation_connexion)
                            {
                                Toast.makeText(context, "Connexion impossible vérifier que le serveur est lancé ou que vous avez une connexion internet", Toast.LENGTH_LONG).show();
                            }
                           else
                           {
                          new GetAssociationTask("Chargement en cours ...","Recherche produits ....").execute();
                           }
                        }
                        else if(map.get("titre").equalsIgnoreCase("Validation"))
        		{
                            if(!validation_connexion)
                            {
                                Toast.makeText(context, "Connexion impossible vérifier que le serveur est lancé ou que vous avez une connexion internet", Toast.LENGTH_LONG).show();
                            }
                            else{
                                String site = lstSite.getSelectedItem().toString();
                                c.setId(site);
                                c.data = "1";
                                c.setEvent(true);
                                try 
                                {
                                    Thread.sleep(5000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               String datasServer = c.getResponseLine();
                               Toast.makeText(context, "Sauvegarde de l'id " + datasServer, Toast.LENGTH_LONG).show();

                                //Enregistrements du site et du stock lié au site
                                ScanNStock.__SITE = site;
                                //Insertion du stock de l'association
                                ScanNStock.__STOCK = datasServer;
                                //Ouverture de la page principale
                                Intent intent = new Intent(ConnexionActivity.this, ListViewCustomActivity.class);
                                 startActivity(intent);
                            }
        	}}
         });
 
    }
    
    private HashMap<String, String> createMap(String string, String valueOf) {
		//Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
		HashMap<String, String> map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier affichageitem.xml
        map.put("titre", string);
        //on ins�re la r�f�rence � l'image (convertit en String car normalement c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", valueOf);
        return map;
	}
    
     private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    //new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
     GridView gv;
    protected Dialog onCreateDialog(int id) {   
    switch(id) {   
    case 1:   
            AlertDialog.Builder builder;   
            Context mContext = this;   
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);   
            View layout = inflater.inflate(R.layout.grid_btnnew, null);   
            gv = (GridView)layout.findViewById(R.id.gridproducts);
            gv.setNumColumns(num_col);
            ProductAdapter adapter = new ProductAdapter(mContext,produits);  
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            gv.invalidateViews();
            gv.setOnItemClickListener(new OnItemClickListener() {   
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {   
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();   
            }   

        });   
            builder = new AlertDialog.Builder(mContext);   
            builder.setView(layout);   
            dialog = builder.create();   
            
           
            
        break;   
    default:   
        dialog = null;   
    }   
    return dialog;   
}   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations);
     

        initializeMenu();
       
        //edt_mdp = (EditText) findViewById(R.id.edtmdp);
        //edt_user = (EditText) findViewById(R.id.edtusername);
        //lstSite = (Spinner) findViewById(R.id.spinsites);
        //lstStock= (Spinner) findViewById(R.id.spinstock);
        //btn_config_server = (Button) findViewById(R.id.btnconfigserver);
        //btn_connexion = (Button) findViewById(R.id.btnconnexionbase);
        //btn_validation = (Button) findViewById(R.id.btnvalidation);
        //btn_test_product = (Button) findViewById(R.id.btntestgoproduct);

        //clickConfigServer();

        //clickConnexionBdd();
        
        //clickValidationData();
        
        //clickTestProduct();

    }

    Intent intentGetAssociations;
    ArrayList<String> listassociations;
    private void generateLstAssociations(Collection<String> data) {

        listassociations = new ArrayList<String>();
        for (String string : data) 
        {
            listassociations.add(string);
        }
        intentGetAssociations = new Intent(this,ListAssociationActivity.class);
        intentGetAssociations.putStringArrayListExtra("lstAssociations", listassociations);
        startActivity(intentGetAssociations);
        /*ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstSite.setAdapter(dataAdapter);*/

    }
   


    
    private void generateLstStock(Collection<String> data) {

        List<String> list = new ArrayList<String>();
        for (String string : data) 
        {
            list.add(string);
        }
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstStock.setAdapter(dataAdapter);

    }

    private void clickConfigServer() {
        btn_config_server.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(ConnexionActivity.this, ParametresActivity.class);
                startActivity(i);
            }
        });
    }
    
   
    
    private class GetAssociationTask extends AsyncTask <Void, Void, String>
{
    private ProgressDialog dialog;
    private String title,subject;

        public GetAssociationTask(String title, String subject) {
            this.title = title;
            this.subject = subject;
        }
    
    

    @Override
    protected void onPreExecute()
    {
        dialog = ProgressDialog.show(
            ConnexionActivity.this,
            title,
            subject, 
            true);
    }

    @Override
    protected String doInBackground(Void... params)
    {
        if(c == null)
        {
            c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT),ConnexionActivity.this);
            Thread t = new Thread(c);
            t.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(validation_connexion = c.isIsConnected())
        {
            if(this.subject.contains("associations"))
            {
                generateAssociation();
            }
            else if(this.subject.contains("produits"))
            {
                generateProducts();
            }
        }              
        return "";
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog.cancel();
        }
    }

        private void generateAssociation() {
                
        c.data = "2";
               c.setEvent(true);
               m_handler.obtainMessage();
               Message msg = new Message();
               msg.obj="associations";
               long time = 10000;
               m_handler.sendMessageDelayed(msg,time);
               //m_handler.sendMessage(msg);
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    private void generateProducts() {
            
          c.data = "4";
          c.setEvent(true);
          m_handler.obtainMessage();
          Message msg = new Message();
          msg.obj="produits";
          long time = 1000;
          m_handler.sendMessageDelayed(msg,time);
          
    }

    private void clickConnexionBdd() {
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //isbn = "lstSite"; //edt_isbn.getText().toString();                

                  
              
               new GetAssociationTask("Chargement","Recherche produits ....").execute();
              
               
            
               
              
                
            }
        });
    }
    
    /**
     * 
     * @param msg : message à afficher
     * @param tags : start ou stop
     */
     private void informClientIHM(String msg,String tags,int time) {
        if(!msg.isEmpty())
        {
        Message myMessage=m_handler.obtainMessage();   
        myMessage.obj = tags;
        myMessage.what=time;
        //Ajouter des données à transmettre au Handler via le Bundle
        Bundle messageBundle = new Bundle();
        messageBundle.putString("msg", msg);
        //Ajouter le Bundle au message
        myMessage.setData(messageBundle);
        //Envoyer le message
        m_handler.sendMessage(myMessage);}
    }
    

    private void clickValidationData() {
        btn_validation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) 
            {
                String id,mdp,site,datas;
                
                //id = edt_user.getText().toString();
                //mdp = edt_mdp.getText().toString();
                site = lstSite.getSelectedItem().toString();
                /*datas = "validateData;"+ id +";"+mdp + ";"+site+"\n";
                
                Toast.makeText(getApplicationContext(), "Envoi " + datas + " to " + ScanNStock.__IP 
                        + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
                
                //c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                c.sendData(datas);
                String datasServer = c.getResponseLine();
                
                 Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();*/
                
                //c.setId(id);
                //c.setMdp(mdp);
                c.setId(site);
                c.data = "1";
               c.setEvent(true);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               String datasServer = c.getResponseLine();
                Toast.makeText(getApplicationContext(), "Sauvegarde de l'id " + datasServer, Toast.LENGTH_LONG).show();
                
                //Enregistrements du site et du stock lié au site
                ScanNStock.__SITE = site;
                //Insertion du stock de l'association
                ScanNStock.__STOCK = datasServer;

                //Ouverture de la page principale
                Intent intent = new Intent(ConnexionActivity.this, ListViewCustomActivity.class);
                 startActivity(intent);
            }
        });}

    private void clickTestProduct() {
         btn_test_product.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String recupIsbn = edt_user.getText().toString();
                
               c.setISBN(recupIsbn);
               c.data = "3";
               c.setEvent(true);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               String datasServer = c.getResponseLine();
                Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();
            
            
            }
        });
    }

}
/*Button btnConnexion;
 EditText edtId, edtMdp;
	
 @Override
 protected void onCreate(Bundle savedInstanceState) 
 {		
 super.onCreate(savedInstanceState);
 this.setContentView(R.layout.connexion);
		
 btnConnexion = (Button) findViewById(R.id.btn_connexion);
 edtId = (EditText) findViewById(R.id.edtIdentifiant);
 edtMdp = (EditText) findViewById(R.id.edtMdp);
				
 btnConnexion.setOnClickListener(new OnClickListener() 
 {
			
 @Override
 public void onClick(View v) 
 {
				
 if(connexionEstablished())
 {
 Intent intent = new Intent(ConnexionActivity.this, ListViewCustomActivity.class);
 startActivity(intent);
 }				
 }
 });
		
		
 }
	
 public boolean connexionEstablished()
 {
 boolean r = false;
 String id = edtId.getText().toString();
 String mdp = edtMdp.getText().toString();
		
 if(id != "" && mdp != "")
 r = Base.getInstance().connection(id,mdp);
		
 return r;
 }

 }*/


