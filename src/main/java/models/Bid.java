package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
public class Bid {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long b_id;
	@Column(name="uid")
	public Long uid;
	@Column(name="pid")
	public Long pid;
	@Column(name="amount")
	public Long amount;
	@Column(name = "product_name")
	public String product_name;

	public Bid() {
		
	}
	
	public Bid(Long uid, Long pid, Long amount, String product_name) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.amount = amount;
		this.product_name = product_name;
	}
	
}
