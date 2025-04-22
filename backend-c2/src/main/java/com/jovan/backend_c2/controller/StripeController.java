package com.jovan.backend_c2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovan.backend_c2.request.PaymentRequest;
import com.jovan.backend_c2.response.PaymentErrorResponse;
import com.jovan.backend_c2.response.PaymentIntentResponse;
import com.jovan.backend_c2.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/stripe")
public class StripeController {

	
	private final StripeService stripeService;
	
	public StripeController(StripeService stripeService) {
		this.stripeService = stripeService;
	}
	
	@PostMapping("/create-payment-intent")
	public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
	    try {
	        String clientSecret = stripeService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());
	        PaymentIntentResponse response = new PaymentIntentResponse(clientSecret, "Payment intent created successfully");
	        return ResponseEntity.ok(response);
	    } catch (StripeException e) {
	        PaymentErrorResponse paymentResponseError = new PaymentErrorResponse(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentResponseError);
	    } catch (Exception e) {
	        PaymentErrorResponse paymentResponseError = new PaymentErrorResponse("UNKNOWN_ERROR");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentResponseError);
	    }
	}
}