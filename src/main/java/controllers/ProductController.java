package controllers;


import com.google.inject.Inject;

import dao.ProductDao;
import models.Product;
import models.ProductDto;
import models.ProductsDto;
import models.DeleteProductDto;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

public class ProductController {
	@Inject
	ProductDao productDao;
	
	
	public Result getAllProducts() {
		
		ProductsDto products = productDao.getAllProducts();
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.json().render("Products Not Found");
	}
	
	
	public Result getSingleProduct(@PathParam("pid") Long pid) {
		Product product = productDao.getSingleProduct(pid);
		if(product != null) {			 
			return Results.json().render(product);
		}
		return Results.json().render("Product Not Found");
	}

	
	public Result addProduct(ProductDto productDto) {
		boolean add = productDao.addProduct(productDto);
		if(add) {
			return Results.json().render("Product Added SuccessFully");
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	
	public Result updateProduct(ProductDto productDto, @PathParam("pid") Long pid) {
		boolean updatedProduct = productDao.updateProduct(productDto, pid);
		
		if(updatedProduct) {
			return Results.json().render("Product SuccessFully Updated");
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	
	public Result deleteProduct(DeleteProductDto obj) {
		Product deletedProduct = productDao.deleteProduct(obj.pid, obj.uid);
		
		if(deletedProduct != null) {
			return Results.json().render(deletedProduct);
		}
		return Results.badRequest().json().render("Bad Request");
	}
}
