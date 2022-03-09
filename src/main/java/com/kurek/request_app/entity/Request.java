package com.kurek.request_app.entity;

import com.kurek.request_app.exception.ContentModificationException;
import com.kurek.request_app.model.RequestStatus;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private RequestStatus status;

    private String name;

    private String content;

    private Long number;

    public long getId() {
        return id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Long getNumber() {
        return number;
    }

    public Request() {
    }

    public Request(String name, String content) {
        this.name = name;
        this.content = content;
        this.status = new RequestStatus();
    }

    public void modifyContent(String newContent) {
        if (this.status.isPossibleToModifyContent()) {
            this.content = newContent;
        } else {
            throw new ContentModificationException();
        }
    }

    public void changeStatusTo(String statusValue) {
        final var newStatus = RequestStatus.Status.valueOf(statusValue);
        this.status.changeStatusTo(newStatus);

        if (newStatus == RequestStatus.Status.PUBLISHED) {
            publish();
        }
    }

    public void cancel(String reason) {
        this.status.rejectOrDelete(reason);
    }

    public void publish() {
        this.number = this.id; // id is unique number value, so there is no need to use any sequence generator
    }
}
                