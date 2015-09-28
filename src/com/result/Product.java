package com.result;

public class Product {
	private String brandName;
    
	private String price;
	private String imgURL;
	private String asin;
	private String productURL;
	private double productRating;

	private String productName;
	
	
	
	public Product(String brandName,String price,
			String imgURL, String asin, String productURL,
			double productRating, String productName) {
		super();
		this.brandName = brandName;
		
		this.price = price;
		this.imgURL = imgURL;
		this.asin = asin;
		this.productURL = productURL;
		this.productRating = productRating;
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getImgURL() {
		return imgURL;
	}

	public String getPrice() {
		return price;
	}

	public String getProductName() {
		return productName;
	}

	public String getAsin() {
		return asin;
	}



	
    

	
	
	
	
}
