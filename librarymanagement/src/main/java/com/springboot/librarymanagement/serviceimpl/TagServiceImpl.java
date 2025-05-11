package com.springboot.librarymanagement.serviceimpl;

import com.springboot.librarymanagement.entity.Tag;
import com.springboot.librarymanagement.repository.TagRepository;
import com.springboot.librarymanagement.request.TagRequest;
import com.springboot.librarymanagement.response.TagResponse;
import com.springboot.librarymanagement.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    private TagResponse mapToResponse(Tag tag) {
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());
        return response;
    }

    @Override
    public TagResponse createTag(TagRequest request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        return mapToResponse(tagRepository.save(tag));
    }

    @Override
    public TagResponse updateTag(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(request.getName());
        return mapToResponse(tagRepository.save(tag));
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public TagResponse getTag(Long id) {
        return tagRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

}
