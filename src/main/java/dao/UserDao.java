/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Copyright (C) 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dao;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.User;
import models.UserDto;
import ninja.jpa.UnitOfWork;


public class UserDao {
    
    @Inject
    Provider<EntityManager> entityManagerProvider;
    
    @Transactional
    public User registerUser(UserDto userDto) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {
    		if((userDto.email).endsWith("@gmail.com")&& (userDto.email).length()>=13 && (userDto.password).length()>=6) {    			
    			User user = new User(userDto.name, userDto.email, userDto.password, userDto.profile_pic, userDto.mobile, userDto.address, userDto.about);
    			entityManager.persist(user);
    			return user;
    		}
    		else {
    			return null;
    		}
    	}
    	catch (Exception e) {
			return null;
		}
    }
    
    
    @UnitOfWork
    public User getUserByEmail(String email) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {
    		Query q = entityManager.createQuery("select x from User x where x.email=:email");
    		q.setParameter("email", email);
    		User user = (User)q.getSingleResult();
    		return user;
    	}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @UnitOfWork
    public User getUser(Long uid) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {
    		User user = entityManager.find(User.class, uid);
    		return user;
    	}
    	catch (Exception e) {
			return null;
		}
    }
    
    @UnitOfWork
    public User getMyProfile(String email) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {
    		TypedQuery<User> query = entityManager.createQuery("SELECT x FROM User x WHERE email=:email", User.class).setParameter("email", email);
    		User user = query.getSingleResult();
    		return user;
    	}
    	catch (Exception e) {
			return null;
		}
    }

    
    
    @Transactional
    public boolean updateUser(UserDto userDto) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {
    		if(userDto.uid == null) {
    			return false;
    		}
    		User user = entityManager.find(User.class, userDto.uid);
    		if(user !=null && (userDto.uid).equals(user.uid)) {
    			Query query = entityManager.createQuery("update User set name=:name, profile_pic=:profile_pic, mobile=:mobile, address=:address, about=:about where uid=:uid");
    			query.setParameter("name", userDto.name);
    			query.setParameter("profile_pic", userDto.profile_pic);
    			query.setParameter("mobile", userDto.mobile);
    			query.setParameter("address", userDto.address);
    			query.setParameter("about", userDto.about);
    			query.setParameter("uid", userDto.uid);
    			query.executeUpdate();
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
    public User getUserById(Long uid) {
    	EntityManager entityManager = entityManagerProvider.get();
    	try {

			User user = entityManager.find(User.class, uid);
			return user;
    	}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    


    public boolean isUserAndPasswordValid(String email, String password) {
        
        if (email != null && password != null) {
            
            try {
	            User user = getUserByEmail(email);
	
	            if (user != null) {
	                
	                if (user.password.equals(password)) {
	
	                    return true;
	                }
	                
	            }
            }
            catch (Exception e) {
				e.printStackTrace();
				return false;
			}

        }
        
        return false;
 
    }
    
    
    

    /**
     * Get single result without throwing NoResultException, you can of course just catch the
     * exception and return null, it's up to you.
     */
//    private static <T> T getSingleResult(TypedQuery<T> query) {
//        query.setMaxResults(1);
//        List<T> list = query.getResultList();
//        if (list == null || list.isEmpty()) {
//            return null;
//        }
//
//        return list.get(0);
//    }
    

}
