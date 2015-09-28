package com.result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailInfo {
	private JSONObject jsonObject;
	private String brandName;
	private String description;
	private String asin;
	private String defaultProductType;
	private String productName;
	private String defaultImageUrl;

	public ProductDetailInfo(String result) {
		try {
			jsonObject = new JSONObject(result);
			analyzeResultList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void analyzeResultList() throws JSONException {
		brandName = jsonObject.getString("brandName");
		description = jsonObject.getString("description");
		description=analyzeDescription(description);
		asin = jsonObject.getString("asin");
		defaultProductType = jsonObject.getString("defaultProductType");
		productName = jsonObject.getString("productName");
		defaultImageUrl = jsonObject.getString("defaultImageUrl");
	}
    
	private String analyzeDescription(String s){
		s=s.replaceAll("<ul>", "");
		s=s.replaceAll("<li>", "");
		s=s.replaceAll("</li>", "\r\n");
		s=s.replaceAll("</ul>", "");
		return s;
	}
	
	public String getBrandName() {
		return brandName;
	}

	public String getDescription() {
		return description;
	}

	public String getAsin() {
		return asin;
	}

	public String getDefaultProductType() {
		return defaultProductType;
	}

	public String getProductName() {
		return productName;
	}

	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	
}
