package com.assignment.numberGenerator.repository;


import com.assignment.numberGenerator.domain.NumberGeneratorRequestWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface NumberGeneratorRepository extends JpaRepository<NumberGeneratorRequestWrapper, UUID> {
}
