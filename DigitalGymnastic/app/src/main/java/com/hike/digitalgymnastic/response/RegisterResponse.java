package com.hike.digitalgymnastic.response;

import com.hike.digitalgymnastic.entitiy.Customer;

/*
 * @auth changqi
 * @description 
 */
public class RegisterResponse extends BaseResponse{
	public Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
