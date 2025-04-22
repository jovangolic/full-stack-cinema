package com.jovan.backend_c2.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

public interface IEmailService {

    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String text);
    
    @Async
    CompletableFuture<Void> sendEmailWithAttachment(String to, String subject, String text, String attachmentFilePath);
    
    @Async
    CompletableFuture<Void> sendEmailWithAttachment(String to, String subject, String text, MultipartFile attachmentFile);
}
