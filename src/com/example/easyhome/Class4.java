package com.example.easyhome;

import java.util.HashMap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Class4 extends ActionBarActivity implements AsyncTaskCompleteListener<String> {

	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private IntentFilter filter;
	
	
	
	
	private final String[][] techList = new String[][] {
			new String[] {
				NfcA.class.getName(),
	            NfcF.class.getName(),
	            MifareUltralight.class.getName(),
	            Ndef.class.getName(),
	            NdefFormatable.class.getName()
			}
	};

	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);

     // creating pending intent:
     		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
     		// creating intent receiver for NFC events:
     		filter = new IntentFilter();

     		filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
     		filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
     		filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
     		// enabling foreground dispatch for getting intent from NFC event:
     		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
     		
    		processNfc(getIntent());
    }

	 public void onToggle(View view) {
	        // Is the toggle on?
	        boolean on = ((ToggleButton) view).isChecked();

	        if (on) {
	            // Turn on torch
	            toggleLamp("on");
	        } else {
	            // Turn off torch
	            toggleLamp("off");
	        }
	    }
	
	
	@Override
	public void onTaskComplete(String result) {
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
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
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)||NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ) {
			
			
			String tagID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
			String torchMessage = stringTagID (tagID );
		    Log.d ("a", tagID);
			TheMessage msg = new TheMessage(stringTagID(tagID));
			new HttpGetTask(this).execute(msg.getStringUrl());
			
			 if (torchMessage.split(" ")[0].equals("lamp")) {
	                Log.d("t", torchMessage);
	                toggleLamp(torchMessage.split(" ")[1], torchMessage);
	            }
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

	private void toggleLamp(String s) {
        toggleLamp(s, " ");
    }

    private void toggleLamp(String s, String extra) {
        TheMessage msg = new TheMessage("lamp " + s);
        new HttpGetTask(Class4.this).execute(msg.getStringUrl());
        if (extra.equals("lamp on")) {
            ((ToggleButton) findViewById(R.id.toggleButton)).setChecked(true);
        }
        if (extra.equals("lamp off")) {
            ((ToggleButton) findViewById(R.id.toggleButton)).setChecked(false);
        }
    }
	
}


