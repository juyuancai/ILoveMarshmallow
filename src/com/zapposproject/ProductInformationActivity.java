package com.zapposproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.result.Product;
import com.result.ProductDetailInfo;
import com.result.SearchResult;
import com.zapposproject.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProductInformationActivity extends Activity {

	private ImageView pictureView;
	private TextView brandNameTxt;
	private TextView productNameTxt;
	private TextView descriptionTxt;
	private Handler handler;
	private String asin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_information);
		getViewById();
		setHandler();
		achieveProductDetail();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_information, menu);
		return true;
	}

	private void getViewById() {
		pictureView = (ImageView) findViewById(R.id.picture);
		productNameTxt = (TextView) findViewById(R.id.productName);
		brandNameTxt = (TextView) findViewById(R.id.brandName);
		descriptionTxt = (TextView) findViewById(R.id.description);
	}

	private void achieveProductDetail() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		asin = bundle.getString("asin");
		new Thread(getProductDetail).start();
	}

	Runnable getProductDetail = new Runnable() {
		@Override
		public void run() {
			String url = "https://zappos.amazon.com/mobileapi/v1/product/asin/";
			HttpGet httpGet = new HttpGet(url + asin);
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
				ProductDetailInfo productDetailInfo = new ProductDetailInfo(
						result);
				Message message = Message.obtain();
				message.what = 1;
				message.obj = productDetailInfo;
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
			ProductDetailInfo productDetail = (ProductDetailInfo) msg.obj;
			productNameTxt.setText(productDetail.getProductName());
			brandNameTxt.setText(productDetail.getBrandName());
			descriptionTxt.setText(productDetail.getDescription());
			loadPicture(productDetail.getDefaultImageUrl());
		}
	}

	private void loadPicture(String url) {
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedImage = asyncImageLoader.loadDrawable(url,
				new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						pictureView.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			pictureView.setImageResource(R.drawable.ic_launcher);
		} else {
			pictureView.setImageDrawable(cachedImage);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.share) {
			Intent i = new Intent(ProductInformationActivity.this,
					ShareActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
