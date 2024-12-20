package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.Entity.Product;
import com.product.model.OrderReport;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public Product createProduct(@Valid @RequestBody Product product) {
		return productService.createProduct(product);
	}

	@GetMapping("/all")
	public List<Product> getAllProduct() {
		return productService.getAllProduct();
	}

	@GetMapping("/{productId}")
	public Product getproductById(@PathVariable String productId) {
		return productService.getProductById(productId);
	}

	@PostMapping("/order")
	public OrderReport order(@RequestBody OrderReport orderReport) {
		return productService.order(orderReport);
	}
}
