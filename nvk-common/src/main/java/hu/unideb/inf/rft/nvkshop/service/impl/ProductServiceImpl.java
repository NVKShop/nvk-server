package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Category;
import hu.unideb.inf.rft.nvkshop.entities.product.Item;
import hu.unideb.inf.rft.nvkshop.entities.product.Order;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveAddress;
import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveItem;
import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveOrder;
import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveProduct;
import hu.unideb.inf.rft.nvkshop.eventhandling.EmailSendingEvent;
import hu.unideb.inf.rft.nvkshop.eventhandling.EventType;
import hu.unideb.inf.rft.nvkshop.repositories.ActiveDiscountDao;
import hu.unideb.inf.rft.nvkshop.repositories.ArchiveOrderDao;
import hu.unideb.inf.rft.nvkshop.repositories.ProductDao;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.util.ProductSearch;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.product.ProductValidator;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductValidator validator;

	@Autowired
	private ActiveDiscountDao activeDiscountDao;

	@Autowired
	private ArchiveOrderDao archiveOrderDao;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public Page<Product> search(ProductSearch search) {

		Sort sort = new Sort(search.getSortDirection(), Arrays.asList(search.getSortBy()));
		PageRequest pageRequest = new PageRequest(search.getFrom(), search.getPageSize(), sort);
		return productDao.search(ObjectUtils.defaultIfNull(search.getSearchTerm(), StringUtils.EMPTY),
				search.getInCategories(), ObjectUtils.defaultIfNull(search.getLowerPrice(), 0D),
				ObjectUtils.defaultIfNull(search.getUpperPrice(), Double.MAX_VALUE), pageRequest);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productDao.findByCategory(category);
	}

	@Override
	public Product addProduct(Product product) throws ValidationException {
		validator.validate(product);
		return productDao.save(product);
	}

	@Override
	public List<Product> getDiscountedProducts() {
		return activeDiscountDao.findDiscountedProducts();
	}

	@Override
	public void placeOrder(Order order) {

		ArchiveOrder archiveOrder = archive(order);

		Map<String, Object> emailPayload = new HashMap<>();

		emailPayload.put("userName", order.getUser().getUserName());
		emailPayload.put("items", order.getItems());
		emailPayload.put("total", getTotalOfOrder(order));
		EmailSendingEvent event = new EmailSendingEvent(EventType.PURCHASE, emailPayload, order.getUser().getEmail());
		eventPublisher.publishEvent(event);
		archiveOrderDao.save(archiveOrder);
	}

	private ArchiveOrder archive(Order order) {

		ArchiveAddress archiveAddress = new ArchiveAddress();
		archiveAddress.setCity(order.getAddress().getCity());
		archiveAddress.setDescription(order.getAddress().getDescription());
		archiveAddress.setCountry(order.getAddress().getCountry());
		archiveAddress.setPhoneNumber(order.getAddress().getPhoneNumber());
		archiveAddress.setRecipient(order.getAddress().getRecipient());
		archiveAddress.setStreet(order.getAddress().getZipCode());
		archiveAddress.setZipCode(order.getAddress().getZipCode());

		ArchiveOrder archiveOrder = new ArchiveOrder();
		archiveOrder.setArchiveAddress(archiveAddress);
		archiveOrder.setUser(order.getUser());

		List<ArchiveItem> archiveItems = new ArrayList<>();

		for (Item item : order.getItems()) {
			ArchiveItem archiveItem = new ArchiveItem();
			archiveItem.setQuantity(item.getQuantity());

			ArchiveProduct archiveProduct = new ArchiveProduct();
			archiveProduct.setDescription(item.getProduct().getDescription());
			archiveProduct.setName(item.getProduct().getName());
			archiveProduct.setPrice(item.getProduct().getPrice());
			archiveProduct.setProduct(item.getProduct());
			archiveItem.setProduct(archiveProduct);
			archiveItems.add(archiveItem);
		}

		archiveOrder.setItems(archiveItems);

		return archiveOrder;
	}

	@Override
	public Double getTotalOfOrder(Order order) {
		return order.getItems().stream().map((Item i) -> i.getProduct().getPrice() * i.getQuantity())
				.reduce(Double::sum).orElse(0D);
	}

	@Override
	public Product findById(Long id) {
		return productDao.findOne(id);
	}

}
