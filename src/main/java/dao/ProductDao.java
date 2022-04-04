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
	public ProductsDto getAllProducts() {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x", Product.class);
			List<Product> products = q.getResultList();
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
			Product product = new Product(productDto.title, productDto.product_pic, productDto.description, Bid_Status.Open, productDto.uid, Sold_Status.Unsold, productDto.base_price);
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
