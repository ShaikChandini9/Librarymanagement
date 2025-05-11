package com.springboot.librarymanagement.service;

import com.springboot.librarymanagement.request.TagRequest;
import com.springboot.librarymanagement.response.TagResponse;

import java.util.List;

public interface TagService {

    TagResponse createTag(TagRequest request);
    TagResponse updateTag(Long id, TagRequest request);
    void deleteTag(Long id);
    TagResponse getTag(Long id);
    List<TagResponse> getAllTags();
}
