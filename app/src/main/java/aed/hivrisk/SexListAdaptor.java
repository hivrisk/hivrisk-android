package aed.hivrisk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import cam.aedes.CActivity;
import cam.aedes.CDeviceMetrics;
import cam.aedes.CInterface;
import cam.hivrisk.SexDatabase;

public class SexListAdaptor {
	
	SexDatabase sx;
	CInterface ci;
	HivRisk ca;
	int parent;
	Context context;
	CDeviceMetrics cdm;
	boolean clickenabled = true;
	boolean usebackground = true;
	boolean demo = false;
	int smimgsize = 48;
	int midimgsize = 64;
	int bigimgsize = 72;
	
	public SexListAdaptor(Context context, HivRisk ca, int parent){
		this.context = context;
		this.parent = parent;
		this.ca = ca;
		sx = new SexDatabase(context);
		ci= new CInterface(context,ca);
		cdm = new CDeviceMetrics(context, ca);
		smimgsize = cdm.DPToPixels(smimgsize);
		midimgsize = cdm.DPToPixels(midimgsize);
		bigimgsize = cdm.DPToPixels(bigimgsize);
	}
	
	public void enableListClick(boolean enable) {
		clickenabled = enable;
	}
	
	public void enableItemBackground(boolean enable) {
		usebackground = enable;
	}
	
	public void demoMode(boolean enable) {
		demo = enable;
	}
	
	public void closeDB() {
		sx.close();
	}
	
	public void ConstructSexList(int self, int partner, int mode){
		Cursor c = sx.getFilteredList(self, partner, mode);
		Log.v("CURSOR COUNT:::", c.getCount()+" .");
		ComputeList(c);

	}
	
	public void ConstructSexListByIndex(int index){
		Cursor c = sx.getIdItem(index);
		Log.v("CURSOR COUNT:::", c.getCount()+" .");
		ComputeList(c);

	}
	
	public void ConstructReview(int[]steps, int[] activities, int[] pro){
		ComputeReviewHeader(steps);
		for(int i = 0; i<activities.length;i++) {
			Cursor c = sx.getIdItem(activities[i]);
			ComputeReviewItem(c, pro[i]);
		}
		DrawReviewFooter(steps);
	}
	
	public void ConstructCoupleFilteredList(int[]steps){
		Cursor c = sx.getPreferenceFilter(steps[9]);
		ComputeInfoList(c);
	}
	
	public void ConstructActivityInfo(int[]steps){
		Cursor c = sx.getIdItem(steps[10]);
		ComputeInfoItem(c);
	}
	
	public void ConstructResults(int[]steps, int[] activities, int[] pro){
		int riskave = RiskCalculator(activities, pro);
		ComputeResultsHeader(steps,riskave);
		for(int i = 0; i<activities.length;i++) {
			Cursor c = sx.getIdItem(activities[i]);
			ComputeResultItem(c, pro[i]);
		}
		DrawResultsFooter(steps);
	}
	
	public int RiskCalculator(int[] activities, int[]pro) {
		int maxrisk = 0;
		int arisk = 0;
		for(int i = 0; i<activities.length;i++) {
			Cursor c = sx.getIdItem(activities[i]);
			for(int ii = 0; ii<c.getCount();ii++) {
				c.moveToPosition(ii);
				int risk = c.getInt(12);
				Log.v("RISK CALCULATOR ACT::: ", "#"+activities[i]+ " risk:"+risk+" pro:"+pro[i]);
				if (pro[i]==1) { arisk = 0; }
				if (pro[i]==2) { arisk = risk; }
				if (pro[i]==3) { arisk = risk; }
			}
			if (arisk > maxrisk) { maxrisk = arisk; }	
		}
		return maxrisk;
		
	}
	
	public void ComputeList(Cursor c) {
		for(int i = 0; i<c.getCount();i++) {
			c.moveToPosition(i);
			String name = c.getString(1).toLowerCase();
			String title = name;
			String caption = name;
			int imgid = R.drawable.frot_cud_hug;
			if (ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName())!=0){
				title = context.getString(ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName()));
				
			}
			if (ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName())!=0){
				caption = context.getString(ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName()));
				
			}
			if (ca.getResources().getIdentifier(name, "drawable", context.getPackageName())!=0){
				imgid = ca.getResources().getIdentifier(name, "drawable", context.getPackageName());
				
			}
			String tag = c.getString(0);
			DrawList(imgid, tag, title, caption);

		}
	}
	
	public void ComputeInfoList(Cursor c) {
		for(int i = 0; i<c.getCount();i++) {
			c.moveToPosition(i);
			String name = c.getString(1).toLowerCase();
			String title = name;
			String caption = name;
			int imgid = R.drawable.frot_cud_hug;
			if (ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName())!=0){
				title = context.getString(ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName()));
				
			}
			if (ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName())!=0){
				caption = context.getString(ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName()));
				
			}
			if (ca.getResources().getIdentifier(name, "drawable", context.getPackageName())!=0){
				imgid = ca.getResources().getIdentifier(name, "drawable", context.getPackageName());
				
			}
			String tag = c.getString(0);
			DrawInfoList(imgid, tag, title, caption);

		}
	}
	
	public void DrawList(int imgid, String tag, String title, String caption) {
		LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
		llz.setPadding(3,3,3,3);
		LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
		if (usebackground) { lla.setBackgroundResource(R.drawable.border3); }
		lla.setPadding(4,4,4,4);
		lla.setTag(tag);
		if (clickenabled) {	lla.setOnClickListener(ca.SexOptionSelected); }
			LinearLayout lli = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				ci.NewImage(lli.getId(), midimgsize, midimgsize, imgid);
			LinearLayout llr = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
			llr.setPadding(6, 2, 2, 2);
				ci.NewTV(llr.getId(), R.style.p1, title);
				ci.NewTV(llr.getId(), R.style.p2, caption);
	}
	
	public void DrawInfoList(int imgid, String tag, String title, String caption) {
		LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
		llz.setPadding(3,3,3,3);
		LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
		if (usebackground) { lla.setBackgroundResource(R.drawable.border3); }
		lla.setPadding(4,4,4,4);
		lla.setTag(tag);
		if (clickenabled) {	lla.setOnClickListener(ca.OptionSelected); }
			LinearLayout lli = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				ci.NewImage(lli.getId(), midimgsize, midimgsize, imgid);
			LinearLayout llr = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
			llr.setPadding(6, 2, 2, 2);
				ci.NewTV(llr.getId(), R.style.p1, title);
				ci.NewTV(llr.getId(), R.style.p2, caption);
	}
	
	public void ComputeReviewItem(Cursor c, int pro) {
		for(int i = 0; i<c.getCount();i++) {
			c.moveToPosition(i);
			String name = c.getString(1).toLowerCase();
			String title = name;
			String caption = "";
			int imgid = R.drawable.frot_cud_hug;
			if (pro==1) {
				caption = context.getString(R.string.m_sem_protected);
			}
			if (pro==2) {
				caption = context.getString(R.string.m_sem_broken);
			}
			if (pro==3) {
				caption = context.getString(R.string.m_sem_unprotected);
			}
			if (ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName())!=0){
				title = context.getString(ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName()));
				
			}
			
			if (ca.getResources().getIdentifier(name, "drawable", context.getPackageName())!=0){
				imgid = ca.getResources().getIdentifier(name, "drawable", context.getPackageName());
				
			}
			
			String tag = c.getString(0);
			DrawReviewItem(imgid, pro, tag, title, caption);

		}
	}
	
	public void DrawReviewItem(int imgid, int pro, String tag, String title, String caption) {
			LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			llz.setPadding(3,3,3,3);
			LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla.setPadding(4,4,4,4);
			lla.setTag(tag);
				LinearLayout lli = ci.NewView(lla.getId(), smimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
					ci.NewImage(lli.getId(), smimgsize, smimgsize, imgid);
				LinearLayout llr = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr.setPadding(4, 1, 1, 1);
					ci.NewTV(llr.getId(), R.style.p1, title);
					if (pro>0) {
						ci.NewTV(llr.getId(), R.style.p2, caption);
					}
	}
	
	public void ComputeResultItem(Cursor c, int pro) {
		for(int i = 0; i<c.getCount();i++) {
			c.moveToPosition(i);
			String name = c.getString(1).toLowerCase();
			int id = c.getInt(0);
			int hivrisk = c.getInt(12);
			int canprotect = c.getInt(8);
			int hepv = c.getInt(14);
			int hpap = c.getInt(15);
			int herv = c.getInt(16);
			int gono = c.getInt(17);
			int sifi = c.getInt(18);
			int para = c.getInt(19);
			int feor = c.getInt(20);
			int clam = c.getInt(21);
			int bact = c.getInt(22);
			String title = name;
			String safe = "";
			String evidencelabel = context.getString(R.string.m_results_evidence); 
			String evidence = "";
			String couldget = context.getString(R.string.m_results_could_get);
			String otherstd = "";
			String haspro = "";
			int imgid = R.drawable.frot_cud_hug;
			int dodo  = R.drawable.dodo;
			int imgrisk = R.drawable.riskn;
			if (canprotect>0) {
				if (pro==1) {
					if ( hivrisk == 0 ) { 
						evidence = context.getString(R.string.m_results_evidence_norisk); 
						dodo  = R.drawable.dodo;
					} else {
						evidence = context.getString(R.string.m_results_evidence_protected);
						safe = context.getString(R.string.m_results_safe);
						haspro = context.getString(R.string.m_results_protected);
						dodo  = R.drawable.dodo;
					}
					
				} else {
					safe = context.getString(R.string.m_results_unsafe);
					haspro = context.getString(R.string.m_results_unprotected);
					dodo  = R.drawable.dont;
					if ( hivrisk == 0 ) { evidence = context.getString(R.string.m_results_evidence_norisk); imgrisk = R.drawable.riskn; }
					if ( hivrisk == 1 ) { evidence = context.getString(R.string.m_results_evidence_verylow); imgrisk = R.drawable.risk0; }
					if ( hivrisk == 2 ) { evidence = context.getString(R.string.m_results_evidence_low); imgrisk = R.drawable.risk1; }
					if ( hivrisk == 3 ) { evidence = context.getString(R.string.m_results_evidence_considerable); imgrisk = R.drawable.risk2; }
					if ( hivrisk == 4 ) { evidence = context.getString(R.string.m_results_evidence_high);  imgrisk = R.drawable.risk3;}
					
					if (hepv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hepb)); }
					if (hpap>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hpap)); }
					if (herv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_herv)); }
					if (gono>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_gono)); }
					if (sifi>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_sifi)); }
					if (para>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_para)); }
					if (feor>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_feor)); }
					if (clam>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_clam)); }
					if (bact>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_bact)); }
				}
			} else {
				if ( hivrisk == 0 ) { evidence = context.getString(R.string.m_results_evidence_norisk); }
				if ( hivrisk == 1 ) { evidence = context.getString(R.string.m_results_evidence_verylow); }
				if ( hivrisk == 2 ) { evidence = context.getString(R.string.m_results_evidence_low); }
				if ( hivrisk == 3 ) { evidence = context.getString(R.string.m_results_evidence_considerable); }
				if ( hivrisk == 4 ) { evidence = context.getString(R.string.m_results_evidence_high); }
				
				if (hepv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hepb)); }
				if (hpap>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hpap)); }
				if (herv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_herv)); }
				if (gono>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_gono)); }
				if (sifi>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_sifi)); }
				if (para>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_para)); }
				if (feor>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_feor)); }
				if (clam>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_clam)); }
				if (bact>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_bact)); }
			}
			
			
			if (ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName())!=0){
				title = context.getString(ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName()));
				
			}
			
			if (ca.getResources().getIdentifier(name, "drawable", context.getPackageName())!=0){
				imgid = ca.getResources().getIdentifier(name, "drawable", context.getPackageName());
				
			}
			

			
			DrawResultItem(id, imgid, dodo, imgrisk, title, evidencelabel, evidence, safe, haspro, couldget, otherstd);


		}
	}
	
	public void ComputeInfoItem(Cursor c) {
		for(int i = 0; i<c.getCount();i++) {
			c.moveToPosition(i);
			String name = c.getString(1).toLowerCase();
			int hivrisk = c.getInt(12);
			int canprotect = c.getInt(8);
			int hepv = c.getInt(14);
			int hpap = c.getInt(15);
			int herv = c.getInt(16);
			int gono = c.getInt(17);
			int sifi = c.getInt(18);
			int para = c.getInt(19);
			int feor = c.getInt(20);
			int clam = c.getInt(21);
			int bact = c.getInt(22);
			String title = name;
			String caption = name;
			String evidencelabel = context.getString(R.string.m_results_evidence); 
			String evidence = "";
			String couldget = context.getString(R.string.m_results_could_get);
			String otherstd = "";
			String promethod = "";
			String about = "";
			String advise = "";
			String avoid = "";
			
			int imgid = R.drawable.frot_cud_hug;
			int imgrisk = R.drawable.riskn;
			int imgpro = R.drawable.condomok;
			
			if (ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName())!=0){
				caption = context.getString(ca.getResources().getIdentifier("t_"+name, "string", context.getPackageName()));
			}
			
			if (ca.getResources().getIdentifier("about_"+name, "string", context.getPackageName())!=0){
				about = context.getString(ca.getResources().getIdentifier("about_"+name, "string", context.getPackageName()));
			}
			
			if (ca.getResources().getIdentifier("advise_"+name, "string", context.getPackageName())!=0){
				advise = context.getString(ca.getResources().getIdentifier("advise_"+name, "string", context.getPackageName()));
			}
			
			if (ca.getResources().getIdentifier("avoid_"+name, "string", context.getPackageName())!=0){
				avoid = context.getString(ca.getResources().getIdentifier("avoid_"+name, "string", context.getPackageName()));
			}
			
			if (canprotect==1) {
				promethod = context.getString(R.string.m_results_protec_condom);
				evidencelabel = context.getString(R.string.m_results_evidence_un);
				imgpro = R.drawable.condomok;
			} else if (canprotect==2) {
				promethod = context.getString(R.string.m_results_protec_dam);
				evidencelabel = context.getString(R.string.m_results_evidence);
				imgpro = R.drawable.dam;
			} else if (canprotect==3) {
				promethod = context.getString(R.string.m_results_protec_glove);
				evidencelabel = context.getString(R.string.m_results_evidence);
				imgpro = R.drawable.glove;
			} else {
				evidencelabel = context.getString(R.string.m_results_evidence);
			}
			
			if ( hivrisk == 0 ) { evidence = context.getString(R.string.m_results_evidence_norisk); imgrisk = R.drawable.riskn; }
			if ( hivrisk == 1 ) { evidence = context.getString(R.string.m_results_evidence_verylow); imgrisk = R.drawable.risk0; }
			if ( hivrisk == 2 ) { evidence = context.getString(R.string.m_results_evidence_low); imgrisk = R.drawable.risk1; }
			if ( hivrisk == 3 ) { evidence = context.getString(R.string.m_results_evidence_considerable); imgrisk = R.drawable.risk2; }
			if ( hivrisk == 4 ) { evidence = context.getString(R.string.m_results_evidence_high);  imgrisk = R.drawable.risk3;}
			
			if (hepv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hepb)); }
			if (hpap>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_hpap)); }
			if (herv>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_herv)); }
			if (gono>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_gono)); }
			if (sifi>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_sifi)); }
			if (para>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_para)); }
			if (feor>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_feor)); }
			if (clam>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_clam)); }
			if (bact>0) { otherstd = plusStr(otherstd,context.getString(R.string.m_std_bact)); }
			
			
			if (ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName())!=0){
				title = context.getString(ca.getResources().getIdentifier("m_"+name, "string", context.getPackageName()));
				
			}
			
			if (ca.getResources().getIdentifier(name, "drawable", context.getPackageName())!=0){
				imgid = ca.getResources().getIdentifier(name, "drawable", context.getPackageName());
				
			}
			

			
			DrawInfoItem(imgid, imgrisk, imgpro, canprotect, title, caption, promethod, 
					evidencelabel, evidence, couldget, otherstd, about, advise, avoid);


		}
	}
	
	public void DrawResultItem(final int content_id, int imgid, int imgdo, int imgrisk, String title, String evidencelabel, String evidence, String safe, String pro, String couldget, String otherstd) {
		int stdimg = R.drawable.stds;
		int oimg = R.drawable.dr;
		LinearLayout llz0 = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz0.setPadding(0,0,0,15);
		
		LinearLayout llz = ci.NewView(llz0.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz.setPadding(3,3,3,3);
		llz.setBackgroundResource(R.drawable.border0);	
			LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla.setPadding(5,0,5,0);
				LinearLayout lli = ci.NewView(lla.getId(), smimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					LinearLayout lls = ci.NewView(lli.getId(), LayoutParams.FILL_PARENT, midimgsize, LinearLayout.HORIZONTAL);	
						lls.setGravity(Gravity.CENTER);
						ci.NewImage(lls.getId(), smimgsize, smimgsize, imgdo);
				LinearLayout llr = ci.NewView(lla.getId(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr.setPadding(4, 1, 1, 1);
					LinearLayout llb = ci.NewView(llr.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
					//llb.setBackgroundResource(R.drawable.border3);
					llb.setPadding(4,4,4,4);
						LinearLayout llw = ci.NewView(llb.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
							ci.NewImage(llw.getId(), smimgsize, smimgsize, imgid);
						LinearLayout llx = ci.NewView(llb.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
						llx.setPadding(6, 2, 2, 2);
							ci.NewTV(llx.getId(), R.style.p1, title);
							ci.NewTV(llx.getId(), R.style.p2, pro);
										
			LinearLayout lla2 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla2.setPadding(5,0,5,0);
				LinearLayout lli2 = ci.NewView(lla2.getId(), smimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli2.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli2.setPadding(0,10,0,0);
					LinearLayout llt = ci.NewView(lli2.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
						llt.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
						ci.NewImage(llt.getId(), smimgsize, smimgsize, imgrisk);	
				LinearLayout llr2 = ci.NewView(lla2.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr2.setPadding(4, 1, 1, 1);
					LinearLayout llu = ci.NewView(llr2.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llu.setPadding(5,5,0,5);
						ci.NewTV(llu.getId(), R.style.p2, evidencelabel);
						ci.NewTV(llu.getId(), R.style.p3, evidence);

			if (otherstd!="") {
			LinearLayout lla3 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla3.setPadding(5,0,5,0);
				LinearLayout lli3 = ci.NewView(lla3.getId(), smimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli3.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli3.setPadding(0,10,0,0);
					ci.NewImage(lli3.getId(), smimgsize, smimgsize, stdimg);
				LinearLayout llr3 = ci.NewView(lla3.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr3.setPadding(4, 1, 1, 1);
					LinearLayout llv = ci.NewView(llr3.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llv.setPadding(5,0,5,5);
						ci.NewTV(llv.getId(), R.style.p2, couldget);
						ci.NewTV(llv.getId(), R.style.p3, otherstd);
			}
			
			LinearLayout lla4 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla4.setPadding(5,5,5,5);
			lla4.setBackgroundResource(R.drawable.border35);	
				LinearLayout lli4 = ci.NewView(lla4.getId(), smimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli4.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli4.setPadding(0,10,0,0);
					ci.NewImage(lli4.getId(), smimgsize, smimgsize, oimg);
				LinearLayout llr4 = ci.NewView(lla4.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr4.setPadding(4, 1, 1, 1);
					LinearLayout llv4 = ci.NewView(llr4.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llv4.setPadding(5,0,5,5);
						ci.NewTV(llv4.getId(), R.style.p1, context.getString(R.string.m_results_protec_strategies));
			lla4.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Context mContext = ca.getApplicationContext();
					Intent myIntent = new Intent(mContext, Wizard.class);
					myIntent.putExtra("step", 11);
			    	myIntent.putExtra("barrier", 0);
					myIntent.putExtra("aindex", 0);
					myIntent.putExtra("simplereturn", true);
					ca.content[10]=content_id;
					myIntent.putExtra("content", ca.content);
					ca.startActivityForResult(myIntent, 0);	
				}});
				    	
		
	}
	
	public void DrawInfoItem(int imgid, int imgrisk, int imgpro, int canprotect, String title, 
			String caption, String promethod, String evidencelabel, String evidence, String couldget, String otherstd,
			String about, String advise, String avoid) {
		String prostrat = context.getString(R.string.m_results_protec_strategies);
		String about_ = context.getString(R.string.m_about);
		String avoid_ = context.getString(R.string.m_avoid);
		String advise_ = context.getString(R.string.m_advise);
		int stdimg = R.drawable.stds;
		
		LinearLayout llz0 = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz0.setPadding(0,0,0,15);
		
		LinearLayout llz = ci.NewView(llz0.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz.setPadding(3,3,3,3);
			//ACTIVITY TITLE
			LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla.setPadding(5,5,5,5);
			lla.setBackgroundResource(R.drawable.border0);	
				LinearLayout lli = ci.NewView(lla.getId(), bigimgsize, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
				lli.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					LinearLayout lls = ci.NewView(lli.getId(), bigimgsize, bigimgsize, LinearLayout.HORIZONTAL);	
						lls.setGravity(Gravity.CENTER);
						ci.NewImage(lls.getId(), bigimgsize, bigimgsize, imgid);
				LinearLayout llr = ci.NewView(lla.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
				llr.setPadding(10,5,5, 5);
					ci.NewTV(llr.getId(), R.style.h4, title);
					ci.NewTV(llr.getId(), R.style.p2, caption);
					
			/*
			// ABOUT ADVISE AVOID			
			LinearLayout llaa = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			llaa.setPadding(5,0,5,0);
				LinearLayout lliaa = ci.NewView(llaa.getId(), midimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lliaa.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lliaa.setPadding(0,10,0,0);
					ci.NewImage(lliaa.getId(), smimgsize, smimgsize, stdimg);
				LinearLayout llraa = ci.NewView(llaa.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llraa.setPadding(4, 1, 1, 1);
					LinearLayout llvaa = ci.NewView(llraa.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llvaa.setPadding(5,0,5,5);
						ci.NewTV(llvaa.getId(), R.style.p2, about_);
						ci.NewTV(llvaa.getId(), R.style.p3, about);
						ci.NewTV(llvaa.getId(), R.style.p2, avoid_);
						ci.NewTV(llvaa.getId(), R.style.p3, avoid);
						ci.NewTV(llvaa.getId(), R.style.p2, advise_);
						ci.NewTV(llvaa.getId(), R.style.p3, advise);
			*/
			//ACTIVITY SCIENTIFIC INFO 							
			LinearLayout lla2 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla2.setPadding(5,0,5,0);
				LinearLayout lli2 = ci.NewView(lla2.getId(), midimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli2.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli2.setPadding(0,10,0,0);
					LinearLayout llt = ci.NewView(lli2.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
						llt.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
						ci.NewImage(llt.getId(), smimgsize, smimgsize, imgrisk);	
				LinearLayout llr2 = ci.NewView(lla2.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr2.setPadding(4, 1, 1, 1);
					LinearLayout llu = ci.NewView(llr2.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llu.setPadding(5,5,0,5);
						ci.NewTV(llu.getId(), R.style.p2, evidencelabel);
						ci.NewTV(llu.getId(), R.style.p3, evidence);
						
			// OTHER CANTPROTECT			
			if (canprotect>0) {
			LinearLayout lla3 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla3.setPadding(5,0,5,0);
				LinearLayout lli3 = ci.NewView(lla3.getId(), midimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli3.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli3.setPadding(0,10,0,0);
					ci.NewImage(lli3.getId(), midimgsize, midimgsize, imgpro);
				LinearLayout llr3 = ci.NewView(lla3.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr3.setPadding(4, 1, 1, 1);
					LinearLayout llv = ci.NewView(llr3.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llv.setPadding(5,0,5,5);
						ci.NewTV(llv.getId(), R.style.p2, prostrat);
						ci.NewTV(llv.getId(), R.style.p3, promethod);
			}
			// OTHER STDS			
			if (otherstd!="") {
			LinearLayout lla4 = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			lla4.setPadding(5,0,5,0);
				LinearLayout lli4 = ci.NewView(lla4.getId(), midimgsize, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				lli4.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);	
					lli4.setPadding(0,10,0,0);
					ci.NewImage(lli4.getId(), smimgsize, smimgsize, stdimg);
				LinearLayout llr4 = ci.NewView(lla4.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
				llr4.setPadding(4, 1, 1, 1);
					LinearLayout llv2 = ci.NewView(llr4.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
					llv2.setPadding(5,0,5,5);
						ci.NewTV(llv2.getId(), R.style.p2, couldget);
						ci.NewTV(llv2.getId(), R.style.p3, otherstd);
			}
	}
	
	public void ComputeReviewHeader(int[] steps){
		String semantic = context.getString(R.string.sm_your_performed);
		String mysex; 
		String osex;
		if (steps[1] == 1) { mysex = context.getString(R.string.m_female_d_nom); } else { mysex = context.getString(R.string.m_male_d_nom); }
		if (steps[2] == 1) { osex = context.getString(R.string.m_female_d_ins); } else { osex = context.getString(R.string.m_male_d_ins); }
		semantic = semantic.replace("{MSEX}", mysex);
		semantic = semantic.replace("{OSEX}", osex);
		DrawReviewHeader(semantic);
	}
	
	public void DrawReviewHeader(String semantic){
		LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz.setPadding(3,3,3,3);
		LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			ci.NewTV(lla.getId(), R.style.h5, semantic);
	}
	
	public void ComputeResultsHeader(int[] steps, int risk){
		String question = "";
		String assume = "";
		String riskave = "";
		String riskmore = "";
		String riskfurt = "";
		String qtest = "";
		String rtest = "";
		String preventing = "";
		int riskimg = R.drawable.risk3;
		
				
		if (steps[0] == 1) { 
			assume = context.getString(R.string.m_results_assume_0); 
			question = context.getString(R.string.m_results_atrisk_0); 
			qtest = context.getString(R.string.m_results_taketest_0); 
			if (risk == 0) { 
				riskave = context.getString(R.string.m_results_nodanger);  
				riskmore = context.getString(R.string.m_results_nodanger_m_0);
				riskfurt = context.getString(R.string.m_results_nodanger_further);
				rtest = context.getString(R.string.m_results_test_nodanger_0);
				preventing = context.getString(R.string.m_results_preventing_s);
				riskimg = R.drawable.riskn;
			}
			if (risk == 1) { 
				riskave = context.getString(R.string.m_results_verylow);  
				riskmore = context.getString(R.string.m_results_verylow_m_0); 
				riskfurt = context.getString(R.string.m_results_verylow_further);
				rtest = context.getString(R.string.m_results_test_verylow_0);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk0;
			}
			if (risk == 2) { 
				riskave = context.getString(R.string.m_results_low);  
				riskmore = context.getString(R.string.m_results_low_m_0);
				riskfurt = context.getString(R.string.m_results_low_further);
				rtest = context.getString(R.string.m_results_test_low_0);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk1;
			}
			if (risk == 3) { 
				riskave = context.getString(R.string.m_results_considerable);  
				riskmore = context.getString(R.string.m_results_considerable_m_0);
				riskfurt = context.getString(R.string.m_results_considerable_further);
				rtest = context.getString(R.string.m_results_test_considerable_0);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk2;
			}
			if (risk == 4) { 
				riskave = context.getString(R.string.m_results_high);  
				riskmore = context.getString(R.string.m_results_high_m_0);
				riskfurt = context.getString(R.string.m_results_high_further);
				rtest = context.getString(R.string.m_results_test_high_0);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk3;
			}
		} else { 
			assume = context.getString(R.string.m_results_assume_1); 
			question = context.getString(R.string.m_results_atrisk_1); 
			qtest = context.getString(R.string.m_results_taketest_1); 
			if (risk == 0) { 
				riskave = context.getString(R.string.m_results_nodanger);  
				riskmore = context.getString(R.string.m_results_nodanger_m_1);
				riskfurt = context.getString(R.string.m_results_nodanger_further);
				rtest = context.getString(R.string.m_results_test_nodanger_1);
				preventing = context.getString(R.string.m_results_preventing_s);
				riskimg = R.drawable.riskn;
			}
			if (risk == 1) { 
				riskave = context.getString(R.string.m_results_verylow);  
				riskmore = context.getString(R.string.m_results_verylow_m_1); 
				riskfurt = context.getString(R.string.m_results_verylow_further);
				rtest = context.getString(R.string.m_results_test_verylow_1);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk0;
			}
			if (risk == 2) { 
				riskave = context.getString(R.string.m_results_low);  
				riskmore = context.getString(R.string.m_results_low_m_1);
				riskfurt = context.getString(R.string.m_results_low_further);
				rtest = context.getString(R.string.m_results_test_low_1);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk1;
			}
			if (risk == 3) { 
				riskave = context.getString(R.string.m_results_considerable);  
				riskmore = context.getString(R.string.m_results_considerable_m_1);
				riskfurt = context.getString(R.string.m_results_considerable_further);
				rtest = context.getString(R.string.m_results_test_considerable_1);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk2;
			}
			if (risk == 4) { 
				riskave = context.getString(R.string.m_results_high);  
				riskmore = context.getString(R.string.m_results_high_m_1);
				riskfurt = context.getString(R.string.m_results_high_further);
				rtest = context.getString(R.string.m_results_test_high_1);
				preventing = context.getString(R.string.m_results_preventing_u);
				riskimg = R.drawable.risk3;
			}
		}
		
		DrawResultsHeader(riskimg, question, assume, riskave, riskmore, riskfurt);
		DrawResultsRiskMod(steps);
		DrawResultsPrevention(preventing);
		DrawResultsTesting(qtest, rtest);
		DrawResultsExtended();
		
	}
	
	public void DrawResultsHeader(int riskimg, String question, String assume, String riskave, String riskmore, String riskfurt){
		LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz.setBackgroundResource(R.drawable.border2);
		llz.setPadding(10,10,10,10);
			ci.NewTV(llz.getId(), R.style.h3, question);
			ci.NewTV(llz.getId(), R.style.p1, assume);
			LinearLayout llh = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
				llh.setGravity(Gravity.CENTER);
				ci.NewImage(llh.getId(), midimgsize, midimgsize, riskimg);
				LinearLayout lla = ci.NewView(llh.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
				lla.setPadding(0,10,0,10);
					ci.NewTV(lla.getId(), R.style.h2, riskave);
					ci.NewTV(lla.getId(), R.style.p3, riskmore);
			ci.NewTV(llz.getId(), R.style.p1, riskfurt);
	}
	
	public void DrawResultsTesting(String qtest, String rtest){
		int testimg = R.drawable.test;
		LinearLayout llx = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llx.setPadding(0,10,0,20);
		//llx.setBackgroundResource(R.drawable.border5);
			LinearLayout llxi = ci.NewView(llx.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
			llxi.setBackgroundResource(R.drawable.border3);
			llxi.setPadding(10,10,10,10);
				LinearLayout llxxi = ci.NewView(llxi.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
					LinearLayout llxil = ci.NewView(llxxi.getId(), smimgsize, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);		
						ci.NewImage(llxil.getId(), smimgsize, smimgsize, testimg);
					LinearLayout llxir = ci.NewView(llxxi.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);	
					llxir.setPadding(5,5,5,5);		
							ci.NewTV(llxir.getId(), R.style.h4, qtest);
			ci.NewTV(llxi.getId(), R.style.p3, rtest);
		//ci.NewTV(parent, R.style.p1, because);
	}
	
	public void DrawResultsRiskMod(int[] steps){
		int modimg = R.drawable.dont;
		String riskmod = context.getString(R.string.m_results_riskmod);
		String riskmodc = context.getString(R.string.m_results_riskmod_0);
		String riskless;
		String risklessc;
		if (steps[0]==1){
			riskless = context.getString(R.string.m_results_risk_haart_0);
			risklessc = context.getString(R.string.m_results_riskless_0);
		} else {
			riskless = context.getString(R.string.m_results_risk_haart_1);
			risklessc = context.getString(R.string.m_results_riskless_1);
		}
		
		LinearLayout llx = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llx.setPadding(0,20,0,10);
		//llx.setBackgroundResource(R.drawable.border5);
			LinearLayout llxi = ci.NewView(llx.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
			llxi.setBackgroundResource(R.drawable.border3);
			llxi.setPadding(10,10,10,10);
				LinearLayout llxxi = ci.NewView(llxi.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
					LinearLayout llxil = ci.NewView(llxxi.getId(), smimgsize, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);		
						ci.NewImage(llxil.getId(), smimgsize, smimgsize, modimg);
					LinearLayout llxir = ci.NewView(llxxi.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);	
					llxir.setPadding(5,5,5,5);	
						ci.NewTV(llxir.getId(), R.style.h4, riskmod);
				ci.NewTV(llxi.getId(), R.style.p3, riskmodc);
				
		LinearLayout llx2 = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llx2.setPadding(0,20,0,10);
		//llx.setBackgroundResource(R.drawable.border5);
			LinearLayout llxi2 = ci.NewView(llx2.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
			llxi2.setBackgroundResource(R.drawable.border3);
			llxi2.setPadding(10,10,10,10);
				LinearLayout llxxi2 = ci.NewView(llxi2.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
					LinearLayout llxil2 = ci.NewView(llxxi2.getId(), smimgsize, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);		
						ci.NewImage(llxil2.getId(), smimgsize, smimgsize, modimg);
					LinearLayout llxir2 = ci.NewView(llxxi2.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);	
					llxir2.setPadding(5,5,5,5);	
						ci.NewTV(llxir2.getId(), R.style.h4, riskless);
				ci.NewTV(llxi2.getId(), R.style.p3, risklessc);
	}
	
	public void DrawResultsPrevention(String preventing){
		int previmg = R.drawable.dodo;
		String prevention = context.getString(R.string.m_results_prevention);
		LinearLayout llx = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llx.setPadding(0,10,0,10);
		//llx.setBackgroundResource(R.drawable.border5);
			LinearLayout llxi = ci.NewView(llx.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
			llxi.setBackgroundResource(R.drawable.border3);
			llxi.setPadding(10,10,10,10);
				LinearLayout llxxi = ci.NewView(llxi.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
					LinearLayout llxil = ci.NewView(llxxi.getId(), smimgsize, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
						ci.NewImage(llxil.getId(), smimgsize, smimgsize, previmg);
					LinearLayout llxir = ci.NewView(llxxi.getId(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);	
					llxir.setPadding(5,5,5,5);		
						ci.NewTV(llxir.getId(), R.style.h4, prevention);
			ci.NewTV(llxi.getId(), R.style.p3, preventing);
	}
	
	public void DrawResultsExtended(){
		String because = context.getString(R.string.m_results_why);
		ci.NewTV(parent, R.style.p1, because);
	}
	
	public void DrawReviewFooter(int[] steps){
		String semantic = context.getString(R.string.sm_your_performed);
		if (steps[0] == 1) { semantic = context.getString(R.string.sm_your_concerned_0); } else { semantic = context.getString(R.string.sm_your_concerned_1); }
		LinearLayout llz = ci.NewView(parent, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
		llz.setPadding(3,3,3,3);
		LinearLayout lla = ci.NewView(llz.getId(), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
			ci.NewTV(lla.getId(), R.style.h5, semantic);
	}
	
	public void DrawResultsFooter(int[] steps){

	}
	
	public SexDatabase getUsedDatabase() {
		return sx;
	}
	
	
	public String plusStr(String str, String app) {
		if (str=="") {
			str = app;
		} else {
			str += ", " + app;
		}
		return str;
	}

	
	

}
