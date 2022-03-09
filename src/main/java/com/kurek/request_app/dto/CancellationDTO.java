package com.kurek.request_app.dto;

import javax.validation.constraints.NotBlank;

public class CancellationDTO {

    @NotBlank(message = "Reason cannot be empty")
    private String reason;

    public String getReason() {
        return reason;
    }
}
