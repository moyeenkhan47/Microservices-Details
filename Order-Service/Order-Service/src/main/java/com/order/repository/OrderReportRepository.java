package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entity.OrderReport;

public interface OrderReportRepository extends JpaRepository<OrderReport, String>  {

}
