package com.kurek.request_app.service;

import com.kurek.request_app.dto.CancellationDTO;
import com.kurek.request_app.dto.ContentUpdateDTO;
import com.kurek.request_app.dto.RequestDTO;
import com.kurek.request_app.dto.StatusUpdateDTO;
import com.kurek.request_app.entity.Request;
import com.kurek.request_app.exception.RequestNotFound;
import com.kurek.request_app.repository.RequestRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<RequestDTO> getRequests(Integer page, Integer pageSize, String name) {


        return requestRepository.findAllByNameContaining(name, PageRequest.of(page, pageSize)).stream()
                .map(RequestDTO::new)
                .toList();
    }

    public RequestDTO createRequest(RequestDTO requestDTO) {
        Request request = new Request(requestDTO.getName(), requestDTO.getContent());

        requestRepository.save(request);
        return new RequestDTO(request);
    }

    @Transactional
    public RequestDTO updateContent(Long requestId, ContentUpdateDTO contentUpdateDTO) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(RequestNotFound::new);

        request.modifyContent(contentUpdateDTO.getNewContent());

        requestRepository.save(request);
        return new RequestDTO(request);
    }

    @Transactional
    public RequestDTO updateStatus(Long requestId, StatusUpdateDTO statusUpdateDTO) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(RequestNotFound::new);

        request.changeStatusTo(statusUpdateDTO.getStatus());

        requestRepository.save(request);
        return new RequestDTO(request);
    }

    @Transactional
    public RequestDTO cancelRequest(Long requestId, CancellationDTO cancellationDTO) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(RequestNotFound::new);

        request.cancel(cancellationDTO.getReason());

        requestRepository.save(request);
        return new RequestDTO(request);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<RequestDTO> getHistory(long requestId) {
        final List<Request> requests = AuditReaderFactory.get(entityManager)
                .createQuery()
                .forRevisionsOfEntity(Request.class, true, true)
                .add(AuditEntity.id().eq(requestId))
                .getResultList();

        return CollectionUtils.emptyIfNull(requests).stream()
                .map(RequestDTO::new)
                .toList();
    }
}


