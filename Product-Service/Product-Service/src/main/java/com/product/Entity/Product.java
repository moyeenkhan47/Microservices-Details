package com.product.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product-record")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String productId;
	@NotEmpty
	private String productName;
	@NotEmpty
	private String  description;
	@NotNull
	private Long price;

}
