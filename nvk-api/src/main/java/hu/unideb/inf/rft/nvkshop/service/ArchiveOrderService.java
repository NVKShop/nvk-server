package hu.unideb.inf.rft.nvkshop.service;

import java.util.List;

import hu.unideb.inf.rft.nvkshop.entities.product.archive.ArchiveOrder;
import hu.unideb.inf.rft.nvkshop.entities.security.User;

public interface ArchiveOrderService {

	List<ArchiveOrder> findByUser(User user);

}
