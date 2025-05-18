package com.springboot.librarymanagement.response;

import com.springboot.librarymanagement.entity.Book;

import java.util.List;

public class BookResponse {

    private Long bookid;
    private String booktitle;
    private String authorname;
    private String isbn;
    private int availabilityCount;

    public BookResponse(Book book) {
        this.bookid = book.getBookid();
        this.booktitle = book.getBooktitle();
        this.authorname = book.getAuthorname();
        this.isbn = book.getIsbn();
        this.availabilityCount = book.getAvailabilityCount();
    }
    private CategoryResponse category;
    private List<TagResponse> tags;

    public BookResponse() {

    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

    public int getAvailabilityCount() {
        return availabilityCount;
    }

    public void setAvailabilityCount(int availabilityCount) {
        this.availabilityCount = availabilityCount;
    }
}
