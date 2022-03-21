package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pid")
	public Long pid;
	@Column(name="title")
	public String title;
	@Column(name="product_pic")
	public String product_pic;
	@Column(name="description")
	public String description;
	@Column(name="bid_status")
	@Enumerated(EnumType.STRING)
	public Bid_Status bid_status;
	@Column(name="created_at")
	public Date created_at;
	@Column(name="uid")
	public Long uid;
	@Column(name="sold_status")
	@Enumerated(EnumType.STRING)
	public Sold_Status sold_status;
	@Column(name="sold_to")
	public Long sold_to;
	
	public Product() {
		
	}
	
	
	public Product(String title, String product_pic, String description, Bid_Status bid_status, Long uid,
			Sold_Status sold_status, Long sold_to) {
		super();
		this.title = title;
		this.product_pic = product_pic;
		this.description = description;
		this.bid_status = bid_status;
		this.created_at = new Date();
		this.uid = uid;
		this.sold_status = sold_status;
		this.sold_to = sold_to;
	}
	
	public Product(String title, String product_pic, String description, Bid_Status bid_status, Long uid, Sold_Status sold_status) {
		super();
		this.title = title;
		this.product_pic = product_pic;
		this.description = description;
		this.bid_status = bid_status;
		this.created_at = new Date();
		this.uid = uid;
		this.sold_status = sold_status;
	}
}
