package cam.hivrisk;

public class SexLibrary {
	
	public SexActivities SActivities[] = new SexActivities[20];
	
	public SexLibrary() {
		SActivities[0] = new SexActivities("ORAL_PEN_ACT",	"ORAL", 1,	0,	1,	0,	0,	1,	1,	0,	1,	1);
		SActivities[1] = new SexActivities("ORAL_PEN_REC",	"ORAL",	1,	1,	0,	0,	0,	1,	1,	0,	1,	1);
		SActivities[2] = new SexActivities("ORAL_ANL_ACT",	"ORAL",	1,	1,	1,	0,	0,	1,	0,	1,	1,	1);
	}

}
