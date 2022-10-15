package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Bid;
import models.BidDto;
import models.Bid_Status;
import models.Product;
import models.ProductDto;
import models.Sold_Status;
import ninja.jpa.UnitOfWork;

public class BidDao {
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Transactional
	public boolean bidOnProduct(Long pid, BidDto bidDto) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			Bid bid = new Bid(bidDto.uid, pid, bidDto.amount, bidDto.product_name);
			entityManager.persist(bid);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@UnitOfWork
	public List<Bid> getAllBidsByPid(Long pid) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			TypedQuery<Bid> q = entityManager.createQuery("SELECT x FROM Bid x where x.pid=:pid ORDER BY x.amount Desc", Bid.class);
			q.setParameter("pid", pid);
			q.setMaxResults(10);
			List<Bid> bids = q.getResultList();
			return bids;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public List<Bid> getAllBidsByUid(Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			TypedQuery<Bid> q = entityManager.createQuery("SELECT x FROM Bid x where x.uid=:uid", Bid.class);
			q.setParameter("uid", uid);
			q.setMaxResults(10);
			List<Bid> bids = q.getResultList();
			return bids;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Transactional
	public boolean changeBidStatus(ProductDto productDto, Long pid) {
		// it will take bid_status , uid and pid
		// when bid status close then sold_status and sold_to will also get update.
		// owner of the product can update the bid_status.
		
		EntityManager entityManager = entityManagerProvider.get();
		try {
			Product product = entityManager.find(Product.class, pid);
			
			if(product != null && (product.uid).equals(productDto.uid) && (productDto.bid_status).name().toLowerCase().equalsIgnoreCase("close")) {
				Query query = entityManager.createQuery("update Product set bid_status=:bid_status where pid=:pid");
				query.setParameter("bid_status", Bid_Status.Close);
				query.setParameter("pid", pid);
				query.executeUpdate();				
		
				Query query1 = entityManager.createQuery("select x from Bid x where x.pid=:pid order by x.amount desc");
				query1.setParameter("pid", pid);
				query1.setMaxResults(1);
				List<Bid> bids = query1.getResultList();
				
				if(bids.size() > 0) {
					Long uid = bids.get(0).uid;
					Long sold_price = bids.get(0).amount;
					Query query2 = entityManager.createQuery("update Product set sold_to=:sold_to, sold_price=:sold_price, sold_status=:sold_status where pid=:pid");
					query2.setParameter("sold_to", uid);
					query2.setParameter("sold_price", sold_price);
					query2.setParameter("sold_status", Sold_Status.Sold);
					query2.setParameter("pid", pid);
					query2.executeUpdate();
				}
				
				return true;
			}
			else {
				return false;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public void closeBids() {
		System.out.println("Inside closeBids");
		EntityManager entityManager = entityManagerProvider.get();
		long bid_end_date = System.currentTimeMillis();
		
		try {
			TypedQuery<Product>  q =  entityManager.createQuery("select x from Product x where x.bid_status='Open' and x.bid_end_date<:bid_end_date", Product.class);
			q.setParameter("bid_end_date", new Date(bid_end_date));
			q.setMaxResults(50);
			List<Product> notSoldProducts = q.getResultList();
			
			for(Product product : notSoldProducts) {
				long pid = product.pid;
				Query query1 = entityManager.createQuery("select x from Bid x where x.pid=:pid order by x.amount desc");
				query1.setParameter("pid", pid);
				query1.setMaxResults(1);
				List<Bid> bids = query1.getResultList();
				
				if(bids.size() > 0) {
					Long uid = bids.get(0).uid;
					Long sold_price = bids.get(0).amount;
					Query query2 = entityManager.createQuery("update Product set sold_to=:sold_to, sold_price=:sold_price, sold_status='Sold', bid_status='Close' where pid=:pid");
					query2.setParameter("sold_to", uid);
					query2.setParameter("sold_price", sold_price);
					query2.setParameter("pid", pid);
					query2.executeUpdate();
				}
				else {
					Query query2 = entityManager.createQuery("update Product set sold_status='Unsold', bid_status='Close' where pid=:pid");
					query2.setParameter("pid", pid);
					query2.executeUpdate();
				}
				
				System.out.println("updated product pid => " + pid);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
