package com.example.serverapi.domain.security.mapper;

import com.example.serverapi.domain.security.entity.Role;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Ryan Miao
 * @date 2019/5/29 18:08
 */
@Component
public interface RoleMapper extends Mapper<Role> {


    /**
     * 列出用户权限.
     * @param userId 用户id.
     * @return 该用户的所有权限。
     */
    List<Role> listRoleAndPermissionsByUserId(@Param("userId") Long userId);
}
