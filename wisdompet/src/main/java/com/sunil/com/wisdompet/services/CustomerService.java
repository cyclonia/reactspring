package com.sunil.com.wisdompet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sunil.com.wisdompet.data.entities.CustomerEntity;
import com.sunil.com.wisdompet.data.repositories.CustomerRepository;
import com.sunil.com.wisdompet.web.errors.NotFoundException;
import com.sunil.com.wisdompet.web.models.Customer;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getAllCustomers(String filterEmail) {

		List<Customer> customers = new ArrayList<>();
		if (StringUtils.hasLength(filterEmail)) {
			CustomerEntity customerEntity = this.customerRepository.findByEmail(filterEmail);
			customers.add(this.translateDbToWeb(customerEntity));
		} else {
			Iterable<CustomerEntity> entities = this.customerRepository.findAll();
			entities.forEach(entity -> customers.add(this.translateDbToWeb(entity)));
		}
		return customers;
	}

	public Customer getCustomer(long id) {
		Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findById(id);
		if (optionalCustomerEntity.isEmpty()) {
			throw new NotFoundException("Customer Not Found with Given ID" + id);
		}
		return this.translateDbToWeb(optionalCustomerEntity.get());
	}

	public Customer createOrUpdateCustomer(Customer customer) {
		CustomerEntity customerEntity = this.translateWebToDb(customer);
		customerEntity = this.customerRepository.save(customerEntity);
		return this.translateDbToWeb(customerEntity);
	}

	public void deleteCustomer(long id) {
		this.customerRepository.deleteById(id);
	}

	private CustomerEntity translateWebToDb(Customer customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(customer.getCustomerId());
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setEmail(customer.getEmailAddress());
		customerEntity.setPhone(customer.getPhoneNumber());
		customerEntity.setAddress(customer.getAddress());

		return customerEntity;
	}

	private Customer translateDbToWeb(CustomerEntity customerEntity) {
		return new Customer(customerEntity.getId(), customerEntity.getFirstName(), customerEntity.getLastName(),
				customerEntity.getEmail(), customerEntity.getPhone(), customerEntity.getAddress());
	}

}
