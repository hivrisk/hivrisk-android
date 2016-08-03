package cam.hivrisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import aed.hivrisk.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SexDatabase extends SQLiteOpenHelper {
	private Context self;
	private static final String DATABASE_NAME = "sexdb";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "hivrisk";
    SQLiteDatabase mydb;
	
	int mf = 0;
	int mm = 0;
	int fm = 0;
	int ff = 0;
	String xx = "";

    public SexDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        self = context;
        mydb = this.getWritableDatabase();
        String raw = LoadText(R.raw.sexactiv);
        String raw2 = LoadText(R.raw.sexcontent);
        mydb.execSQL("DROP TABLE IF EXISTS sexactiv");
        mydb.execSQL(raw);
        mydb.execSQL(raw2);
        Log.v("LENGTH:::",DatabaseUtils.queryNumEntries(mydb, "sexactiv")+"");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}
	

	
	public Cursor getFilteredList(int self, int partner, int mode){
		if (mode==1) {
			if (self==2&&partner==2) {	mm = 1; xx="mm"; }
			if (self==2&&partner==1) {	mf = 1;	xx="mf"; }
			if (self==1&&partner==2) {	fm = 1;	xx="fm"; }
			if (self==1&&partner==1) {	ff = 1;	xx="ff"; }
		} else if (mode==2) {
			if (self==2&&partner==2) {	mm = 1;	xx="mm"; }
			if (self==2&&partner==1) {	fm = 1;	xx="fm"; }
			if (self==1&&partner==2) {	mf = 1;	xx="mf"; }
			if (self==1&&partner==1) {	ff = 1;	xx="ff"; }
		}
		
		String search = "SELECT * FROM  `sexactiv` WHERE `" + xx + "`=1";
		Log.v("QUERY:::",search);
		return mydb.rawQuery(search, null);
		//return mydb.query("sexactiv",null,null, null, null, null,null);
		
	}
	
	public Cursor getIdItem(int item){
		String search = "SELECT * FROM  `sexactiv` WHERE `id`="+item;
		Log.v("QUERY:::",search);
		return mydb.rawQuery(search, null);
		//return mydb.query("sexactiv",null,null, null, null, null,null);	
	}
	
	public Cursor getPreferenceFilter(int item){		
		String search = "SELECT * FROM  `sexactiv` ";
		if (item==0) {
			search += "WHERE `ff`=1";
		}
		else if (item==1) {
			search += "WHERE `fm`=1 OR `mf`=1";
		}
		else if (item==2) {
			search += "WHERE `mm`=1";
		}
		else if (item==3) {
			search = "SELECT * FROM  `sexactiv` ";
		}
		Log.v("QUERY:::",search);
		return mydb.rawQuery(search, null);
	}

	public String LoadText(int resourceId) {
	    InputStream is = self.getResources().openRawResource(resourceId);  
	    if (is==null){ Log.v("INPUTSTREAM::","NULLLLL");}
	    return StreamToString(is);
	}
	
	public String StreamToString(InputStream is) {
		if (is != null) {
		    byte[] bytes = new byte[2500];
		    StringBuilder x = new StringBuilder();
		    int numRead = 0;
		    try {
				while ((numRead = is.read(bytes)) >= 0) {
				    x.append(new String(bytes, 0, numRead));
				    //is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.v("Error IO EXC JSON", e.toString());
				return "";
			} catch (Exception e) {
				e.printStackTrace();
				Log.v("Error Generic EXC JSON", e.toString());
				return "";
			}
		  
		  return x.toString();
		}  else { 
			return "";
		}
    }
	
	public int queryInt(int id, String column){
		String query = "SELECT * FROM sexactiv WHERE id="+id;
		Cursor c = mydb.rawQuery(query, null);
		c.moveToFirst();
		return c.getInt(8);
	}
}