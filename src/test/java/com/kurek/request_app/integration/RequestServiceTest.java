package com.kurek.request_app.integration;

import com.kurek.request_app.dto.RequestDTO;
import com.kurek.request_app.entity.Request;
import com.kurek.request_app.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestServiceTest {


    @Autowired
    private RequestService requestService;


    @Test
    public void shouldGenerateHistory() {

        final var requestData = createRequestData();
        final var added = requestService.createRequest(requestData);


        requestService.getHistory(added.getId());


    }


    private RequestDTO createRequestData() {
        Request request = new Request("name", "content");
        return new RequestDTO(request);
    }
}
