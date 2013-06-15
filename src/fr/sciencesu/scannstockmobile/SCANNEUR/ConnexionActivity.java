package fr.sciencesu.scannstockmobile.SCANNEUR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.Client;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.ScanNStock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnexionActivity extends Activity {

    private Button btn_connexion, btn_config_server,btn_validation,btn_test_product;
    private EditText edt_mdp;
    private EditText edt_user;
    private Spinner lstSite,lstStock;
    String isbn = "";
    static Client c;
    //String IP = "192.168.1.86";
    //int PORT = 5000;
    

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations2);
     

        //edt_mdp = (EditText) findViewById(R.id.edtmdp);
        //edt_user = (EditText) findViewById(R.id.edtusername);
        lstSite = (Spinner) findViewById(R.id.spinsites);
        //lstStock= (Spinner) findViewById(R.id.spinstock);
        btn_config_server = (Button) findViewById(R.id.btnconfigserver);
        btn_connexion = (Button) findViewById(R.id.btnconnexionbase);
        btn_validation = (Button) findViewById(R.id.btnvalidation);
        //btn_test_product = (Button) findViewById(R.id.btntestgoproduct);

        clickConfigServer();

        clickConnexionBdd();
        
        clickValidationData();
        
        //clickTestProduct();

    }

    private void generateLstAssociations(Collection<String> data) {

        List<String> list = new ArrayList<String>();
        for (String string : data) 
        {
            list.add(string);
        }
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstSite.setAdapter(dataAdapter);

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

    private void clickConnexionBdd() {
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //isbn = "lstSite"; //edt_isbn.getText().toString();
                
                Toast.makeText(getApplicationContext(), "Envoi " + isbn + " to " + ScanNStock.__IP 
                        + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
                
                /*c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                
                //Client c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT), "lstSite\n");
                c.sendData(isbn);
                String datasServer = c.getResponseLine();
                
                Collection<String> dataLst = new ArrayList<String>();
                dataLst.addAll(Arrays.asList(datasServer.split("\n")));
                generateLstSite(dataLst);*/
                c = null;
                try {
                    c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                } catch (IOException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
                Thread t = new Thread(c);
                t.start();
                
                 try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               String datasServer = c.getResponseLine();
                Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();
                
                
                c.data = "2";
               c.setEvent(true);
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               generateLstAssociations(c.getAssociations());
                
            }
        });
    }

    private void clickValidationData() {
        btn_validation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) 
            {
                String id,mdp,site,datas;
                
                id = edt_user.getText().toString();
                mdp = edt_mdp.getText().toString();
                site = lstSite.getSelectedItem().toString();
                /*datas = "validateData;"+ id +";"+mdp + ";"+site+"\n";
                
                Toast.makeText(getApplicationContext(), "Envoi " + datas + " to " + ScanNStock.__IP 
                        + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
                
                //c = new Client(ScanNStock.__IP, Integer.parseInt(ScanNStock.__PORT));
                c.sendData(datas);
                String datasServer = c.getResponseLine();
                
                 Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();*/
                
                c.setId(id);
                c.setMdp(mdp);
                c.data = "1";
               c.setEvent(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
               String datasServer = c.getResponseLine();
                Toast.makeText(getApplicationContext(), datasServer, Toast.LENGTH_LONG).show();
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
