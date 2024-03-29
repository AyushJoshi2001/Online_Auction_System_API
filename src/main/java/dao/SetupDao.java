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

package dao;


import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Bid;
import models.Bid_Status;
import models.Feedback;
import models.Product;
import models.Sold_Status;
import models.User;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class SetupDao {
    
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Transactional
    public void setup() {
        
        EntityManager entityManager = entityManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x", User.class);
        List<User> users = q.getResultList();        
        
        if (users.size() == 0) {

            // Create a new user and save it
            User bob = new User("bob", "bob@gmail.com", "password", "", "4564512", "address", "about section");
            entityManager.persist(bob);
            
            // Create a new product and save it
            
            Product prod1 = new Product("car", "", "this is demo product", Bid_Status.Open, new Long(8375889), Sold_Status.Unsold, new Long(100), new Date());
            entityManager.persist(prod1);
            
            // Create a new bid and save it
            
            Bid bid1 = new Bid(new Long(12345), new Long(54678), new Long(100), "dummy");
            entityManager.persist(bid1);
            
            // Create a new feedback and save it
            
            Feedback feed1 = new Feedback(new Long(12345), "demo feedback");
            entityManager.persist(feed1);
            
//            // Create a new post
//            Article bobPost3 = new Article(bob, "My third post", lipsum);
//            entityManager.persist(bobPost3);
//
//            // Create a new post
//            Article bobPost2 = new Article(bob, "My second post", lipsum);
//            entityManager.persist(bobPost2);
//            
//            // Create a new post
//            Article bobPost1 = new Article(bob, post1Title, post1Content);
//            entityManager.persist(bobPost1);
//            
//            entityManager.setFlushMode(FlushModeType.COMMIT);
//            entityManager.flush();
        }

    }
    
    String lipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit sed nisl sed lorem commodo elementum in a leo. Aliquam erat volutpat. Nulla libero odio, consectetur eget rutrum ac, varius vitae orci. Suspendisse facilisis tempus elit, facilisis ultricies massa condimentum in. Aenean id felis libero. Quisque nisl eros, accumsan eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula.";

    
    public String post1Title = "Hello to the blog example!";
    public String post1Content = 
            "<p>Hi and welcome to the demo of Ninja!</p> "
            + "<p>This example shows how you can use Ninja in the wild. Some things you can learn:</p>"
            + "<ul>"
            + "<li>How to use the templating system (header, footer)</li>"
            + "<li>How to test your application with ease.</li>"
            + "<li>Setting up authentication (login / logout)</li>"
            + "<li>Internationalization (i18n)</li>" 
            + "<li>Static assets / using webjars</li>"
            + "<li>Persisting data</li>"
            + "<li>Beautiful <a href=\"/article/3\">html routes</a> for your application</li>"
            + "<li>How to design your restful Api (<a href=\"/api/bob@gmail.com/articles.json\">Json</a> and <a href=\"/api/bob@gmail.com/articles.xml\">Xml</a>)</li>"
            + "<li>... and much much more.</li>"
            + "</ul>" 
            + "<p>We are always happy to see you on our mailing list! "
            + "Check out <a href=\"https://www.ninjaframework.org\">our website for more</a>.</p>";

}
