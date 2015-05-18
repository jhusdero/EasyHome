package com.example.easyhome;



import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.main, menu);
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        
        
        
        return super.onCreateOptionsMenu(menu);
    	
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
        case R.id.action_search:
        	
        	break;
        case R.id.action_settings:
        	settingsMenuItem();
        		break;
        case R.id.action_help:
        	helpMenuItem();
        	break;
        case R.id.action_refresh:
        	refreshMenuItem();
        	break;
        		     
        }
        return true;
             
    }
    
    private void settingsMenuItem(){
    	new AlertDialog.Builder(this)
    	.setTitle("Settings")
    	.setMessage("For Settings Press Next")
    	.setNeutralButton("Next",new DialogInterface.OnClickListener(){
    		
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				
    			}
        		
        	   		
        	}).show();    
        }
    private void refreshMenuItem(){
    	new AlertDialog.Builder(this)
    	.setTitle("Refresh")
    	.setMessage("To Refresh Press OK")
    	.setNeutralButton("OK",new DialogInterface.OnClickListener(){
    		
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				
    			}
        		
        	   		
        	}).show();    
        }
    private void helpMenuItem(){
    	new AlertDialog.Builder(this)
    	.setTitle("Help")
    	.setMessage("For Help Press Next")
    	.setNeutralButton("Next",new DialogInterface.OnClickListener(){
    		
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				
    			}
        		
        	   		
        	}).show();    
        }
     


    
    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    	public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    public void onButton1 (View view) {
    	Intent intent = new Intent(MainActivity.this, Class2.class);
    	startActivity(intent);
    }
    
    public void onButton2 (View view) {
    	Intent intent = new Intent(MainActivity.this, Class3.class);
    	startActivity(intent);
    }
    
    public void onButton3 (View view) {
    	Intent intent = new Intent(MainActivity.this, Class4.class);
    	startActivity(intent);
    }
    
}
