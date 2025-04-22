package com.jovan.backend_c2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.Hall;


@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

}
