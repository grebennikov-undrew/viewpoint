package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.users.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto save(@Valid UserDto newUser) {
        User user = userMapper.toUser(newUser);

        // Если в dto не задан пароль -> не менять его
        if (newUser.getPassword().equals("")) {
            User foundUser = userRepository.findById(newUser.getId()).get();
            user.setPassword(foundUser.getPassword());
        }
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Удалить пользователя по id
     * @param userId id пользователя
     * */
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
