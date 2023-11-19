package com.grebennikovas.viewpoint.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    /**
     * Получить список пользователей
     * @return список пользователей в формте коротких DTO
     * */
    public List<UserDto> findAll() {
        List<User> foundUsers = userRepository.findAll();
        List<UserDto> foundUsersDto = foundUsers.stream()
                .map(user -> userMapper.toDto(user)).toList();
        return foundUsersDto;
    }

    /**
     * Сохранить/изменить пользователя
     * @param newUser пользователь
     * @return сохраненный пользователь
     * */
    public UserDto save(UserDto newUser) {
        User user = userMapper.toUser(newUser);
        UserDto savedUserDto = userMapper.toDto(userRepository.save(user));
        return savedUserDto;
    }

    /**
     * Удалить пользователя по id
     * @param userId id пользователя
     * */
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
