package com.example.serverapi.domain.security.service.impl;

import com.example.serverapi.domain.security.entity.Role;
import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.mapper.RoleMapper;
import com.example.serverapi.domain.security.mapper.UserMapper;
import com.example.serverapi.domain.security.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Ryan Miao
 * @date 2019/5/29 20:25
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
        RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("ryan"));
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("test"));
    }

    @Override
    public User getById() {
        return null;
    }

    @Override
    public User findSecurityUserByUsername(String username)
        throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("username cannot be empty");
        }

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username)
            .andEqualTo("enabled", 0);
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //获取user permission
        List<Role> roleList = roleMapper.listRoleAndPermissionsByUserId(user.getId());
        user.setRoleList(roleList);
//        Set<String> roles = new HashSet<>();
//        Set<String> permissions = new HashSet<>();
//        if (roleList != null && roleList.size() > 0) {
//            for (Role role : roleList) {
//                roles.add(role.getName());
//                List<Permission> permissionList = role.getPermissionList();
//                if (permissionList != null && permissionList.size() > 0) {
//                    for (Permission permission : permissionList) {
//                        permissions.add(permission.getName());
//                    }
//                }
//            }
//        }

//        UserInfo userInfo = UserConverter.INSTANCE.toUserInfo(user);
//        userInfo.setRoles(roles).setPermissions(permissions);

        return user;
    }
}
