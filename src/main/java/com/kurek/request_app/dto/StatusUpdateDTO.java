package com.kurek.request_app.dto;

import javax.validation.constraints.Pattern;

public class StatusUpdateDTO {

    @Pattern(regexp = "VERIFIED|ACCEPTED|PUBLISHED")
    private String status;

    public String getStatus() {
        return status;
    }


}
