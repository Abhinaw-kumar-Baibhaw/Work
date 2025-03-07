package com.example.SocialMediaUser;

import com.example.SocialMediaUser.controller.UsersController;
import com.example.SocialMediaUser.dto.UserDTO;
import com.example.SocialMediaUser.model.Users;
import com.example.SocialMediaUser.service.UsersService;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;

    private Users user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new Users(1L, "Test User", "test@example.com", "ROLE_USER", "password123");
        userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController()).build();
    }

    @Test
    void testCreateUser_Valid() throws Exception {
        when(usersService.createUser(any(Users.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(userDTO));
        mockMvc.perform(post("/users/create")
                        .contentType("application/json")
                        .content("{\"name\":\"Test User\", \"email\":\"test@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
        verify(usersService, times(1)).createUser(any(Users.class));
    }

    @Test
    void testCreateUser_Invalid() throws Exception {
        mockMvc.perform(post("/users/create")
                        .contentType("application/json")
                        .content("{\"name\":\"\", \"email\":\"\", \"password\":\"\"}"))
                .andExpect(status().isBadRequest());  // Expect HTTP 400 status for invalid input
        verify(usersService, times(0)).createUser(any(Users.class));
    }

    @Test
//    @WithMockUser(roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        List<UserDTO> userDTOList = Arrays.asList(userDTO);
        when(usersService.getAllUsers()).thenReturn(ResponseEntity.ok(userDTOList));

        mockMvc.perform(get("/users/getAll"))
                .andExpect(status().isOk())  // Expect HTTP 200 status
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    void testGetAllUsers_NoContent() throws Exception {
        when(usersService.getAllUsers()).thenReturn(ResponseEntity.noContent().build());
        mockMvc.perform(get("/users/getAll"))
                .andExpect(status().isNoContent());  // Expect HTTP 204 status
        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    void testGetById_Valid() throws Exception {
        when(usersService.findById(user.getId())).thenReturn(ResponseEntity.ok(userDTO));

        mockMvc.perform(get("/users/getById/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(usersService, times(1)).findById(user.getId());
    }

    @Test
    void testGetById_UserNotFound() throws Exception {
        when(usersService.findById(user.getId())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        mockMvc.perform(get("/users/getById/{id}", user.getId()))
                .andExpect(status().isNotFound());

        verify(usersService, times(1)).findById(user.getId());
    }

    @Test
    void testSearchByEmail() throws Exception {
        when(usersService.searchByEmail(user.getEmail())).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users/search")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk())  // Expect HTTP 200 status
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        verify(usersService, times(1)).searchByEmail(user.getEmail());
    }

    @Test
    void testSearchByEmail_NotFound() throws Exception {
        when(usersService.searchByEmail(user.getEmail())).thenReturn(Arrays.asList());

        mockMvc.perform(get("/users/search")
                        .param("email", user.getEmail()))
                .andExpect(status().isNotFound());

        verify(usersService, times(1)).searchByEmail(user.getEmail());
    }

    @Test
    void testSearchByName() throws Exception {
        when(usersService.searchByName(user.getName())).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users/searchByName")
                        .param("searchByName", user.getName()))
                .andExpect(status().isOk())  // Expect HTTP 200 status
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        verify(usersService, times(1)).searchByName(user.getName());
    }

    @Test
    void testSearchByRole() throws Exception {
        when(usersService.searchByRole(eq(user.getRole()))).thenReturn(Arrays.asList(user));
        mockMvc.perform(get("/users/searchByRole")
                        .param("role", user.getRole()))
                .andExpect(status().isOk())  // Expect HTTP 200 status
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
        verify(usersService, times(1)).searchByRole(user.getRole());
    }
}