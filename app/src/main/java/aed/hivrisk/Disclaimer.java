package aed.hivrisk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class Disclaimer extends HivRisk {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.disclaimer);
        
        ((View)findViewById(R.id.btnAgree)).setOnClickListener(AgreeDisclaimer);
        ((View)findViewById(R.id.btnDont)).setOnClickListener(DontDisclaimer);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
        	resetWizard();
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, entrez.class);
    		startActivityForResult(myIntent, 0);
        	finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}