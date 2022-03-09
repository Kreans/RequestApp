package com.kurek.request_app.entity;

import com.kurek.request_app.model.RequestStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RequestTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    Request request;

    @Test
    public void shouldChangeContentInVerifiedStatus() {
        Mockito.when(request.getStatus().getStatus()).thenReturn(RequestStatus.Status.CREATED);

        request.modifyContent("new");

        assertEquals("new", request.getContent());
    }
}