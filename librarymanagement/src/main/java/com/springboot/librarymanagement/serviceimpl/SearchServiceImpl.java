package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.repository.BookRepository;
import com.springboot.librarymanagement.request.SearchCriteriaRequest;
import com.springboot.librarymanagement.response.BookResponse;
import com.springboot.librarymanagement.service.SearchService;
import com.springboot.librarymanagement.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final BookRepository bookRepository;

    @Autowired
    public SearchServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponse> searchBooks(SearchCriteriaRequest criteria) {
        Specification<Book> spec = BookSpecification.buildFromCriteria(criteria);
        return bookRepository.findAll(spec).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
}
