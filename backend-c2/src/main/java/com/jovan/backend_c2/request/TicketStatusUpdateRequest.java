package com.jovan.backend_c2.request;

import com.jovan.backend_c2.enumeration.TicketStatus;

public class TicketStatusUpdateRequest {

	
	private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
