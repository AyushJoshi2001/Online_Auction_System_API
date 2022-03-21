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
	Long b_id;
	@Column(name="uid")
	Long uid;
	@Column(name="pid")
	Long pid;
	@Column(name="amount")
	Long amount;
	
	public Bid(Long uid, Long pid, Long amount) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.amount = amount;
	}
	
}
