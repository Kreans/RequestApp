package com.kurek.request_app.model;

import com.kurek.request_app.exception.WrongStatusChange;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Embeddable
public class RequestStatus {

    private static final Map<Status, Set<Status>> possibilityChanges = Map.of(
            Status.CREATED, Set.of(Status.DELETED, Status.VERIFIED),
            Status.VERIFIED, Set.of(Status.REJECTED, Status.ACCEPTED),
            Status.ACCEPTED, Set.of(Status.REJECTED, Status.PUBLISHED),
            Status.PUBLISHED, Collections.emptySet()
    );

    public enum Status {
        CREATED, VERIFIED, ACCEPTED, PUBLISHED, REJECTED, DELETED,
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;
    private String reason;


    public Status getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public void rejectOrDelete(String reason) {
        this.status = getRejectOrDeletedStatus();
        this.reason = reason;
    }

    public boolean isPossibleToModifyContent() {
        return List.of(Status.CREATED, Status.VERIFIED).contains(status);
    }

    public void changeStatusTo(Status newStatus) {
        if (canBeChangedTo(newStatus)) {
            this.status = newStatus;
        } else {
            throw new WrongStatusChange(String.format("Cannot be changed status from %s to %s", this.status, newStatus));
        }
    }

    private boolean canBeChangedTo(Status newStatus) {
        final Set<Status> possibilitiesFromCurrentStatus = possibilityChanges.get(this.status);
        return possibilitiesFromCurrentStatus.contains(newStatus);
    }

    private Status getRejectOrDeletedStatus() {
        final Set<Status> possibilitiesFromCurrentStatus = possibilityChanges.get(this.status);


        if (possibilitiesFromCurrentStatus.contains(Status.DELETED)) {
            return Status.DELETED;
        } else if (possibilitiesFromCurrentStatus.contains(Status.REJECTED)) {
            return Status.REJECTED;
        }
        throw new WrongStatusChange("Request cannot be canceled");
    }
}
