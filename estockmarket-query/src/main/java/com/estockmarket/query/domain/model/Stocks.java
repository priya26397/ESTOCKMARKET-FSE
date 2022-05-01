package com.estockmarket.query.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stocks {

	@Id
	private long id;
	private String companyCode;
	private Double price;
	private Long  createdOn;
	private Long  updatedOn;
	@Version
	private Integer version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long  getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long  createdOn) {
		this.createdOn = createdOn;
	}

	public Long  getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Long  updatedOn) {
		this.updatedOn = updatedOn;
	}

}
