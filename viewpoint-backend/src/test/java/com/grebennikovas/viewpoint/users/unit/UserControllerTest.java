package com.grebennikovas.viewpoint.users.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grebennikovas.viewpoint.users.UserController;
import com.grebennikovas.viewpoint.users.UserService;
import com.grebennikovas.viewpoint.users.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private static List<UserDto> mockUsers;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Подготовка данных для тестирования
        UserDto user1 = new UserDto();
        user1.setId(1001L);

        UserDto user2 = new UserDto();
        user2.setId(1002L);

        mockUsers = Arrays.asList(user1, user2);
    }

    @Test
    public void findAllUsersTest() throws Exception {
        // Мокирование сервиса
        when(userService.findAll()).thenReturn(mockUsers);

        // Проверка
        mockMvc.perform(get("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(mockUsers.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(mockUsers.get(1).getId()));
    }

    @Test
    public void saveValidUserTest() throws Exception {
        // Подготовка валидного пользователя
        UserDto validUser3 = new UserDto();
        validUser3.setUsername("validUser3");
        validUser3.setFirstname("validUser3 fname");
        validUser3.setLastname("validUser3 lname");
        validUser3.setIsActive(true);
        validUser3.setEmail("validUser3@test.test");

        // Мокирование сервиса
        when(userService.save(validUser3)).thenReturn(validUser3);

        // Проверка
        mockMvc.perform(post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validUser3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").value(validUser3));
    }

    @Test
    public void saveInvalidUserTest() throws Exception {
        // Подготовка не валидного пользователя
        UserDto invalidUser3 = new UserDto();
        invalidUser3.setFirstname("invalidUser3 fname");
        invalidUser3.setLastname("invalidUser3 lname");
        invalidUser3.setIsActive(true);
        invalidUser3.setEmail("invalidUser3@test.test");

        // Мок сервиса отсутствует - не должна пройти валидация

        // Проверка
        mockMvc.perform(post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser3)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteUserTest() throws Exception {
        // Мок удаления пользователя по Id
        doNothing().when(userService).deleteById(1L);

        // Проверка
        mockMvc.perform(delete("/api/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

