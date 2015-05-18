package com.example.easyhome;

import com.example.easyhome.ButtonAdapter2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



@SuppressLint("CutPasteId") public class Class3 extends ActionBarActivity implements AsyncTaskCompleteListener<String>
{
	
	@Override
	public void onTaskComplete(String result) {
		Toast.makeText(Class3.this, result, Toast.LENGTH_SHORT).show();
	}
	Button button2;
	
	ListView list;
	String[] lectureType;
	String[] lectureTopics;
	int[] audioIcon ={R.drawable.audio_video1, R.drawable.audio_video2, R.drawable.audio_video3, R.drawable.audio_video4,R.drawable.audio_video5,R.drawable.audio_video6,R.drawable.audio_video7,R.drawable.audio_video8};
	String[] lecturesAudio = {"LectureAudio_1","TutorialAudio_1","LectureAudio_2","TutorialAudio_2","LectureAudio_3","TutorialAudio_3","LectureAudio_4","TutorialAudio_4"};
	
	
	@SuppressLint("CutPasteId") protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        
        
        Resources res=getResources();
        list=(ListView) findViewById(R.id.listView2);
        lectureType=res.getStringArray(R.array.type);
        lectureTopics=res.getStringArray(R.array.topics);
        
        ButtonAdapter2 adapter=new ButtonAdapter2(this,lectureType, audioIcon,lectureTopics);
        list.setAdapter(adapter);
        
        ListView listView2 = (ListView) findViewById(R.id.listView2);
    	Point size = new Point();
    	this.getWindowManager().getDefaultDisplay().getSize(size);
    	TranslateAnimation animation1 = new TranslateAnimation(size.x, 0, 0.0f, 0.0f);
    	animation1.setDuration(250);
    	animation1.setRepeatCount(0);
    	animation1.setFillAfter(true);
    	listView2.setAnimation(animation1);
        
	    list.setOnItemClickListener(new OnItemClickListener(){
	    	
	    	@Override
	        public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
	    		// make a http request
	    		
	    		
			    TheMessage msg = new TheMessage("play_audio "+ lecturesAudio[position]);
			    new HttpGetTask(Class3.this).execute(msg.getStringUrl());
	    	}
	    });
	
	}
}
class ButtonAdapter2 extends ArrayAdapter<String>{

		Context context;
		int[] images;
		String[] typeArray;
		String[] topicsArray;
		
		ButtonAdapter2(Context c,String[] type,int imgs[],String[] topics)
		{
			super(c,R.layout.audio,R.id.textView1,type);
			this.context=c;
			this.images=imgs;
			this.typeArray=type;
			this.topicsArray=topics;
			
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View audio=inflater.inflate(R.layout.audio, parent, false);
			
			ImageView myImage=(ImageView) audio.findViewById(R.id.audioImage);
			TextView myType=(TextView) audio.findViewById(R.id.textView1);
			TextView myTopics=(TextView) audio.findViewById(R.id.textView2);
			
			myImage.setImageResource(images[position]);
			myType.setText(typeArray[position]);
			myTopics.setText(topicsArray[position]);
			
			return audio;
		}
	}
