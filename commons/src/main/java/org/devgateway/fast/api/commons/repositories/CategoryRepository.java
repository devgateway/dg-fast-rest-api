package org.devgateway.fast.api.commons.repositories;

import org.devgateway.fast.api.commons.domain.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Generated by Spring Data Generator on 23/04/2020
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>, QuerydslPredicateExecutor<Category> {


    Category findByValue(String value);
}
