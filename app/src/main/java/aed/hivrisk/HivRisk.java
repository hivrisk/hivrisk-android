package aed.hivrisk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cam.aedes.CActivity;
import cam.aedes.DownloadFilesTask;
import cam.hivrisk.SexDatabase;


public class HivRisk extends CActivity {
	
	public static String PREFS_NAME = "CAM_AEDES_HIVRISK";
	public String haslicensed = "";
	public boolean agreed = false;
	public long agreed_date;
	public int step = 0;
	public int content[] = new int[11];
	public static int activities[] = new int[5];
	public int barrier[] = new int[5];
	public int eyaculation[] = new int[5];
	public int aindex = 0;
	public Map<Integer, Integer> SexMap = new HashMap<Integer, Integer>();
	public SexListAdaptor sxla;
	public int selected = 0;
	public int prot = 0;
	public int eyac = 0;
	public String age;
	public String country;
	public View printview;
	public String currlang = "en";
	final String tego = "t3g0ac3l1t3g0d31";
	public boolean simplereturn = false;
		
    public OnClickListener StartWizard = new OnClickListener() {
        public void onClick(View v) {
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, Wizard.class);
    		startActivityForResult(myIntent, 0);
    		finish();
        }
    };
	
    public OnClickListener ViewDisclaimer = new OnClickListener() {
        public void onClick(View v) {
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, Disclaimer.class);
    		startActivityForResult(myIntent, 0);
    		finish();
        }
    };
    
    public OnClickListener AgreeDisclaimer = new OnClickListener() {
        public void onClick(View v) {
        	storeData("disclaimer", 1);
        	long sigtime = System.currentTimeMillis();
        	storeData("date_agreed", sigtime);
        	storeData("disclaimer", 1);
        	storeData("signature", getUniqueTimeStampSignature(sigtime));
        	//checkLicense();
        	storeData("license", getUniqueTimeStampSignature(sigtime));
        	Log.v("SIGNATURE_ID",getUniqueTimeStampSignature(sigtime));
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, entrez.class);
    		startActivityForResult(myIntent, 0);
        	finish();
        }
    };
       
    public OnClickListener DontDisclaimer = new OnClickListener() {
        public void onClick(View v) {
        	storeData("disclaimer", 0);
        	resetKey("date_agreed");
        	resetKey("signature");
        	resetKey("license");
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, entrez.class);
    		startActivityForResult(myIntent, 0);
        	finish();
        }
    };
    
    public OnClickListener BuyApp = new OnClickListener() {
        public void onClick(View v) {
        	Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "http://market.android.com/details?id=" + getPackageName()));
            startActivity(marketIntent);
            finish();
        }
    };
    
    public OnClickListener NavEnd = new OnClickListener() {
        public void onClick(View v) {
        	resetWizard();
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, entrez.class);
    		startActivityForResult(myIntent, 0);
        	finish();
        }
    };
    
    public OnClickListener NavBack = new OnClickListener() {
        public void onClick(View v) {
        	if (simplereturn) {
        		finish();
        		return;
        	}
        	Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, Wizard.class);
    		if (step==9) { myIntent.putExtra("step", 0); }
    		else { myIntent.putExtra("step", step-1); }
    		myIntent.putExtra("content", content);
    		myIntent.putExtra("aindex", aindex);
    		myIntent.putExtra("barrier", barrier);
    		startActivityForResult(myIntent, 0);
        	finish();
        }
    };
    
    public OnClickListener OptionSelected = new OnClickListener() {
        public void onClick(View v) {
        	String id = v.getId()+"";
        	String tag = v.getTag()+"";
        	contentInteractionDispatcher(id, tag);

        }
    };
    
    public OnClickListener SexOptionSelected = new OnClickListener() {
        public void onClick(View v) {
        	SelectSexActivity(v);        	
        }
    };
        
    public OnClickListener ProtectionSelected = new OnClickListener() {
        public void onClick(View v) {
        	SelectProtection(Integer.parseInt((String)v.getTag()));      	
        }
    };
    
    public OnClickListener FloatingContent = new OnClickListener() {
        public void onClick(View v) {
        	//SelectProtection(Integer.parseInt((String)v.getTag()));  
        	showWindowContent(v.getTag().toString());
        }
    };
    
    public OnClickListener NavNextStep = new OnClickListener() {
        public void onClick(View v) {
        	navigateNext();
        }
    };
    
    public OnClickListener NavNext = new OnClickListener() {
        public void onClick(View v) {
        	ComputeSexActivities();
        }
    };
    
    public OnClickListener SnapShot = new OnClickListener() {
        public void onClick(View v) {
        	saveViewSnapshot(printview);
        }
    };
    
    public OnClickListener WebContentTo = new OnClickListener() {
        public void onClick(View v) {
        	LaunchWebContent(v.getTag().toString());
        }
    };
    
    public OnClickListener WebExternal = new OnClickListener() {
        public void onClick(View v) {
        	LaunchWebExternal(v.getTag().toString());
        }
    };
    
    public OnClickListener WebInternal = new OnClickListener() {
        public void onClick(View v) {
        	LaunchWebInternal(v.getTag().toString());
        }
    };
        
    public void enableGoNext() {
    	
    }
    
    public void disableGoNext() {
    	
    }
    
    public void LaunchWebContent(String contentid) {
    	String contenturl = getWebContentUrl(contentid);
    	Context mContext = getApplicationContext();
		Intent myIntent = new Intent(mContext, WebContent.class);
		myIntent.putExtra("url", contenturl);
		startActivityForResult(myIntent, 0);
    }
    
    public void LaunchWebExternal(String contentid) {
    	String contenturl = getString(getResources().getIdentifier("url_"+contentid, "string", getPackageName()));
    	Uri uri = Uri.parse(contenturl);
		startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
    }
    
    public void LaunchWebInternal(String contentid) {
    	String locale = getResources().getConfiguration().locale.getLanguage();
    	String localeid = contentid + "_" + locale + ".html";
    	String localurl = "file:///android_asset/" + contentid + "_" + locale + ".html";
    	String genericurl = "file:///android_asset/" + contentid + ".html";
    	String contenturl = genericurl;
    	Context mContext = getApplicationContext();
    	
        if (checkIfInAssets(localeid)) contenturl = localurl;
    	
		Intent myIntent = new Intent(mContext, WebContent.class);
		myIntent.putExtra("url", contenturl);
		startActivityForResult(myIntent, 0);
    }
    
    
    public String getWebContentUrl(String slug) {
    	String locale = getResources().getConfiguration().locale.getLanguage();
    	if (locale!="") { currlang=locale; }
    	String baseurl = getString(R.string.url_mobile)+"resources.php?name="+slug+"&lang="+currlang;
    	return baseurl;	
    }
       
    public void SelectSexActivity(View v) {
    	int id = v.getId();
    	int tag = Integer.parseInt((String) v.getTag());
    	Log.v("SELECTED MAP SEX:::", "id "+id+" tag "+tag);
    	if (SexMap.containsKey(id)) {
    		v.setBackgroundResource(R.drawable.border3);
    		SexMap.remove(id);
    		selected--;
    	} else {
    		if (selected<5) {
	    		v.setBackgroundResource(R.drawable.border5);
	    		SexMap.put(id, tag);
	    		selected++;
    		}
    	}
    	if (selected>0) {
    		enableGoNext();
    	} else {
    		disableGoNext();
    	}
    	Log.v("SELECTED count:::", selected+"#");
    	
    }
    
    public void ComputeSexActivities() {
    	volcateSexMap(SexMap, activities);
    	//evaluateSexActivities(activities);
    	navigateNext();
    }
    
    public static void volcateSexMap(Map mp, int[] aint) {
        Iterator it = mp.entrySet().iterator();
        int x = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Log.v("VOLCATE::: ",pairs.getKey() + " = " + pairs.getValue());
            aint[x] = (Integer) pairs.getValue();
            x++;
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    public void evaluateSexActivities(int[] aint) {
    	boolean protectionCandidate = false;
    	for(int x = 0; x<aint.length; x++) {
    		if (aint[x]!=0) {
		    	SexDatabase sx = sxla.getUsedDatabase();
		    	int a = sx.queryInt(aint[x], "PRO");
		    	Log.v("RESULT PRO from "+aint[x]+":::",""+a);
		    	if (a>0) { protectionCandidate = true; }
    		}
    	}
    	if (protectionCandidate) {
    		content[step] = 1;
    		Log.v("RESULT PRO :::", "ASK FOR PROTECTION");
    	} else {
    		content[step] = -1;
    		Log.v("RESULT PRO :::", "DONT ASK FOR PROTECTION");
    	}
    }
    
    
    public void resetWizard() {
    	step=0;
    }
    
    public void SelectProtection(int tag){
    	Log.v("SELECTED PROTECTION FOR ACTIVITY ("+activities[aindex]+"):::", "tag "+tag);
    	barrier[aindex]=tag;
    	if (aindex<4){
    		navigateSelf();
    	} else {
    		navigateNext();
    	}
    }
    
    
    public void navigateNext(){
    	Context mContext = getApplicationContext();
		Intent myIntent = new Intent(mContext, Wizard.class);
		myIntent.putExtra("step", step+1);
		myIntent.putExtra("content", content);
		myIntent.putExtra("barrier", barrier);
		myIntent.putExtra("aindex", aindex);
		startActivityForResult(myIntent, 0);
    	finish();
    }
    
    public void navigateSelf(){
    	Context mContext = getApplicationContext();
		Intent myIntent = new Intent(mContext, Wizard.class);
		myIntent.putExtra("step", step);
		myIntent.putExtra("content", content);
		myIntent.putExtra("aindex", aindex+1);
		myIntent.putExtra("barrier", barrier);
		startActivityForResult(myIntent, 0);
    	finish();
    }
    
    public void contentInteractionDispatcher(String name, String value){	
        	if (value.equalsIgnoreCase("1")){
        		content[step] = 1;
        	}
        	if (value.equalsIgnoreCase("2")){
        		content[step] = 2;
        	}
        	
        	if (value.equalsIgnoreCase("3")&&step==0){
        		content[step] = 3;
        		step=8;
        	}
        	
        	if (value.equalsIgnoreCase("3")){
        		content[step] = 3;
        	}
        	
        	Log.v("VALUE:::::", value);
        	Log.v("STEP::::", step+"");
        	if (step==10){
        		content[step] = Integer.parseInt(value);
        	}
        	
        	
    	navigateNext();
    }
    
    public void showWindowContent(String content) {
    	String msg = "";
    	String title = "";
    	if (getResources().getIdentifier("m_tip_" + content + "_title", "string", getPackageName())!=0){
			title = getString(getResources().getIdentifier("m_tip_" + content + "_title", "string", getPackageName()));
		}
    	if (getResources().getIdentifier("m_tip_" + content + "_msg", "string", getPackageName())!=0){
			msg = getString(getResources().getIdentifier("m_tip_" + content + "_msg", "string", getPackageName()));
		}
    	msgbox(msg, title);
    }
    
    public void msgbox(String message,String title) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);              
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
    
    public void saveViewSnapshot(View v) {
    	boolean success = true;
    	SimpleDateFormat s = new SimpleDateFormat("ddMMyyhhmm");
    	String format = s.format(new Date());
    	String filename = "hivresults_" + format + ".jpg";
    	String done = getString(R.string.m_save_done_0) + " "+ filename + " " + getString(R.string.m_save_done_1);
    	String dont = getString(R.string.m_save_dont);
    	Drawable pb = v.getBackground();
    	v.setBackgroundColor(Color.WHITE);
    	Bitmap viewBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(viewBitmap);
    	v.draw(canvas);
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    	viewBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

    	File f = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
    	try {
			f.createNewFile();
	    	FileOutputStream fo = new FileOutputStream(f);
	    	fo.write(bytes.toByteArray());
		} catch (IOException e) {
			Log.v("Error on SAVE IMG", e.toString());
			e.printStackTrace();
			success = false;
		}
		v.setBackgroundDrawable(pb);
		if (success) {
			Toast nt = Toast.makeText(getApplicationContext(), done, Toast.LENGTH_LONG);
			nt.show();
		} else {
			Toast nt = Toast.makeText(getApplicationContext(), dont, Toast.LENGTH_LONG);
			nt.show();
		}
    }
    
    public void toSubmitForm(View v) {
    	TextView tv = (TextView)v;
    	String pre = tv.getText().toString();
    	tv.setText(R.string.m_send_wait);
    	tv.setEnabled(false);
    	String sendstr = constructSendString();
    	wwwConsult(sendstr);
    	tv.setText(pre);
    	tv.setEnabled(true);
    }
    
    public void wwwConsult(String sendstr) {
    	AsyncTask<String, Void, String> dft = new DownloadFilesTask(this,sendstr);
    	dft.execute(sendstr,"sendform","sendform");  

    }
    
    public void completed_download_callback(String param, String result, String adparam) {
    	String sent = getString(R.string.m_send_sent);
    	String couldnt = getString(R.string.m_send_couldnt);
    	if (adparam.equalsIgnoreCase("sendform")) {
    		Log.v("suposed param:::", param);
    		Log.v("suposed result:::", result);
    		if (param.equalsIgnoreCase("done")) {
    			Toast nt = Toast.makeText(getApplicationContext(), sent, Toast.LENGTH_LONG);
    			nt.show();
    		} else {
    			Toast nt = Toast.makeText(getApplicationContext(), couldnt, Toast.LENGTH_LONG);
    			nt.show();
    		}
    		
    		Context mContext = getApplicationContext();
    		Intent myIntent = new Intent(mContext, Wizard.class);
    		myIntent.putExtra("step", step-1);
    		myIntent.putExtra("content", content);
    		startActivityForResult(myIntent, 0);
        	finish();
    	}
    }
       
    public String constructSendString() {
    	String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    	String www = "http://www.hivrisk.org/contribute/results.php?";
    	String sig = "sig=" + md5(tego);
    	String id = "id="+deviceId;
    	String mod = "mod=";
    	String ver = "ver=2";
    	String plat = "platform=android";
    	String act = "act=";
    	String prot = "pro=";
    	String aet = "aet="+age;
    	String nat = "nat="+ Uri.encode(country, "UTF-8");
    	SimpleDateFormat s = new SimpleDateFormat("ddMMyyhhmmss");
    	String tst = "tst=" + s.format(new Date());
    	mod += (content[0]+"")+(content[1]+"")+(content[2]+"");
    	for (int x=0;x<activities.length;x++) {
    		act += (stdDblDigitStr(activities[x]));
    		prot += (stdDblDigitStr(barrier[x]));
    	}
    	www += sig + "&" + id + "&" + mod + "&" + act + "&" + prot + "&" + aet + "&" + nat + "&" + tst + "&" + ver + "&" + plat;
    	Log.v("www request:::", www);
    	return www;
    }
    
    public String stdDblDigitStr(int val) {
    	String dd = val+"";
    	if (dd.length()<2) { dd = "0"+dd; }
    	return dd;
    }
    
    
    public static final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
     
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
     
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    
    AssetManager am;
    List<String> mapList = null;

    /**
     * Checks if an asset exists.
     *
     * @param assetName
     * @return boolean - true if there is an asset with that name.
     */
    public boolean checkIfInAssets(String assetName) {
        if (mapList == null) {
            am = getAssets();
            try {
                mapList = Arrays.asList(am.list(""));
            } catch (IOException e) {
            }
        }
        return mapList.contains(assetName) ? true : false;
    }
    

    
    

}
