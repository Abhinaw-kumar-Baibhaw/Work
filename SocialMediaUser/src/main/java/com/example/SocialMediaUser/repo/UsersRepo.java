package com.example.SocialMediaUser.repo;

import com.example.SocialMediaUser.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpaUsersRepo")
public interface UsersRepo extends JpaRepository<Users,Long> {
    List<Users> findByEmailContaining(String email);
    Users findByEmail(String username);
    List<Users> findByName(String name);
    List<Users> findByRole(String role);
}
