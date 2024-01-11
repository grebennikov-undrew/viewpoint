package com.grebennikovas.viewpoint.users.unit;

import com.grebennikovas.viewpoint.users.*;
import com.grebennikovas.viewpoint.users.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static List<UserDto> mockUsersDto;
    private static List<User> mockUsers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // список юзеров DTO
        UserDto user1Dto = new UserDto();
        user1Dto.setId(1001L);
        user1Dto.setUsername("user1");
        user1Dto.setFirstname("user1 fname");
        user1Dto.setLastname("user1 lname");
        user1Dto.setIsActive(true);
        user1Dto.setEmail("user1@test.test");

        UserDto user2Dto = new UserDto();
        user2Dto.setId(1002L);
        user2Dto.setUsername("user2");
        user2Dto.setFirstname("user2 fname");
        user2Dto.setLastname("user2 lname");
        user2Dto.setIsActive(true);
        user2Dto.setEmail("user2@test.test");

        mockUsersDto = Arrays.asList(user1Dto, user2Dto);

        // список юзеров
        User user1 = new User();
        user1.setId(1001L);
        user1.setUsername("user1");
        user1.setFirstname("user1 fname");
        user1.setLastname("user1 lname");
        user1.setActive(true);
        user1.setEmail("user1@test.test");

        User user2 = new User();
        user2.setId(1002L);
        user2.setUsername("user2");
        user2.setFirstname("user2 fname");
        user2.setLastname("user2 lname");
        user2.setActive(true);
        user2.setEmail("user2@test.test");
    }

    @Test
    public void testSave() {
        // Создаем тестовые данные
        UserDto newUserDto = new UserDto(/* заполните поля DTO */);
        User newUser = new User(/* заполните поля пользователя */);

        // Настроим поведение мока PasswordEncoder
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // Создаем настоящий экземпляр UserMapper (сгенерированный MapStruct)
        UserMapper userMapper = new UserMapperImpl();

        UserDto result = userService.save(newUserDto);

        // Проверяем, что методы вызывались правильно и результат соответствует ожиданиям
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(newUser);
        assertEquals(newUserDto, result);
    }

    // Тесты для других методов UserService аналогичны
}
