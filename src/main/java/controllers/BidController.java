package controllers;

import com.google.inject.Inject;

import dao.BidDao;
import models.BidDto;
import models.ProductDto;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

public class BidController {
	@Inject
	BidDao bidDao;
	
	public Result bidOnProduct(@PathParam("pid") Long pid, BidDto bidDto) {
		boolean bid = bidDao.bidOnProduct(pid, bidDto);
		if(bid) {			
			return Results.json().render("Bid Successfull");
		}
		
		return Results.badRequest().json().render("Bad Request");
	}
	

	
	public Result changeBidStatus(ProductDto productDto, @PathParam("pid") Long pid) {
		
		boolean bid_status = bidDao.changeBidStatus(productDto, pid);
		if(bid_status) {			
			return Results.json().render("Bid_Status Changed SuccessFully");
		}
		
		return Results.badRequest().json().render("Bad Request");	}
}
