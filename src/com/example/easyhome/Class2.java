package com.example.easyhome;

import java.util.HashMap;

import com.example.easyhome.ButtonAdapter;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Point;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Class2 extends ActionBarActivity implements AsyncTaskCompleteListener<String> 
{

	private final String[][] techList = new String[][] {
			new String[] {
				NfcA.class.getName(),
	            NfcF.class.getName(),
	            MifareUltralight.class.getName(),
	            Ndef.class.getName(),
	            NdefFormatable.class.getName()
			}
	};

	
	private PendingIntent pendingIntent;
	private IntentFilter filter;
	private NfcAdapter nfcAdapter;
	
	
	
	@Override
	public void onTaskComplete(String result) {
		Toast.makeText(Class2.this, result, Toast.LENGTH_SHORT).show();
	}
	
	Button button1;
	
	ListView list;
	String[] lectureType;
	String[] lectureTopics;
	int[] videoIcon ={R.drawable.video_icon1, R.drawable.video_icon2, R.drawable.video_icon3, R.drawable.video_icon4, R.drawable.video_icon5, R.drawable.video_icon6, R.drawable.video_icon7, R.drawable.video_icon8};
	String[] lecturesVideo = {"LectureVideo_1","TutorialVideo_1","LectureVideo_2","TutorialVideo_2","LectureVideo_3","TutorialVideo_3","LectureVideo_4","TutorialVideo_4"};

	
	@SuppressLint("CutPasteId") @Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        
		// creating pending intent:
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// creating intent receiver for NFC events:
		filter = new IntentFilter();

		filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
		filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		// enabling foreground dispatch for getting intent from NFC event:
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
        Resources res=getResources();
        lectureType=res.getStringArray(R.array.type);
        lectureTopics=res.getStringArray(R.array.topics);

        
		
        list=(ListView) findViewById(R.id.listView1);
        
        ButtonAdapter adapter=new ButtonAdapter(this, lectureType, videoIcon,lectureTopics);
        list.setAdapter(adapter);
        
        ListView listView1 = (ListView) findViewById(R.id.listView1);
    	Point size = new Point();
    	this.getWindowManager().getDefaultDisplay().getSize(size);
    	TranslateAnimation animation1 = new TranslateAnimation(size.x, 0, 0.0f, 0.0f);
    	animation1.setDuration(250);
    	animation1.setRepeatCount(0);
    	animation1.setFillAfter(true);
    	listView1.setAnimation(animation1);
    	
	   list.setOnItemClickListener(new OnItemClickListener(){
	    	
	    	@Override
	        public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
	    		// make a http request
			    TheMessage msg = new TheMessage("play_video "+ lecturesVideo[position]);
			    new HttpGetTask(Class2.this).execute(msg.getStringUrl());
	    	}
	    });
	   processNfc(getIntent());
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("dero", "resume");
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// disabling foreground dispatch:
		nfcAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		processNfc(intent);

	}
	
	private void processNfc(Intent intent) {
		// TODO Auto-generated method stub

		Log.d("dero", "nfc");


			
			String action = intent.getAction();
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)||NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)||NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
				String tagID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
				Log.d ("a", tagID);
				TheMessage msg = new TheMessage(stringTagID(tagID));
				new HttpGetTask(Class2.this).execute(msg.getStringUrl());
			}
		}
		
	

	private String ByteArrayToHexString(byte [] inarray) {
	    int i, j, in;
	    String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	    String out= "";
	
	    for(j = 0 ; j < inarray.length ; ++j) 
	        {
	        in = (int) inarray[j] & 0xff;
	        i = (in >> 4) & 0x0f;
	        out += hex[i];
	        i = in & 0x0f;
	        out += hex[i];
	        }
	    return out;
	}
		
	public static String stringTagID (String tagID ){
		HashMap<String, String> hmp = new HashMap<String, String>();
		hmp.put("04D4516A432F80", "play_file play_lecture1");
		hmp.put("0429C2415B2380", "control pause");
		hmp.put("0470666A432F80", "control stop");
		hmp.put("049C626A432F80", "lamp on");
		hmp.put("04CEC14ABC2B80", "lamp off");
		return hmp.get(tagID).toString();
		
	}
	
	
	
}
class ButtonAdapter extends ArrayAdapter<String>
{
	
	Context context;
	int[] images;
	String[] typeArray;
	String[] topicsArray;
	ButtonAdapter(Context c,String[] type, int imgs[], String[] topics)
	{
		super(c,R.layout.row,R.id.textView1,type);
		this.context=c;
		this.images=imgs;
		this.typeArray=type;
		this.topicsArray=topics;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.row, parent, false);
		
		ImageView myImage=(ImageView) row.findViewById(R.id.imageView1);
		TextView myType=(TextView) row.findViewById(R.id.textView1);
		TextView myTopics=(TextView) row.findViewById(R.id.textView2);
		
		myImage.setImageResource(images[position]);
		myType.setText(typeArray[position]);
		myTopics.setText(topicsArray[position]);
		
		return row;
	}
}
