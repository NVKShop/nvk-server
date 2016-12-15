package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;

public interface DiscountService {

	void save(ScheduledDiscount discount);

	void activateDiscount(Long scheduledDiscountId);

	void terminateDiscount(Long discountId);
}
