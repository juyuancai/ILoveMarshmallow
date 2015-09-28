package com.zapposproject;

import java.util.ArrayList;

import com.result.Product;
import com.zapposproject.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
//this is the adapter to show product list
public class ProductListAdapter extends BaseAdapter {

	private ArrayList<Product> productList = new ArrayList();
	private Context context;
	  private AsyncImageLoader asyncImageLoader=new AsyncImageLoader();
	  private ListView listView;
	  ImageView iv;  
	
	public ProductListAdapter(Context context,ArrayList<Product> t,ListView listView) {
		this.context = context;
		this.productList=t;
		 this.listView = listView;
		System.out.println("constructer get count "+productList.size());
	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("get count "+productList.size());
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("get view");
		LinearLayout ll = null;

		if (convertView != null) {

			ll = (LinearLayout) convertView;
		}

		else {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			ll = (LinearLayout) inflater.inflate(R.layout.product_cell, null);
		}
		Product product = (Product) getItem(position);
		iv = (ImageView) ll.findViewById(R.id.image);  
        String url=product.getImgURL();  
        iv.setTag(url);  
	
        
		 Drawable cachedImage = asyncImageLoader.loadDrawable(product.getImgURL(), new ImageCallback() {


			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				  ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);  
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }	
			}
         });
         if (cachedImage == null) {
             iv.setImageResource(R.drawable.ic_launcher);
         }else{
             iv.setImageDrawable(cachedImage);
         }
		TextView brandNameTxt = (TextView) ll.findViewById(R.id.brandName);
		TextView productNameTxt = (TextView) ll.findViewById(R.id.productName);
		TextView priceTxt = (TextView) ll.findViewById(R.id.price);
		brandNameTxt.setText(product.getBrandName());
		productNameTxt.setText(product.getProductName());
		priceTxt.setText(product.getPrice());
		return ll;
		
	}

}
