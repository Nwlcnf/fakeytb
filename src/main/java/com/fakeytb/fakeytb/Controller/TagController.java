package com.fakeytb.fakeytb.Controller;

import com.fakeytb.fakeytb.DTO.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fakeytb.fakeytb.Model.Tag;
import com.fakeytb.fakeytb.Repository.TagRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        if (tagRepository.findByTitle(tag.getTitle()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un tag qui porte ce nom existe déjà");
        }
        Tag createdTag = tagRepository.save(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @PutMapping("/tags/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable UUID tagId, @RequestBody TagDTO updatedTagDTO) {
        Tag existingTag = tagRepository.findById(tagId).orElse(null);
        if (existingTag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le tag à modifier n'existe pas");
        }
        existingTag.setTitle(updatedTagDTO.getTitle());
        Tag savedTag = tagRepository.save(existingTag);
        return ResponseEntity.ok(savedTag);
    }

    @GetMapping("/tags/{tagTitle}")
    public ResponseEntity<Tag> getTagByTitle(@PathVariable String tagTitle) {
        if (tagRepository.findByTitle(tagTitle) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le tag n'existe pas");
        }
        Tag tag = tagRepository.findByTitle(tagTitle);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/tags/similaire/{tagTitle}")
    public ResponseEntity<List<Tag>>getTagSimilaire(@PathVariable String tagTitle) {
        List<Tag> tags = tagRepository.findByTitleContaining(tagTitle);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID tagId) {
        if (tagRepository.findById(tagId).orElse(null) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le tag à supprimer n'existe pas");
        }
        tagRepository.deleteById(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
