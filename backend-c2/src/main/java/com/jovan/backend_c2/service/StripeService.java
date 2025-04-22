package com.jovan.backend_c2.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {

	@Value("${stripe.secret.key}")
	private String stripeSecretKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecretKey;
	}

	public String createPaymentIntent(Double amount, String currency) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("currency", currency);
		params.put("automatic_payment_methods", Map.of("enabled", true));
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent.getClientSecret();
	}
}
