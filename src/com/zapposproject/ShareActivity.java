package com.zapposproject;

import com.result.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ShareActivity extends Activity {

	private ListView friendListView;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		getViewById();
		setListView();
		setOnclick();
	}

	private void getViewById(){
		 friendListView= (ListView)findViewById(R.id.friendList);
	}
	
	private void setOnclick() {
		friendListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String name = (String) adapter.getItem(position);
				Toast.makeText(getApplicationContext(), "share link to friend "+name,
					     Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setListView(){
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);  
        adapter.add("Robin");  
        adapter.add("John");  
        adapter.add("Linda");        
        friendListView.setAdapter(adapter);  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
