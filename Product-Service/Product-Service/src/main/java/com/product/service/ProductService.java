package com.product.service;

import java.util.List;

import com.product.Entity.Product;
import com.product.model.OrderReport;

public interface ProductService {
	public Product createProduct(Product product);
	public Product getProductById(String productId);
	public List<Product> getAllProduct();
	OrderReport order (OrderReport orderReport);

}
