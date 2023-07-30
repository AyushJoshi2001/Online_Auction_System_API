package controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import dao.ProductDao;
import ninja.NinjaTest;
import ninja.Result;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest extends NinjaTest {

	@Mock
	ProductDao productDao;
	
	ProductController productController;
	
	@Before
	public void setup() {
		productController = new ProductController();
		productController.productDao = productDao;
	}
	
	@Test
	public void testTotalProductCount() {
		when(productDao.getTotalProductCount()).thenReturn(5l);
		
		Result result = productController.totalProductCount();
		
		assertEquals(200, result.getStatusCode());
	}
	
	@Test
	public void testTotalProductCountWithNull() {
		when(productDao.getTotalProductCount()).thenReturn(null);
		
		Result result = productController.totalProductCount();
		
		assertEquals(400, result.getStatusCode());
	}
}
