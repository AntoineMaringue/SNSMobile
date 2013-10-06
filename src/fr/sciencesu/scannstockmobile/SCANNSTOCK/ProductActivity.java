/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fr.sciencesu.scannstockmobile.SCANNEUR.R;

/**
 *
 * @author antoi_000
 */
public class ProductActivity extends AbstractUtilsActivity
{

    Button bouton_validation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_new_product);
        
        bouton_validation.setOnClickListener(onClickListener);
    }
    
    View.OnClickListener onClickListener= new View.OnClickListener() {

        public void onClick(View view) {
            //Prise en compte des informations
            finish();
        }
    };
}
