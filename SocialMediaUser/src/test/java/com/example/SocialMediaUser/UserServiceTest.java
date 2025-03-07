package com.example.SocialMediaUser;


import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.repo.UsersRepo;
import com.example.SocialMediaUser.serviceImplementation.UsersServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class UsersServiceImplementationTest {

    @InjectMocks
    private UsersServiceImplementation usersService;

    @Mock
    private UsersRepo usersRepo;

    @Mock
    private HttpSession session;

    private Users user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new Users(1L, "Test User", "test@example.com", "ROLE_USER", "password123");
        userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        when(usersRepo.save(any(Users.class))).thenReturn(user);
        ResponseEntity<UserDTO> response = usersService.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usersRepo, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        when(usersRepo.findAll()).thenReturn(Arrays.asList(user));
        ResponseEntity<List<UserDTO>> response = usersService.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(usersRepo, times(1)).findAll();
    }

    @Test
    void testFindById_UserExists() {
        when(usersRepo.findById(user.getId())).thenReturn(Optional.of(user));
        ResponseEntity<UserDTO> response = usersService.findById(user.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usersRepo, times(1)).findById(user.getId());
    }

    @Test
    void testFindById_UserNotFound() {
        when(usersRepo.findById(user.getId())).thenReturn(Optional.empty());
        ResponseEntity<UserDTO> response = usersService.findById(user.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usersRepo, times(1)).findById(user.getId());
    }

    @Test
    void testSearchByEmail() {
        when(usersRepo.findByEmailContaining("test@example.com")).thenReturn(Arrays.asList(user));
        List<Users> result = usersService.searchByEmail("test@example.com");
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(usersRepo, times(1)).findByEmailContaining("test@example.com");
    }

    @Test
    void testSearchByName() {
        when(usersRepo.findByName("Test User")).thenReturn(Arrays.asList(user));
        List<Users> result = usersService.searchByName("Test User");
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(usersRepo, times(1)).findByName("Test User");
    }

    @Test
    void testSearchByRole() {
        when(usersRepo.findByRole("ROLE_USER")).thenReturn(Arrays.asList(user));
        List<Users> result = usersService.searchByRole("ROLE_USER");
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(usersRepo, times(1)).findByRole("ROLE_USER");
    }

    @Test
    void testTrackSession() {
        usersService.trackSession(user);
        verify(session, times(1)).setAttribute("user", user);
    }

    @Test
    void testGetUserFromSession() {
        when(session.getAttribute("user")).thenReturn(user);
        Users sessionUser = usersService.getUserFromSession();
        assertEquals(user, sessionUser);
        verify(session, times(1)).getAttribute("user");
    }

    @Test
    void testInvalidateSession() {
        usersService.invalidateSession();
        verify(session, times(1)).invalidate();
    }
}

