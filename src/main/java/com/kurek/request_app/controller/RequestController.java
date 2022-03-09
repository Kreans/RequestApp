package com.kurek.request_app.controller;

import com.kurek.request_app.dto.CancellationDTO;
import com.kurek.request_app.dto.ContentUpdateDTO;
import com.kurek.request_app.dto.RequestDTO;
import com.kurek.request_app.dto.StatusUpdateDTO;
import com.kurek.request_app.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public List<RequestDTO> getAllRequests(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "") String name) {

        return requestService.getRequests(page, pageSize, name);
    }

    @PostMapping
    public RequestDTO createRequest(@Valid @RequestBody RequestDTO requestDTO) {
        return requestService.createRequest(requestDTO);
    }

    @PutMapping("/{id}/content")
    public RequestDTO updateDescription(@PathVariable long id, @Valid @RequestBody ContentUpdateDTO contentUpdateDTO) {
        return requestService.updateContent(id, contentUpdateDTO);
    }

    @PutMapping("/{id}/status")
    public RequestDTO updateStatus(@PathVariable long id, @Valid @RequestBody StatusUpdateDTO statusUpdateDTO) {
        return requestService.updateStatus(id, statusUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public RequestDTO cancelRequest(@PathVariable long id, @Valid @RequestBody CancellationDTO cancellationDTO) {
        return requestService.cancelRequest(id, cancellationDTO);
    }

    @GetMapping("/{id}/history")
    public List<RequestDTO> getHistory(@PathVariable long id) {
        return requestService.getHistory(id);
    }

}
