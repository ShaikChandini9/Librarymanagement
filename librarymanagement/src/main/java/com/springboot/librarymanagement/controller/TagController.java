package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.request.TagRequest;
import com.springboot.librarymanagement.response.TagResponse;
import com.springboot.librarymanagement.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/add-tag")
    public TagResponse create(@RequestBody TagRequest request) {
        return tagService.createTag(request);
    }

    @PutMapping("/{id}")
    public TagResponse update(@PathVariable Long id, @RequestBody TagRequest request) {
        return tagService.updateTag(id, request);
    }

    @GetMapping("/{id}")
    public TagResponse get(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    @GetMapping("/all")
    public List<TagResponse> getAll() {
        return tagService.getAllTags();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
