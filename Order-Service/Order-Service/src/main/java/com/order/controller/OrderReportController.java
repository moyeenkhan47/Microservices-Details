package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.entity.OrderReport;
import com.order.service.OrderReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/order")
public class OrderReportController {
	@Autowired
	private OrderReportService orderReportService;
	
	@PostMapping("/add")
	public OrderReport creatOrder( @Valid @RequestBody OrderReport orderReport) {
		return orderReportService.createOrderReport(orderReport);
	}
	@GetMapping("/all")
	public List<OrderReport>  getAllOrder() {
		return orderReportService.getAllOrderReport();
	}
@GetMapping("/{orderId}")
public OrderReport getOrderById(@PathVariable String  orderId) {
	return orderReportService.getOrderReportById(orderId);
}
}
