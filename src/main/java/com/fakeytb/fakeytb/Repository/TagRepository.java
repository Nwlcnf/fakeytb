package com.fakeytb.fakeytb.Repository;

import com.fakeytb.fakeytb.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag findByTitle(String tagTitle);


    List<Tag> findByTitleContaining(String tagTitle);
}
