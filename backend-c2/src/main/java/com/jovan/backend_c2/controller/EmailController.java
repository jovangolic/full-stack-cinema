package com.jovan.backend_c2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovan.backend_c2.request.EmailAttachementRequest;
import com.jovan.backend_c2.request.EmailRequest;
import com.jovan.backend_c2.service.IEmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
	
	private final IEmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest){
		emailService.sendEmail(emailRequest.to(), emailRequest.subject(), emailRequest.text());
		return  ResponseEntity.ok("Email send successfully");
	}
	
	
	@PostMapping("send-with-attachemt")
	public ResponseEntity<String> sendEmailWithAttachment(@RequestBody EmailAttachementRequest request){
		try {
			emailService.sendEmailWithAttachment(request.to(), request.subject(), request.text(), request.attachmentFilePath());
			return ResponseEntity.ok("Email sent successfully with attachment.");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email"+ e.getMessage());
		}
	}
}
