package com.zapposproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.result.Product;
import com.result.SearchResult;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity {

	
	private ListView productListView;
	private ProductListAdapter adapter;
	private Handler handler;
	private String queryString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getViewById();
		setOnclick();
		setHandler();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.searchView)
				.getActionView();
		
		   searchView.setFocusable(true);
		   searchView.setIconified(false);  
		   searchView.setQueryHint("search here");
		   searchView.setOnQueryTextListener(new OnQueryTextListener() {

		        @Override
		        public boolean onQueryTextChange(String newText) {
		            //Log.e("onQueryTextChange", "called");
		            return false;
		        }
		        
		        @Override
		        public boolean onQueryTextSubmit(String query) {
		        	queryString=query;
		        	new Thread(getAnswer).start(); 	
		            return false;
		        }	        
		    });
			
		   return true;
	}

	private void getViewById() {
		productListView= (ListView)findViewById(R.id.productList);
	}

	private void setOnclick() {
		productListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Product product = (Product) adapter.getItem(position);
				Intent i = new Intent(MainActivity.this,
				ProductInformationActivity.class);
				 i.putExtra("asin", product.getAsin());
				 startActivity(i);
			}
		});
	}

	Runnable getAnswer = new Runnable() {
		@Override
		public void run() {
			String httpString = "https://zappos.amazon.com/mobileapi/v1/search?term="+queryString;
			HttpGet httpGet = new HttpGet(httpString);
			HttpClient httpClient = new DefaultHttpClient();
			InputStream inputStream = null;
			try {
				HttpResponse mHttpResponse = httpClient.execute(httpGet);
				HttpEntity mHttpEntity = mHttpResponse.getEntity();
				inputStream = mHttpEntity.getContent();

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));
				
				String result = "";
				String line = "";
				while (null != (line = bufferedReader.readLine())) {
					result += line;
				}
				
				SearchResult searchResult = new SearchResult(result);
				for (Product p : searchResult.getResultArrayList()) {
					System.out.println(p.getBrandName());
				}
				Message message = Message.obtain();
				message.what = 1;
				message.obj=searchResult.getResultArrayList();
				handler.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};
	
	private void setHandler() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				analyzeMessage(msg);
			}
		};
	}

	private void analyzeMessage(Message msg) {
		System.out.println("maintabl handler!");
		switch (msg.what) {
		case 1:
		    ArrayList<Product> resultList=(ArrayList<Product>) msg.obj;
			adapter=new ProductListAdapter(this,resultList,productListView);
			 productListView.setAdapter(adapter);		
			break;
		}
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
