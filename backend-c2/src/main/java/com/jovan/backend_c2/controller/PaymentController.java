package com.jovan.backend_c2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovan.backend_c2.model.Payment;
import com.jovan.backend_c2.request.PaymentRequest;
import com.jovan.backend_c2.response.PaymentErrorResponse;
import com.jovan.backend_c2.response.PaymentResponse;
import com.jovan.backend_c2.service.PaymentService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	//ako zelim da vratim entite direkto kao odgovor
		/*@PostMapping("/process")
		public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request){
			try {
	            Payment payment = paymentService.processPayment(request.getAmount(), request.getCurrency());
	            return ResponseEntity.ok(payment);
	        } catch (StripeException e) {
	            return ResponseEntity.badRequest().build();
	        }
		}*/
		
		@PostMapping("/procces")
		public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request){
			try {
				Payment payment = paymentService.processPayment(request.getAmount(), request.getCurrency(),request.getUserId());
				PaymentResponse response = new PaymentResponse(payment.getId(), payment.getAmount(), payment.getCurrency(), payment.getStatus() + "Payment intent created successfully");
				return ResponseEntity.ok(response);
			}
			catch(StripeException e) {
				PaymentErrorResponse errorResponse = new PaymentErrorResponse("Payment failed: "+e.getMessage(),HttpStatus.BAD_REQUEST.value());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}
		}

}
