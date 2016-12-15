package hu.unideb.inf.rft.nvkshop.service;

import java.util.Date;
import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.Discount;
import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;

public interface ScheduledDiscountService {

	void createScheduledDiscountJob(List<Discount> discounts,Date from,Date until);
	
	void unScheduleDiscount(ScheduledDiscount discount);	

}
