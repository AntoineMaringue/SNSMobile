package fr.sciencesu.scannstockmobile.SCANNEUR;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.Client;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConnexionActivity extends Activity 
{
	private Button btn;
    private Spinner lstSite;
    String isbn = "";    
    String IP = "192.168.1.86";
    int PORT = 5000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations);
        
        //edt_isbn = (EditText) findViewById(R.id.edtisbn);
        //edt_reply = (TextView) findViewById(R.id.edtreply);       
        lstSite = (Spinner)findViewById(R.id.spinsites);      
        
        
        btn = (Button) findViewById(R.id.btnconnexionbase);
        //btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {            	
                isbn = "lstSite"; //edt_isbn.getText().toString();
            	Toast.makeText(getApplicationContext(), "Envoi " + isbn + " to " + IP + " sur le port : " + PORT, Toast.LENGTH_LONG).show();
                Client c = new Client(IP,PORT,"lstSite\n");
                String datasServer = c.getResponseLine();
               // edt_reply.setText(datasServer);

                Collection<String>  dataLst = new ArrayList<String>();
                for (String string : datasServer.split("\n")) {
                        dataLst.add(string);
                }
                generateLstSite(dataLst);
                
                                //Thread t = new Thread(c);
                                //t.start();
                                
            }
        });
        
    }

    private void generateLstSite(Collection<String> data) {
            
                List<String> list = new ArrayList<String>();
                list.addAll(data);
                ArrayAdapter<String> dataAdapter;
                dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lstSite.setAdapter(dataAdapter);
        
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
