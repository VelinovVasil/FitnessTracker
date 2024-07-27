package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.config.SecurityConfiguration;
import com.fitnesstracker.fitnesstracker.models.dto.UserDTO;
import com.fitnesstracker.fitnesstracker.services.UserService;
import com.fitnesstracker.fitnesstracker.config.JwtService;
import com.fitnesstracker.fitnesstracker.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllUsersWithAdminRole() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setUsername("john.doe");
        user1.setEmail("john.doe@example.com");
        user1.setRole("USER");

        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setUsername("jane.doe");
        user2.setEmail("jane.doe@example.com");
        user2.setRole("USER");

        List<UserDTO> userList = Arrays.asList(user1, user2);


        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("john.doe"))
                .andExpect(jsonPath("$[1].username").value("jane.doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllUsersWithoutAdminRole() throws Exception {
        mockMvc.perform(get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
