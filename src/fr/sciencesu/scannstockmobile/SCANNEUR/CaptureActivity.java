
package fr.sciencesu.scannstockmobile.SCANNEUR;

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;

import fr.sciencesu.scannstockmobile.GMAP.LocalisationActivity;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.ClientBis;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.ScanNStock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.sciencesu.scannstockmobile.SCANNSTOCK.Client;

public class CaptureActivity extends DecoderActivity 
{

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet.of(ResultMetadataType.ISSUE_NUMBER, ResultMetadataType.SUGGESTED_PRICE,
            ResultMetadataType.ERROR_CORRECTION_LEVEL, ResultMetadataType.POSSIBLE_COUNTRY);

    private TextView statusView = null;
    private View resultView = null;
    private boolean inScanMode = false;
    
    private Button btn;
    private String isbn;

    @Override
    public void onCreate(Bundle icicle) 
    {
        super.onCreate(icicle);
        setContentView(R.layout.capture);
        //Log.v(TAG, "onCreate()");

        resultView = findViewById(R.id.result_view);
        statusView = (TextView) findViewById(R.id.status_view);

        inScanMode = false;
        
        btn = (Button) findViewById(R.id.btn_search);
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
                
            	Toast.makeText(getApplicationContext(), "Envoi " + isbn + " to " + ScanNStock.__IP 
                        + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
				
            	
		//Les données envoyés au serveur
                // ISBN le code scanné
                // Choice Département pour trouvé le bon stock dans la base de données
                 String data = isbn+";"+(LocalisationActivity.choiceDepartement) != null?LocalisationActivity.choiceDepartement:"69";
                
                //Création du client pour envoyer les données au serveur de création de produit
                Client c = new Client(ScanNStock.__IP,Integer.parseInt(ScanNStock.__PORT),data);
                
                //Lecture des données renvoyée par le serveur
                Toast.makeText(getApplicationContext(),c.getResponseLine(), Toast.LENGTH_LONG).show();
            	/*Toast.makeText(getApplicationContext(), "Envoi " + isbn + " � " + ScanNStock.__IP + " sur le port : " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
				
            	ClientBis c = new ClientBis(ScanNStock.__IP,ScanNStock.__PORT);
				
				String data = isbn.toString()+";"+(LocalisationActivity.choiceDepartement) != null?LocalisationActivity.choiceDepartement:"69";
				
				boolean reply = c.sendToServer(data);
				if(reply)
				{
					Toast.makeText(getApplicationContext(), "Produit trouv� et enregistr� dans la base ! " + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Le produit  est introuvable !" + ScanNStock.__PORT, Toast.LENGTH_LONG).show();
					
				}
            	*/
            }
        });
        
        Button bl = (Button) findViewById(R.id.btn_loc);
        bl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	//Toast.makeText(getApplicationContext(), "Localisation ", Toast.LENGTH_LONG).show();
            	Intent i = new Intent(CaptureActivity.this,LocalisationActivity.class);
            	startActivity(i);
            	//ledon();
            }
        });
    }
    
    Camera cam;
    void ledon() {
        cam = Camera.open();     
        Parameters params = cam.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_ON);
        cam.setParameters(params);
        cam.startPreview();
        cam.autoFocus(new Camera.AutoFocusCallback() {
                    public void onAutoFocus(boolean success, Camera camera) {
                    }
                });
    }
     
    void ledoff() {
        cam.stopPreview();
        cam.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inScanMode) finish();
            else onResume();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode) {
        drawResultPoints(barcode, rawResult);

        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
        handleDecodeInternally(rawResult, resultHandler, barcode);
    }

    protected void showScanner() {
        inScanMode = true;
        resultView.setVisibility(View.GONE);
        statusView.setText(R.string.msg_default_status);
        statusView.setVisibility(View.VISIBLE);
        viewfinderView.setVisibility(View.VISIBLE);
    }

    protected void showResults() {
        inScanMode = false;
        statusView.setVisibility(View.GONE);
        viewfinderView.setVisibility(View.GONE);
        resultView.setVisibility(View.VISIBLE);
    }

    // Put up our own UI for how to handle the decodBarcodeFormated contents.
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        onPause();
        showResults();
        
        btn.setEnabled(true);
        isbn = resultHandler.getDisplayContents().toString();
        btn.setText("Search to " + isbn );

        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
        if (barcode == null) {
            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        } else {
            barcodeImageView.setImageBitmap(barcode);
        }

        TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
        formatTextView.setText(rawResult.getBarcodeFormat().toString());

        TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
        typeTextView.setText(resultHandler.getType().toString());

        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
        TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
        timeTextView.setText(formattedTime);

        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
        View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
        metaTextView.setVisibility(View.GONE);
        metaTextViewLabel.setVisibility(View.GONE);
        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
        if (metadata != null) {
            StringBuilder metadataText = new StringBuilder(20);
            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
                    metadataText.append(entry.getValue()).append('\n');
                }
            }
            if (metadataText.length() > 0) {
                metadataText.setLength(metadataText.length() - 1);
                metaTextView.setText(metadataText);
                metaTextView.setVisibility(View.VISIBLE);
                metaTextViewLabel.setVisibility(View.VISIBLE);
            }
        }

        TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
        CharSequence displayContents = resultHandler.getDisplayContents();
        contentsTextView.setText(displayContents);
        // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
        
        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
    }
}
