package com.springboot.librarymanagement.repository;

import com.springboot.librarymanagement.entity.Book;
import com.springboot.librarymanagement.entity.Category;
import com.springboot.librarymanagement.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //List<Book> findByCategory(Category category);
    //List<Book> findByTags(Tag tag);
    Optional<Book> findByBooktitleAndAuthornameAndIsbn(String booktitle, String authorname, String isbn);

}
