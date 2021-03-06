package org.devgateway.fast.api.example.repositories;

import org.devgateway.fast.api.example.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Generated by Spring Data Generator on 23/04/2020
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>, JpaSpecificationExecutor<Survey>, QuerydslPredicateExecutor<Survey> {


}
