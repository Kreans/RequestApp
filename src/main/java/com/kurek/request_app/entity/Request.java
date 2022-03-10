package com.kurek.request_app.entity;

import com.kurek.request_app.exception.ContentModificationException;
import com.kurek.request_app.exception.EmptyCancelReason;
import com.kurek.request_app.model.RequestStatus;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private RequestStatus requestStatus;

    private String name;

    private String content;

    private Long number;

    public long getId() {
        return id;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
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
        this.requestStatus = new RequestStatus();
    }

    public void modifyContent(String newContent) {
        if (this.requestStatus.isPossibleToModifyContent()) {
            this.content = newContent;
        } else {
            throw new ContentModificationException();
        }
    }

    public void changeStatusTo(String statusValue) {
        final var newStatus = RequestStatus.Status.valueOf(statusValue);
        this.requestStatus.changeStatusTo(newStatus);

        if (newStatus == RequestStatus.Status.PUBLISHED) {
            publish();
        }
    }

    public void cancel(String reason) {
        if (StringUtils.isEmpty(reason)) {
            throw new EmptyCancelReason();
        }
        this.requestStatus.rejectOrDelete(reason);
    }

    private void publish() {
        this.number = this.id; // id is unique number value, so there is no need to use any sequence generator
    }
}
                