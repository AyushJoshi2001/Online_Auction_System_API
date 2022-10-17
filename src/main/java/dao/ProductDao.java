package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Bid_Status;
import models.Product;
import models.ProductDto;
import models.ProductsDto;
import models.Sold_Status;
import ninja.jpa.UnitOfWork;

public class ProductDao {
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@UnitOfWork
	public ProductsDto getAllProducts(int pageNo, int pageSize, String title, Long pid, Long maxPrice) {
		if(pageSize==0) pageSize = 10;
		if(pageNo==0) pageNo = 1;
		int offset = (pageNo-1)*pageSize;
		EntityManager entityManager = entityManagerProvider.get();
		List<Product> products;
		System.out.println("pid =>" +pid + "title =>" +title + "maxPrice =>" +maxPrice);
		try {
			if(pid!=null && pid!=0l) {
				TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.pid=:pid order by created_at desc", Product.class);
				q.setParameter("pid", pid);
				q.setFirstResult(offset);
				q.setMaxResults(pageSize);
				products = q.getResultList();
			}
			else if(maxPrice!=null && maxPrice!=0l){
				TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.base_price<=:maxPrice order by created_at desc", Product.class);
				q.setParameter("maxPrice", maxPrice);
				q.setFirstResult(offset);
				q.setMaxResults(pageSize);
				products = q.getResultList();
			}
			else if(title!=null) {
				TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.title LIKE :title order by created_at desc", Product.class);
				q.setParameter("title", title+"%");
				q.setFirstResult(offset);
				q.setMaxResults(pageSize);
				products = q.getResultList();
			}
			else {				
				TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x order by created_at desc", Product.class);
				q.setFirstResult(offset);
				q.setMaxResults(pageSize);
				products = q.getResultList();
			}
			ProductsDto productsDto = new ProductsDto();
			productsDto.products = products;
			return productsDto;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public Long getTotalProductCount() {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			Query q = entityManager.createQuery("SELECT COUNT(pid) FROM Product");
			Long productCount = (Long) q.getResultList().get(0);
			return productCount;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public Long totalProductCountByMaxPrice(Long maxPrice) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			Query q = entityManager.createQuery("SELECT COUNT(x.pid) FROM Product x where x.base_price<=:maxPrice");
			q.setParameter("maxPrice", maxPrice);
			Long productCount = (Long) q.getResultList().get(0);
			return productCount;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public Product getSingleProduct(Long pid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.pid=:pid", Product.class);
			q.setParameter("pid", pid);
			Product product = q.getSingleResult();
			return product;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@UnitOfWork
	public List<Product> getProductByTitle(String title) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.title LIKE :title order by created_at desc", Product.class);
			q.setParameter("title", title+"%");
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public List<Product> getProductByPid(Long pid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.pid=:pid order by created_at desc", Product.class);
			q.setParameter("pid", pid);
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public List<Product> getProductByMaxPrice(Long maxPrice) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.base_price<=:maxPrice order by created_at desc", Product.class);
			q.setParameter("maxPrice", maxPrice);
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public List<Product> getProductByUid(Long uid, int pageNo, int pageSize) {
		if(pageSize==0) pageSize = 10;
		if(pageNo==0) pageNo = 1;
		int offset = (pageNo-1)*pageSize;
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.uid =:uid order by created_at desc", Product.class);
			q.setParameter("uid", uid);
			q.setFirstResult(offset);
			q.setMaxResults(pageSize);
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@UnitOfWork
	public List<Product> getProductBySoldTo(Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.sold_to=:uid order by created_at desc", Product.class);
			q.setParameter("uid", uid);
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@UnitOfWork
	public List<Product> getProductByTitleAndUid(String title, Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.uid=:uid AND x.title LIKE :title", Product.class);
			q.setParameter("title", title+"%");
			q.setParameter("uid", uid);
			List<Product> products = q.getResultList();
			return products;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Transactional
	public boolean addProduct(ProductDto productDto) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			Product product = new Product(productDto.title, productDto.product_pic, productDto.description, Bid_Status.Open, productDto.uid, Sold_Status.Unsold, productDto.base_price, productDto.bid_end_date);
			entityManager.persist(product);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Transactional
	public boolean updateProduct(ProductDto productDto, Long pid) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			Product product = entityManager.find(Product.class, pid);
			if(product!=null && (product.uid).equals(productDto.uid)) {
				Query updateQuery = entityManager.createQuery("update Product set title=:title, product_pic=:product_pic, description=:description, base_price=:base_price where pid=:pid");
				updateQuery.setParameter("title", productDto.title);
				updateQuery.setParameter("product_pic", productDto.product_pic);
				updateQuery.setParameter("description", productDto.description);
				updateQuery.setParameter("base_price", productDto.base_price);
				updateQuery.setParameter("pid", pid);
				updateQuery.executeUpdate();
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
	public Product deleteProduct(Long pid, Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.pid=:pid", Product.class);
			q.setParameter("pid", pid);
			Product deletedProduct = q.getSingleResult();
			if(deletedProduct != null && (deletedProduct.uid).equals(uid)) {
				Query deleteQuery = entityManager.createQuery("DELETE FROM Bid x where x.pid=:pid AND x.uid=:uid");
				deleteQuery.setParameter("pid", pid);
				deleteQuery.setParameter("uid", uid);
				deleteQuery.executeUpdate();
				Query deleteQuery1 = entityManager.createQuery("DELETE FROM Product x where x.pid=:pid AND x.uid=:uid");
				deleteQuery1.setParameter("pid", pid);
				deleteQuery1.setParameter("uid", uid);
				deleteQuery1.executeUpdate();
				return deletedProduct;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
