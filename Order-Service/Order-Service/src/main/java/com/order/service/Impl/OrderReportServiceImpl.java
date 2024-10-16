package com.order.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.entity.OrderReport;
import com.order.exception.ResourceNotFoundException;
import com.order.repository.OrderReportRepository;
import com.order.service.OrderReportService;



@Service
public class OrderReportServiceImpl  implements OrderReportService{
	@Autowired
	private OrderReportRepository orderReportRepository;

	@Override
	public OrderReport createOrderReport(OrderReport orderReport) {
		
	return orderReportRepository.save(orderReport);
	}

	@Override
	public OrderReport getOrderReportById(String orderId) {
	return orderReportRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Resource with ID " + orderId + " not found"));
	
	}

	@Override
	public List<OrderReport> getAllOrderReport() {
		return orderReportRepository.findAll();
	}

}
