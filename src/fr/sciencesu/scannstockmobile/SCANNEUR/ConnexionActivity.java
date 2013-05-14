package fr.sciencesu.scannstockmobile.SCANNEUR;

import java.net.InetAddress;
import java.net.Socket;

import fr.sciencesu.scannstockmobile.DAO.Base;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.Client;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.ScanNStock;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConnexionActivity extends Activity 
{
	Button btnConnexion;
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

}
