package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Product;
import models.User;

public class AdminDao {
	@Inject
	UserDao userDao;
	Provider<EntityManager> entityManagerProvider;
	
	@Transactional
	public Product deleteProduct(Long pid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			TypedQuery<Product> q = entityManager.createQuery("SELECT x FROM Product x where x.pid=:pid", Product.class);
			q.setParameter("pid", pid);
			Product deletedProduct = q.getSingleResult();
			Query deleteQuery1 = entityManager.createQuery("DELETE FROM Product x where x.pid=:pid");
			deleteQuery1.setParameter("pid", pid);
			deleteQuery1.executeUpdate();
			return deletedProduct;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public User deleteUser(Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		try {
			User deletedUser = entityManager.find(User.class, uid);
			Query deleteQuery1 = entityManager.createQuery("DELETE FROM User x where x.uid=:uid");
			deleteQuery1.setParameter("uid", uid);
			deleteQuery1.executeUpdate();
			return deletedUser;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	public boolean isValidAdmin(String email) {
		User user = userDao.getUserByEmail(email);
		if(user != null && user.isAdmin==1) {
			return true;
		}
		return false;
	}
}
