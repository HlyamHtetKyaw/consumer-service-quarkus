package com.hlyam.consumer.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsumerService {
	public String consume(String food) {
		return "Consumer eats "+food;
	}
}
