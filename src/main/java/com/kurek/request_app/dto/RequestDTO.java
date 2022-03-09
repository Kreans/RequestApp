package com.kurek.request_app.dto;

import com.kurek.request_app.entity.Request;

import javax.validation.constraints.NotEmpty;

public class RequestDTO {

    private long id;

    private String status;

    private String reason;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotEmpty(message = "Please provide a name")
    private String content;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.name = request.getName();
        this.status = request.getStatus().getStatus().toString();
        this.reason = request.getStatus().getReason();
        this.content = request.getContent();
    }

    public RequestDTO() {

    }
}
