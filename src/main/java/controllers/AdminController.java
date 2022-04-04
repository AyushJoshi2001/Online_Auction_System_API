package controllers;

import com.google.inject.Inject;

import dao.AdminDao;
import models.Product;
import models.User;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

public class AdminController {
	@Inject
	AdminDao adminDao;
	
	public Result deleteUser(@PathParam("uid") Long uid){
		
		User deletedUser = adminDao.deleteUser(uid);
		
		if(deletedUser != null) {
			return Results.json().render(deletedUser);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result deleteProduct(@PathParam("pid") Long pid) {
		Product deletedProduct = adminDao.deleteProduct(pid);
		
		if(deletedProduct != null) {
			return Results.json().render(deletedProduct);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
}
