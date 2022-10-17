package controllers;


import java.util.List;

import com.google.inject.Inject;

import dao.ProductDao;
import models.Product;
import models.ProductDto;
import models.ProductsDto;
import models.DeleteProductDto;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

public class ProductController {
	@Inject
	ProductDao productDao;
	
	
	public Result getAllProducts(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("title") String title, @Param("pid") Long pid, @Param("maxPrice") Long maxPrice) {
		ProductsDto products = productDao.getAllProducts(pageNo, pageSize, title, pid, maxPrice);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result totalProductCount() {
		Long totalCount = productDao.getTotalProductCount();
		if(totalCount != null) {			
			return Results.json().render(totalCount);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result totalProductCountByMaxPrice(@Param("maxPrice") Long maxPrice) {
		Long totalCount = productDao.totalProductCountByMaxPrice(maxPrice);
		if(totalCount != null) {			
			return Results.json().render(totalCount);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getSingleProduct(@PathParam("pid") Long pid) {
		Product product = productDao.getSingleProduct(pid);
		if(product != null) {			 
			return Results.json().render(product);
		}
		return Results.badRequest().json().render("Product Not Found");
	}
	
	public Result getProductByTitle(@PathParam("title") String title) {
		List<Product> products = productDao.getProductByTitle(title);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getProductByPid(@PathParam("pid") Long pid) {
		List<Product> products = productDao.getProductByPid(pid);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getProductByMaxPrice(@PathParam("maxPrice") Long maxPrice) {
		List<Product> products = productDao.getProductByMaxPrice(maxPrice);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getProductByUid(@PathParam("uid") Long uid, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
		List<Product> products = productDao.getProductByUid(uid, pageNo, pageSize);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getProductBySoldTo(@PathParam("uid") Long uid) {
		List<Product> products = productDao.getProductBySoldTo(uid);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
	}
	
	public Result getProductByTitleAndUid(@PathParam("title") String title, @PathParam("uid") Long uid) {
		List<Product> products = productDao.getProductByTitleAndUid(title, uid);
		if(products != null) {			
			return Results.json().render(products);
		}
		return Results.badRequest().json().render("Bad Request");
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
