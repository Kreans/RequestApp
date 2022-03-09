package com.kurek.request_app.repository;

import com.kurek.request_app.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByNameContaining(String status, Pageable pageable);
}