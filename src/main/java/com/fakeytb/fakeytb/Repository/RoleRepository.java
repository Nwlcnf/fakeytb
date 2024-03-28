package com.fakeytb.fakeytb.Repository;

import com.fakeytb.fakeytb.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByTitle(String roleTitle);
}
