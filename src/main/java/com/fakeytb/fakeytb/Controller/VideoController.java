package com.fakeytb.fakeytb.Controller;

import com.fakeytb.fakeytb.DTO.VideoDTO;
import com.fakeytb.fakeytb.Model.Tag;
import com.fakeytb.fakeytb.Model.Video;
import com.fakeytb.fakeytb.Repository.TagRepository;
import com.fakeytb.fakeytb.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/videos")
    public ResponseEntity<Iterable<Video>> getVideos() {
        Iterable<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }
    @PostMapping("/videos")
    public ResponseEntity<VideoDTO> createVideo(@RequestBody VideoDTO videoDTO) {
        Video video = convertToEntity(videoDTO);
        Video createdVideo = videoRepository.save(video);
        VideoDTO createdVideoDTO = convertToDTO(createdVideo);
        return new ResponseEntity<>(createdVideoDTO, HttpStatus.CREATED);
    }

    private Video convertToEntity(VideoDTO videoDTO) {
        Video video = new Video();
        video.setTitle(videoDTO.getTitle());
        video.setShortDescription(videoDTO.getShortDescription());
        video.setLongDescription(videoDTO.getLongDescription());
        return video;
    }
    private VideoDTO convertToDTO(Video video) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle(video.getTitle());
        videoDTO.setShortDescription(video.getShortDescription());
        videoDTO.setLongDescription(video.getLongDescription());
        return videoDTO;
    }

    @GetMapping("/videos/{videoTitle}")
    public ResponseEntity<Video> getVideoByTitle(@PathVariable String videoTitle) {
        Video video = videoRepository.findByTitle(videoTitle);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/videos/tag/{tagTitle}")
    public ResponseEntity<List<Video> >getVideoByTag(@PathVariable String tagTitle) {
        if(tagRepository.findByTitle(tagTitle) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le tag n'existe pas");
        }
        List<Video> videos = videoRepository.findByTagsTitle(tagTitle);
        if (videos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune vidéo avec le tag spécifié trouvé");
        }
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/videos/similaire/titre/{videoTitle}")
    public ResponseEntity<List<Video>> getVideoSimilaireByTitle(@PathVariable String videoTitle) {
        List<Video> videos = videoRepository.findByTitleContaining(videoTitle);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/videos/similaire/description/{videoDescription}")
    public ResponseEntity<List<Video>> getVideoSimilaireByDesc(@PathVariable String videoDescription) {
        List<Video> videos = videoRepository.findByshortDescriptionContaining(videoDescription);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("/videos/{videoId}/tags/{tagId}")
    public ResponseEntity<Video> addTagToVideo(@PathVariable UUID videoId, @PathVariable UUID tagId) {
        Video video = videoRepository.findById(videoId).orElse(null);
        if (video == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La vidéo n'existe pas");
        }
        Tag tag = tagRepository.findById(tagId).orElse(null);
        if (tag == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le tag n'existe pas");
        }
        video.getTags().add(tag);
        Video updatedVideo = videoRepository.save(video);

        return ResponseEntity.ok(updatedVideo);
    }

    @PutMapping("/videos/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable UUID videoId, @RequestBody VideoDTO updatedVideoDTO) {
        if (!videoRepository.existsById(videoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La vidéo à modifier n'existe pas");
        }

        Video existingVideo = videoRepository.findById(videoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "La vidéo à modifier n'existe pas"));

        if (updatedVideoDTO.getTitle() != null) {
            existingVideo.setTitle(updatedVideoDTO.getTitle());
        }
        if (updatedVideoDTO.getShortDescription() != null) {
            existingVideo.setShortDescription(updatedVideoDTO.getShortDescription());
        }
        if (updatedVideoDTO.getLongDescription() != null) {
            existingVideo.setLongDescription(updatedVideoDTO.getLongDescription());
        }
        if (updatedVideoDTO.getTags() != null) {
            Set<Tag> tags = updatedVideoDTO.getTags().stream()
                    .map(tagTitle -> tagRepository.findByTitle(tagTitle))
                    .collect(Collectors.toSet());
            existingVideo.setTags(tags);
        }

        Video savedVideo = videoRepository.save(existingVideo);
        return ResponseEntity.ok(savedVideo);
    }

    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID videoId) {
        videoRepository.deleteById(videoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

