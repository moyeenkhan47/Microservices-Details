package com.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReport {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String orderId;
	@NotEmpty
	private String productId;
	@NotEmpty
	private String productName;
	@NotNull
	private Long price;
	@NotEmpty
	private String userId;
	@Email
	private String email;
	@NotEmpty
	private String fullName;

}
