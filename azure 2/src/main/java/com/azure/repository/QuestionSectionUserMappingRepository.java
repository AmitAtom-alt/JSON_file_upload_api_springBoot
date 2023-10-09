package com.azure.repository;

import com.azure.entity.QuestionSectionUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionSectionUserMappingRepository extends JpaRepository<QuestionSectionUserMapping, Long> {
}
