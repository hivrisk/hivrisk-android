package aed.hivrisk;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class entrez extends HivRisk {
    boolean agreed = false;
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //Configuration c = new Configuration(getApplicationContext().getResources().getConfiguration());
        //c.locale = new Locale("es","ES");
        //c.locale = Locale.ENGLISH;
        //getApplicationContext().getResources().updateConfiguration(c,getApplicationContext().getResources().getDisplayMetrics());
        
        setContentView(R.layout.main);
        startListeners();
        startContent();
        startInterface();

    }
	
	public void startListeners() {
        ((View)findViewById(R.id.btnAgree)).setOnClickListener(ViewDisclaimer);
        ((View)findViewById(R.id.tvReadAgain)).setOnClickListener(ViewDisclaimer);
        ((View)findViewById(R.id.btnStart)).setOnClickListener(StartWizard);
	}
    
    public void startContent() {
    	int wasagreed = getDataInt("disclaimer");
    	Log.v("RETRIEVED_DISCLAIMER:: ", wasagreed+"");
    	Log.v("RETRIEVED_SIGNATURE: ", getDataString("signature"));
    	if (wasagreed>0) { agreed = true; }
    }
    
    public void startInterface() {
    	if (agreed) {
    		((View)findViewById(R.id.llAlreadyAgreedArea)).setVisibility(0);
    		((View)findViewById(R.id.llNotYetAgreedArea)).setVisibility(8);
    	} else {
    		((View)findViewById(R.id.llNotYetAgreedArea)).setVisibility(0);
    		((View)findViewById(R.id.llAlreadyAgreedArea)).setVisibility(8);
    	}
    }
    
    
    

}