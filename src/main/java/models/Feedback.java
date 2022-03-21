package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feedback", schema = "public")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long f_id;
	@Column(name="uid")
	Long uid;
	@Column(name="message")
	String message;
	
	public Feedback(Long uid, String message) {
		super();
		this.uid = uid;
		this.message = message;
	}
	
}
