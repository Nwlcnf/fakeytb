package com.fakeytb.fakeytb.Repository;

import com.fakeytb.fakeytb.Model.Role;
import com.fakeytb.fakeytb.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);


    List<User> findByRole(Role role);

    List<User> findByUsernameContaining(String username);
}
