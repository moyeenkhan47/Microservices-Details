package com.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.Entity.Product;
import com.product.configuration.OrderClient;
import com.product.exception.ResourceNotFoundException;
import com.product.model.OrderReport;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	private OrderClient orderClient;

	@Override
	public Product createProduct(Product product) {

		return productRepository.save(product);
	}

	@Override
	public Product getProductById(String productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + productId + " not found"));

	}

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public OrderReport order(OrderReport orderReport) {
		Product product = productRepository.findById(orderReport.getProductId()).get();
		orderReport.setProductName(product.getProductName());
		orderReport.setPrice(product.getPrice());
		return orderClient.order(orderReport);
	}

}
