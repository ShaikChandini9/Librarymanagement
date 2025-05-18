package com.springboot.librarymanagement.specification;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.request.SearchCriteriaRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> buildFromCriteria(SearchCriteriaRequest criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getBooktitle() != null && !criteria.getBooktitle().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("booktitle")), "%" + criteria.getBooktitle().toLowerCase() + "%"));
            }

            if (criteria.getAuthorname() != null && !criteria.getAuthorname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("authorname")), "%" + criteria.getAuthorname().toLowerCase() + "%"));
            }

            if (criteria.getCategoryName() != null && !criteria.getCategoryName().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("category").get("name")), criteria.getCategoryName().toLowerCase()));
            }

            if (Boolean.TRUE.equals(criteria.getAvailablecount())) {
                predicates.add(cb.greaterThan(root.get("availabilityCount"), 0));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
