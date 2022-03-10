package com.kurek.request_app.integration;

import com.kurek.request_app.dto.ContentUpdateDTO;
import com.kurek.request_app.dto.RequestDTO;
import com.kurek.request_app.dto.StatusUpdateDTO;
import com.kurek.request_app.entity.Request;
import com.kurek.request_app.model.RequestStatus;
import com.kurek.request_app.service.RequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Mock
    private ContentUpdateDTO contentUpdateDTO;

    @Mock
    private StatusUpdateDTO statusUpdateDTO;

    @Test
    void shouldGenerateHistory() {

        final var requestData = createRequestData();
        Mockito.when(contentUpdateDTO.getNewContent()).thenReturn("updatedContent");
        Mockito.when(statusUpdateDTO.getStatus()).thenReturn(RequestStatus.Status.VERIFIED.toString());

        final var added = requestService.createRequest(requestData);
        requestService.updateContent(added.getId(), contentUpdateDTO);
        requestService.updateStatus(added.getId(), statusUpdateDTO);

        final var history = requestService.getHistory(added.getId());
        final int expectedRecordsNumber = 3; // 1 -> create, 2 -> modify content, 3-> modify status

        Assertions.assertEquals(expectedRecordsNumber, history.size());
    }

    @Test
    void shouldGenerateHistoryForSpecificRequest() {
        final var requestData = createRequestData();
        Mockito.when(contentUpdateDTO.getNewContent()).thenReturn("updatedContent");

        final var added = requestService.createRequest(requestData);
        final var secondAdded = requestService.createRequest(requestData);

        requestService.updateContent(added.getId(), contentUpdateDTO);
        final var history = requestService.getHistory(added.getId());
        final var secondHistory = requestService.getHistory(secondAdded.getId());

        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals(1, secondHistory.size());
    }

    private RequestDTO createRequestData() {
        Request request = new Request("name", "content");
        return new RequestDTO(request);
    }
}
