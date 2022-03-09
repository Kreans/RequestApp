package com.kurek.request_app.dto;

import javax.validation.constraints.NotEmpty;

public class ContentUpdateDTO {

    @NotEmpty(message = "Please provide a new content")
    private String newContent;

    public String getNewContent() {
        return newContent;
    }

}

