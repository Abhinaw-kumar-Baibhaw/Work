package com.example.SocialMediaUser.serviceImplementation;

import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.repo.UsersRepo;
import com.example.SocialMediaUser.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UsersServiceImplementation implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImplementation.class);

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private HttpSession session;

    private UserDTO convertToDTO(Users users) {
        return new UserDTO(users.getId(), users.getName(), users.getEmail(), users.getRole());
    }

    @Override
//    @CacheEvict(value = "usersCache", allEntries = true)
    public ResponseEntity<UserDTO> createUser(Users users) {
        logger.info("Creating a new user with email: {}", users.getEmail());
        String password = users.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        users.setPassword(encodedPassword);
        Users createdUser = usersRepo.save(users);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return new ResponseEntity<>(convertToDTO(createdUser), HttpStatus.CREATED);
    }

    @Override
//    @Cacheable(value = "usersCache")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users from the database");
        List<Users> usersList = usersRepo.findAll();
        List<UserDTO> userDTOList = usersList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        logger.info("Fetched {} users", userDTOList.size());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @Override
//    @Cacheable(value = "userCache", key = "#id")
    public ResponseEntity<UserDTO> findById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<Users> user = usersRepo.findById(id);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", id);
            return new ResponseEntity<>(convertToDTO(user.get()), HttpStatus.OK);
        } else {
            logger.error("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Users> searchByEmail(String email) {
        logger.info("Searching for users with email containing: {}", email);
        return usersRepo.findByEmailContaining(email);
    }

    @Override
    public List<Users> searchByName(String name) {
        return usersRepo.findByName(name);
    }

    @Override
    public List<Users> searchByRole(String role) {
        return usersRepo.findByRole(role);
    }


    public void trackSession(Users authenticatedUser) {
        logger.info("Tracking session for user with email: {}", authenticatedUser.getEmail());
        session.setAttribute("user", authenticatedUser);
    }


    public Users getUserFromSession() {
        return (Users) session.getAttribute("user");
    }


    public void invalidateSession() {
        session.invalidate();
        logger.info("User session invalidated.");
    }
}
