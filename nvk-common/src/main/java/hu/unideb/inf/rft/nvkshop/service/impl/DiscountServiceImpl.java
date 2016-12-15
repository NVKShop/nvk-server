package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.inf.rft.nvkshop.entities.product.ActiveDiscount;
import hu.unideb.inf.rft.nvkshop.entities.product.Discount;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;
import hu.unideb.inf.rft.nvkshop.repositories.ActiveDiscountDao;
import hu.unideb.inf.rft.nvkshop.repositories.ProductDao;
import hu.unideb.inf.rft.nvkshop.repositories.ScheduledDiscountDao;
import hu.unideb.inf.rft.nvkshop.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private ScheduledDiscountDao scheduledDiscountDao;

	@Autowired
	private ActiveDiscountDao activeDiscountDao;

	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void activateDiscount(Long scheduledDiscountId) {
		ScheduledDiscount scheduledDiscount = scheduledDiscountDao.findOne(scheduledDiscountId);

		List<ActiveDiscount> activeDiscounts = new LinkedList<>();

		for (Discount discount : scheduledDiscount.getDiscounts()) {

			for (Product product : discount.getProducts()) {
				Double newPrice = discount.modifyPrice(product.getPrice());
				Double oldPrice = product.getPrice();

				ActiveDiscount activeDiscount = ActiveDiscount.builder().newPrice(newPrice).oldPrice(oldPrice)
						.product(product).build();

				activeDiscounts.add(activeDiscount);
			}

		}
		activeDiscountDao.save(activeDiscounts);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void save(ScheduledDiscount discount) {
		scheduledDiscountDao.save(discount);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void terminateDiscount(Long scheduledDiscountId) {
		ScheduledDiscount scheduledDiscount = scheduledDiscountDao.findOne(scheduledDiscountId);

		List<ActiveDiscount> activeDiscounts = activeDiscountDao.findByScheduledDiscount(scheduledDiscount);

		List<Product> modifiedProducts = new LinkedList<>();

		for (ActiveDiscount activeDiscount : activeDiscounts) {
			Double oldPrice = activeDiscount.getOldPrice();
			Product product = activeDiscount.getProduct();
			product.setPrice(oldPrice);
			modifiedProducts.add(product);
		}
		productDao.save(modifiedProducts);
		activeDiscountDao.delete(activeDiscounts);
	}

}
