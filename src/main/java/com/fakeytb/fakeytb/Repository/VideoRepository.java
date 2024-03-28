package com.fakeytb.fakeytb.Repository;

import com.fakeytb.fakeytb.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    Video findByTitle(String videoTitle);
    List<Video> findByTagsTitle(String tagTitle);
    List<Video> findByTitleContaining(String videoTitle);

    List<Video> findByshortDescriptionContaining(String videoDescription);
}
