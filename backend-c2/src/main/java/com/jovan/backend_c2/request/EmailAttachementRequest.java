package com.jovan.backend_c2.request;

public record EmailAttachementRequest(String to, String subject, String text, String attachmentFilePath) {

}
