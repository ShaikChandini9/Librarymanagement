package com.springboot.librarymanagement.repository;

import com.springboot.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBooktitleAndAuthornameAndIsbn(String booktitle, String authorname, String isbn);

}
