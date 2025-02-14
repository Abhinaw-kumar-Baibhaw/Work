package com.example.SocialMediaUser.repo;

import com.example.SocialMediaUser.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String username);
}
