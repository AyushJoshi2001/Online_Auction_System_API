package controllers;

import ninja.Result;
import ninja.Results;

public class AdminController {

	public Result deleteUser(){
		
		return Results.json().render("user deleted");
	}
	
	public Result deleteProduct() {
		
		return Results.json().render("Product deleted");
	}
}
