package aed.hivrisk;

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Wizard extends HivRisk {
	

	@Override 
    public void onDestroy() { 
            super.onDestroy(); 
            if (sxla!=null) {
            	sxla.closeDB();
            }
    } 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	
        	if(simplereturn) {
        		return super.onKeyDown(keyCode, event);
        	}

	        if (step>0&&step<9) {
	        	Context mContext = getApplicationContext();
	    		Intent myIntent = new Intent(mContext, Wizard.class);
	    		myIntent.putExtra("step", step-1);
	    		myIntent.putExtra("content", content);
	    		myIntent.putExtra("aindex", aindex);
	    		myIntent.putExtra("barrier", barrier);
	    		startActivityForResult(myIntent, 0);
	        	finish();
	        } else if (step==9) {
	        	Context mContext = getApplicationContext();
	    		Intent myIntent = new Intent(mContext, Wizard.class);
	    		myIntent.putExtra("step", 0);
	    		myIntent.putExtra("content", content);
	    		myIntent.putExtra("aindex", aindex);
	    		myIntent.putExtra("barrier", barrier);
	    		startActivityForResult(myIntent, 0);
	        	finish();
	        } else if (step>9) {
	        	Context mContext = getApplicationContext();
	    		Intent myIntent = new Intent(mContext, Wizard.class);
	    		myIntent.putExtra("step", step-1);
	    		myIntent.putExtra("content", content);
	    		myIntent.putExtra("aindex", aindex);
	    		myIntent.putExtra("barrier", barrier);
	    		startActivityForResult(myIntent, 0);
	        	finish();
	        } else {
	        	resetWizard();
	        	Context mContext = getApplicationContext();
	    		Intent myIntent = new Intent(mContext, entrez.class);
	    		startActivityForResult(myIntent, 0);
	        	finish();
	        }
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)
        {
        	if(extras.containsKey("step")){
        		step = extras.getInt("step");
        	}
        	if(extras.containsKey("content")){
        		content = extras.getIntArray("content");
        	}
        	if(extras.containsKey("aindex")){
        		aindex = extras.getInt("aindex");
        	}
        	if(extras.containsKey("barrier")){
        		barrier = extras.getIntArray("barrier");
        	}
        	if(extras.containsKey("simplereturn")){
        		simplereturn = extras.getBoolean("simplereturn");
        	}
            Log.v("THIS STEP:::",step+"");
           
        }    
        
        haslicensed = getDataString("license");
        

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        switch (getResources().getDisplayMetrics().densityDpi) {
        case DisplayMetrics.DENSITY_LOW:
            Log.v("DENSITY:::", "LOW");
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
        	Log.v("DENSITY:::", "MED");
            break;
        case DisplayMetrics.DENSITY_HIGH:
        	Log.v("DENSITY:::", "HIGHT");
            break;
        default:
        	Log.v("DENSITY:::", "XXX");
            break;
        }
        
        
        if ((getResources().getConfiguration().screenLayout & 
        	    Configuration.SCREENLAYOUT_SIZE_MASK) == 
        	        Configuration.SCREENLAYOUT_SIZE_LARGE) {
        	Log.v("SIZE:::", "LARGE");

    	}
        
        if ((getResources().getConfiguration().screenLayout & 
        	    Configuration.SCREENLAYOUT_SIZE_MASK) == 
        	        Configuration.SCREENLAYOUT_SIZE_NORMAL) {
        	Log.v("SIZE:::", "NORMAL");

    	}
        
        if ((getResources().getConfiguration().screenLayout & 
        	    Configuration.SCREENLAYOUT_SIZE_MASK) == 
        	        Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
        	Log.v("SIZE:::", "UDEF");

    	}
        
        
        if(step==0){
            //RESET THIS STEP
            content[step]=0;
            aindex = 0;
            Arrays.fill(barrier, 0);
            Arrays.fill(activities, 0);
        	setContentView(R.layout.step_zero);
        	((View)findViewById(R.id.btnOne)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnTwo)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnThree)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnNavBack)).setVisibility(8);
        }
        
        else if(step==1){
            //RESET THIS STEP
            content[step]=0;
            aindex = 0;
            Arrays.fill(barrier, 0);
            Arrays.fill(activities, 0);
        	setContentView(R.layout.step_one);
        	((View)findViewById(R.id.btnOne)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnTwo)).setOnClickListener(OptionSelected);
        }
        
        else if(step==2){
            //RESET THIS STEP
            content[step]=0;
            aindex = 0;
            Arrays.fill(barrier, 0);
            Arrays.fill(activities, 0);
        	setContentView(R.layout.step_two);
        	((View)findViewById(R.id.btnOne)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnTwo)).setOnClickListener(OptionSelected);
        }
        
        else if(step==3){
            //RESET THIS STEP
            content[step]=0;
            aindex = 0;
            Arrays.fill(barrier, 0);
            Arrays.fill(activities, 0);
        	setContentView(R.layout.step_three);   
        	((View)findViewById(R.id.btnNavNext)).setOnClickListener(NavNext);
        	setContentLoader("three");
        }
        
        else if(step==4){
            //RESET THIS STEP
        	if (aindex==0) {
                content[step]=0;
                Arrays.fill(barrier, 0);
        	}
        	setContentView(R.layout.step_four); 
        	((View)findViewById(R.id.btnOne)).setOnClickListener(ProtectionSelected);
        	((View)findViewById(R.id.btnTwo)).setOnClickListener(ProtectionSelected);
        	((View)findViewById(R.id.btnThree)).setOnClickListener(ProtectionSelected);
        	((View)findViewById(R.id.btnTip1)).setOnClickListener(FloatingContent);
        	((View)findViewById(R.id.btnTip2)).setOnClickListener(FloatingContent);
        	setContentLoader("four");
        }
        
        else if(step==5){
        	setContentView(R.layout.step_review);
    		//if (haslicensed=="") { 
    			//((View)findViewById(R.id.llLicenseArea)).setVisibility(0);
    			//((View)findViewById(R.id.btnBuyApp)).setVisibility(0);
    			//((View)findViewById(R.id.btnNavNext)).setVisibility(8);
    			//((View)findViewById(R.id.btnBuyApp)).setOnClickListener(BuyApp);
			//} else {
				((View)findViewById(R.id.btnNavNext)).setOnClickListener(NavNext);
			//}
        	
        	setContentLoader("review");
        }
        
        else if(step==6){
        	setContentView(R.layout.step_results);
        	((View)findViewById(R.id.btnNavNext)).setOnClickListener(NavNextStep);
        	((View)findViewById(R.id.btnSnapShot)).setOnClickListener(SnapShot);
        	setContentLoader("results");
        }
        
        else if(step==7){
        	setContentView(R.layout.step_donext); 
        	((View)findViewById(R.id.btnContribute)).setOnClickListener(NavNextStep);
        	((View)findViewById(R.id.btnHivTest)).setOnClickListener(WebContentTo);
        	//((View)findViewById(R.id.btnFAQ)).setOnClickListener(WebContentTo);
        	((View)findViewById(R.id.btnMoreInfo)).setOnClickListener(WebContentTo);
        	((View)findViewById(R.id.btnToWeb)).setOnClickListener(WebExternal);
        	((View)findViewById(R.id.btnToBiblos)).setOnClickListener(WebInternal);
        	((View)findViewById(R.id.iFace)).setOnClickListener(WebExternal);
        	setContentLoader("donext");
        }
        
        else if(step==8){
        	setContentView(R.layout.step_send); 
        	((View)findViewById(R.id.btnSubmit)).setOnClickListener(SubmitForm);
        	setContentLoader("send");
        }
        
        else if(step==9){
        	setContentView(R.layout.step_sexselect); 
        	((View)findViewById(R.id.btnOne)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnTwo)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnThree)).setOnClickListener(OptionSelected);
        	((View)findViewById(R.id.btnFour)).setOnClickListener(OptionSelected);
        	setContentLoader("sexselect");
        }
        
        else if(step==10){
        	setContentView(R.layout.step_sexview); 
        	setContentLoader("sexview");
        }
        
        else if(step==11){
        	setContentView(R.layout.step_sexinfo); 
        	((View)findViewById(R.id.btnMoreInfo)).setOnClickListener(WebContentTo);
        	((View)findViewById(R.id.btnToBiblos)).setOnClickListener(WebInternal);
        	//((View)findViewById(R.id.btnWikiInfo)).setOnClickListener(WebContentTo);
        	setContentLoader("sexinfo");
        }
        
        else {
        	resetWizard();
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, entrez.class);
    		startActivityForResult(myIntent, 0);
        	finish();
        }
        
        
        ((View)findViewById(R.id.btnNavBack)).setOnClickListener(NavBack);
        ((View)findViewById(R.id.btnNavEnd)).setOnClickListener(NavEnd);
    }
    
    public void setContentLoader(String id) {
    	if (id.equalsIgnoreCase("three")) {
    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llQuestionArea);
    		int self = content[1];
    		int partner = content [2];
    		int mode = content[0]; 		
    		sxla.ConstructSexList(self, partner, mode);
    	}
    	
    	if (id.equalsIgnoreCase("four")) {
    		if (aindex>=4){
    			navigateNext();
    		} else {
    			if (activities[aindex]==0) {
    				navigateSelf();
    			} else {
    	    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llContextArea);
    	    		sxla.enableListClick(false);
    	    		sxla.enableItemBackground(false);
    	    		int askprotection = sxla.getUsedDatabase().queryInt(activities[aindex], "PRO");
    		    	if (askprotection>0) { 
    		    		sxla.ConstructSexListByIndex(activities[aindex]);
		    		} else {
		    			navigateSelf();
		    		}	
    			}
    		}

    	}
    	
    	if (id.equalsIgnoreCase("review")) {
    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llQuestionArea);
	    	sxla.ConstructReview(content, activities, barrier);
    	}
    	
    	if (id.equalsIgnoreCase("results")) {
    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llQuestionArea);
    		printview = (View) findViewById(R.id.llQuestionArea);
	    	sxla.ConstructResults(content, activities, barrier);
    	}
    	
    	if (id.equalsIgnoreCase("sexselect")) {

    	}
    	
    	if (id.equalsIgnoreCase("sexview")) {
    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llQuestionArea);
    		printview = (View) findViewById(R.id.llQuestionArea);
	    	sxla.ConstructCoupleFilteredList(content);
    	}
    	
    	if (id.equalsIgnoreCase("sexinfo")) {
    		sxla = new SexListAdaptor(getApplicationContext(),this,R.id.llQuestionArea);
    		printview = (View) findViewById(R.id.llQuestionArea);
    		sxla.ConstructActivityInfo(content);
    	}
    	
    	if (id.equalsIgnoreCase("donext")) {
    		
    	}
    	
    	if (id.equalsIgnoreCase("send")) {
    		
    	}
    }
    
    public void enableGoNext() {
    	((View)findViewById(R.id.btnNavNext)).setVisibility(0);
    }
    
    public void disableGoNext() {
    	((View)findViewById(R.id.btnNavNext)).setVisibility(4);
    }
    
    public OnClickListener SubmitForm = new OnClickListener() {
        public void onClick(View v) {
        	age = ((EditText)findViewById(R.id.eAge)).getText().toString();
        	country = ((EditText)findViewById(R.id.eCountry)).getText().toString();
        	
        	if (age.length()<1) {
        		Toast toast2 = Toast.makeText(getApplicationContext(), getString(R.string.m_send_mustfillall), Toast.LENGTH_LONG);
        		toast2.show();
        		return;
        	}
        	
        	if (country.length()<1) {
        		Toast toast2 = Toast.makeText(getApplicationContext(), getString(R.string.m_send_mustfillall), Toast.LENGTH_LONG);
        		toast2.show();
        		return;
        	}
        	
        	int paage = Integer.parseInt(age);
        	if (!((paage>0)&&(paage<100))) {
        		Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.m_send_age_wrong), Toast.LENGTH_LONG);
        		toast.show();
        	} else {
        		toSubmitForm(v);
        	}
        }
    };
}