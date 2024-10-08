package com.product.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.model.OrderReport;

@FeignClient(name="ORDER-SERVICE")
public interface OrderClient {
	@PostMapping("/api/order/add")
	public OrderReport order (@RequestBody OrderReport orderReport);

}
