package com.springboot.librarymanagement.request;

public class SearchCriteriaRequest {

    private String Booktitle;
    private String authorname;
    private String categoryName;
    private Boolean availablecount;

    public String getBooktitle() {
        return Booktitle;
    }

    public void setBooktitle(String booktitle) {
        Booktitle = booktitle;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getAvailablecount() {
        return availablecount;
    }

    public void setAvailablecount(Boolean availablecount) {
        this.availablecount = availablecount;
    }
}
