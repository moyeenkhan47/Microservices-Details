package com.order.service;

import java.util.List;

import com.order.entity.OrderReport;


public interface OrderReportService {
	public OrderReport createOrderReport(OrderReport orderReport);
	public OrderReport getOrderReportById(String orderId);
	public List<OrderReport> getAllOrderReport();

}
