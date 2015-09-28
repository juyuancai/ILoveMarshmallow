package com.result;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResult {
	
	  private JSONObject jsonObject;
	  private int totalResults;
	  private ArrayList<Product> resultArrayList=new ArrayList();
     
	public SearchResult(String result){
		try {
			jsonObject = new JSONObject(result);
			totalResults=(int) jsonObject.get("totalResults");
			analyzeResultList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getTotalResults() {
		return totalResults;
	}

	public ArrayList<Product> getResultArrayList() {
		return resultArrayList;
	}

	private void analyzeResultList() throws JSONException{
		JSONArray resultList=jsonObject.getJSONArray("results");
		
		for (int i = 0; i < resultList.length(); i++) {
			String brandName=resultList.getJSONObject(i).getString("brandName");
			
			String price=resultList.getJSONObject(i).getString("price");
			String imageURL=resultList.getJSONObject(i).getString("imageUrl");
			String asin=resultList.getJSONObject(i).getString("asin");
			String productUrl=resultList.getJSONObject(i).getString("productUrl");
			double productRating=resultList.getJSONObject(i).getDouble("productRating");
			String productName=resultList.getJSONObject(i).getString("productName");
			Product product=new Product(brandName,price,imageURL,asin,productUrl,productRating,productName);
			resultArrayList.add(product);
		}
	}
}
