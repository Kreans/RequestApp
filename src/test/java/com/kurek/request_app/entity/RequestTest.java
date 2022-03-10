package com.kurek.request_app.entity;

import com.kurek.request_app.exception.ContentModificationException;
import com.kurek.request_app.exception.EmptyCancelReason;
import com.kurek.request_app.exception.WrongStatusChange;
import com.kurek.request_app.model.RequestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequestTest {

    private static final String VERIFIED = RequestStatus.Status.VERIFIED.toString();
    private static final String ACCEPTED = RequestStatus.Status.ACCEPTED.toString();
    private static final String PUBLISHED = RequestStatus.Status.PUBLISHED.toString();

    @Test
    void shouldChangeStatusToPublished() {
        final var request = new Request("name", "content");

        request.changeStatusTo(VERIFIED);
        request.changeStatusTo(ACCEPTED);
        request.changeStatusTo(PUBLISHED);

        Assertions.assertEquals(RequestStatus.Status.PUBLISHED, request.getRequestStatus().getStatus());
        Assertions.assertNotNull(request.getNumber());
    }

    @Test
    void shouldNotChangeStatusToPublishedWithoutAccepted() {
        final var request = new Request("name", "content");
        request.changeStatusTo(RequestStatus.Status.VERIFIED.toString());

        Assertions.assertThrows(WrongStatusChange.class, () -> request.changeStatusTo(PUBLISHED));
    }

    @Test
    void shouldNotChangeStatusToPublishedFromCreated() {
        final var request = new Request("name", "content");

        Assertions.assertThrows(WrongStatusChange.class, () -> request.changeStatusTo(PUBLISHED));
    }

    @Test
    void shouldBeCancelledCreatedRequestChangeToDeleted() {
        final var request = new Request("name", "content");

        request.cancel("text");

        Assertions.assertEquals(RequestStatus.Status.DELETED, request.getRequestStatus().getStatus());
    }

    @Test
    void shouldBeCancelledVerifiedRequestChangeToRejected() {
        final var request = new Request("name", "content");
        request.changeStatusTo(VERIFIED);

        request.cancel("text");

        Assertions.assertEquals(RequestStatus.Status.REJECTED, request.getRequestStatus().getStatus());
    }

    @Test
    void shouldBeCancelledAcceptedRequestChangeToRejected() {
        final var request = new Request("name", "content");
        request.changeStatusTo(VERIFIED);
        request.changeStatusTo(ACCEPTED);

        request.cancel("text");

        Assertions.assertEquals(RequestStatus.Status.REJECTED, request.getRequestStatus().getStatus());
    }

    @Test
    void shouldNotBeCancelledPublishedRequest() {
        final var request = new Request("name", "content");
        request.changeStatusTo(VERIFIED);
        request.changeStatusTo(ACCEPTED);
        request.changeStatusTo(PUBLISHED);

        Assertions.assertThrows(WrongStatusChange.class, () -> request.cancel("text"));
    }

    @Test
    void shouldNotBeCancelRequestWithEmptyReason() {
        final var request = new Request("name", "content");

        Assertions.assertThrows(EmptyCancelReason.class, () -> request.cancel(""));
        Assertions.assertThrows(EmptyCancelReason.class, () -> request.cancel(null));
    }

    @Test
    void shouldChangeContentOfCreatedRequest() {
        final var request = new Request("name", "content");

        request.modifyContent("new content");

        Assertions.assertEquals("new content", request.getContent());
    }

    @Test
    void shouldChangeContentOfVerifiedRequest() {
        final var request = new Request("name", "content");
        request.changeStatusTo(VERIFIED);

        request.modifyContent("new content");

        Assertions.assertEquals("new content", request.getContent());
    }

    @Test
    void shouldNotChangeContentOfPublishedRequest() {
        final var request = new Request("name", "content");
        request.changeStatusTo(VERIFIED);
        request.changeStatusTo(ACCEPTED);
        request.changeStatusTo(PUBLISHED);

        Assertions.assertThrows(ContentModificationException.class, () -> request.modifyContent("new content"));
    }

    @Test
    void shouldNotChangeContentOfDeletedRequest() {
        final var request = new Request("name", "content");
        request.cancel("text");

        Assertions.assertThrows(ContentModificationException.class, () -> request.modifyContent("new content"));
    }
}