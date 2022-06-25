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
 * Copyright (C) 2012 the original author or authors.
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

package controllers;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.UserDao;
import models.LoginDto;
import models.User;
import models.UserDto;

@Singleton
public class AuthController {
    
    @Inject
    UserDao userDao;
    
    public Result registerUser(UserDto userDto) {
    	User user = userDao.registerUser(userDto);
    	if(user != null) {    		
    		user.password = null;
    		return Results.json().render(user);
    	}
    	return Results.badRequest().json().render("Bad Request");
    }
    
    public Result getUser(@PathParam("uid") Long uid) {
    	User user = userDao.getUser(uid);
    	if(user != null) {    		
    		user.password = null;
    		return Results.json().render(user);
    	}
    	return Results.badRequest().json().render("Bad Request");
    }
    
    public Result getMyProfile(Context context) {
    	String email = context.getSession().get("email");
    	User user = userDao.getMyProfile(email);
    	if(user != null) {    		
    		user.password = null;
    		return Results.json().render(user);
    	}
    	return Results.badRequest().json().render("Bad Request");
    }
    
    
    public Result updateUser(UserDto userDto) {
    	boolean user = userDao.updateUser(userDto);
    	if(user) {    		
    		User updatedUser = userDao.getUser(userDto.uid);
    		return Results.json().render(updatedUser);
    	}
    	return Results.badRequest().json().render("Bad Request");
    }
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    // Login
    ///////////////////////////////////////////////////////////////////////////
    public Result login(Context context) {

        return Results.html();

    }

    public Result loginPost(LoginDto loginDto, Session session) {

        boolean isUserNameAndPasswordValid = userDao.isUserAndPasswordValid(loginDto.email, loginDto.password);

        if (isUserNameAndPasswordValid) {
        	session.put("email", loginDto.email);
        	session.put("password", loginDto.password);
        	session.setExpiryTime(24*60*60*1000L);
        	User user = userDao.getUserByEmail(loginDto.email);
        	return Results.ok().json().render(user);

        } else {

        	return Results.unauthorized().json().render("Unauthorized User");

        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
//        context.getFlashScope().success("login.logoutSuccessful");

//        return Results.redirect("/");
        return Results.json().render("SuccessFully logged out");

    }

}
