package hu.unideb.inf.nvkshop.rest;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.unideb.inf.nvkshop.rest.vo.CartItem;
import hu.unideb.inf.nvkshop.rest.vo.OrderPlacement;
import hu.unideb.inf.rft.nvkshop.entities.product.Item;
import hu.unideb.inf.rft.nvkshop.entities.product.Order;
import hu.unideb.inf.rft.nvkshop.entities.product.Product;
import hu.unideb.inf.rft.nvkshop.entities.security.Address;
import hu.unideb.inf.rft.nvkshop.entities.security.User;
import hu.unideb.inf.rft.nvkshop.service.AddressService;
import hu.unideb.inf.rft.nvkshop.service.ProductService;
import hu.unideb.inf.rft.nvkshop.service.UserService;

@RestController
public class OrderHandlingRest {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/orderPlacement", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void placeOrder(@RequestBody OrderPlacement orders) {
		
		User u = userService.findById(1L);
		
		Address address = null;
		if (orders.getAddressId()!=null){
			address = addressService.findById(orders.getAddressId());
		}else{
			address = addressService.getDefaultAddress(u);
		}
		Order order = new Order();
		order.setUser(u);
		order.setAddress(address);
		
		List<Item> items = new LinkedList<>();
		
		for (CartItem cartItem:orders.getItems()){
			Item item = new Item();
			Product product = productService.findById(cartItem.getProductId());
			item.setProduct(product);
			item.setQuantity(cartItem.getQuantity());
			items.add(item);
		}	
		
		order.setItems(items);
		productService.placeOrder(order);
	}

}
