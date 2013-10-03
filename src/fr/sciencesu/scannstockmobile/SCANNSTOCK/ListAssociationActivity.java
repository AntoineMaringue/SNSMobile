/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;
import java.util.ArrayList;

/**
 *
 * @author antoi_000
 */
public class ListAssociationActivity extends Activity
{

    private final Context context = ListAssociationActivity.this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.lstassociations);
        
        Intent i = getIntent();
        ArrayList<String> associations = i.getStringArrayListExtra("lstAssociations");
        ListView list = (ListView)findViewById(R.id.lstassociation);
        list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,associations));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            

            public void onItemClick(AdapterView<?> av, View view, int i, long l) 
            {
                Toast.makeText(context, "Position "+i, Toast.LENGTH_SHORT).show();
            }
        });
        
    }
    
}
