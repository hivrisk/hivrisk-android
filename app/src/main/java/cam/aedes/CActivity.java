package cam.aedes;

import org.xml.sax.InputSource;

import cam.async.AsyncFeatures;
import android.app.Activity;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;

public class CActivity extends Activity implements AsyncFeatures{
	
	public int Elementid;
	public boolean ErrorOcurred;
	public static String PREFS_NAME = "CAM_AEDES_APP";
	public CDeviceMetrics cdm;
	
	public void completed_download_callback(String param, String result, String adparam) {
		// TODO Auto-generated method stub
		
	}

	
	public void storeData(String key, int val){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, val);
        editor.commit();
    }
	
	public void storeData(String key, long val){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, val);
        editor.commit();
    }
	
	public void storeData(String key, String str){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, str);
        editor.commit();
    }
    
	public void resetKey(String key){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }
    
	public void resetAppStoreSetting(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
    
	public int getDataInt(String key){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getInt(key, 0);
    }
    
    public String getDataString(String key){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(key, "");
    }
    
    public String getUniqueTimeStampSignature(long timestamp) {
    	String android_id = Secure.getString(getBaseContext().getContentResolver(),
                Secure.ANDROID_ID); 
    	return android_id+timestamp;
    }


	public void completed_download_callback(InputSource param, String result,
			String adparam) {
		// TODO Auto-generated method stub
		
	}


	public void completed_download_callback(String... result) {
		// TODO Auto-generated method stub
		
	}


	public void error_download_callback(String param, String result,
			String adparam) {
		// TODO Auto-generated method stub
		
	}
}
