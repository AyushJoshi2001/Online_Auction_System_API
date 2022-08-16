package models;

import java.util.Date;

public class ProductDto {
	public Long pid;
	public String title;
	public String product_pic;
	public String description;
	public Bid_Status bid_status;
	public Date created_at;
	public Long uid;
	public Sold_Status sold_status;
	public Long sold_to;
	public Long base_price;
	public Date bid_end_date;
	
	public ProductDto() {}
}
