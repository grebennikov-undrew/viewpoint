package com.grebennikovas.viewpoint.security.rbac;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleShortDto;
import com.grebennikovas.viewpoint.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;

    /**
     * Получить список всех ролей без привилегий
     * @return список ролей
     * */
    public List<RoleShortDto> findAll() {
        return roleRepository.findAll().stream()
                .map(r -> roleMapper.mapToShortDto(r))
                .toList();
    }

    /**
     * Получить детализацию роли - список привилегий и назначенных пользователей
     * @param roleId - ID роли
     * @return список ролей
     * */
    public RoleDto findById(Long roleId) {
        Role role = roleRepository.findById(roleId).get();
        return roleMapper.mapToRoleDto(role);
    }

    /**
     * Сохранить/изменить роль
     * @param roleDto настройки роли (пользователи и привилегии)
     * @return сохраненная роль
     * */
    public RoleDto save(RoleDto roleDto) throws SQLException {
        Role role = roleMapper.mapToRole(roleDto);
        return roleMapper.mapToRoleDto(roleRepository.save(role));
    }

    /**
     * Удалить роль по id
     * @param roleId id роли
     * @return сообщение об ошибке
     * */
    public void deleteById(Long roleId) throws SQLException {
        roleRepository.deleteById(roleId);
    }

}
