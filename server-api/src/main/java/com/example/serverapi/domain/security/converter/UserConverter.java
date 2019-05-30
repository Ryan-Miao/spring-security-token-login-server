package com.example.serverapi.domain.security.converter;

import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.vo.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * user对象转换
 * @author Ryan Miao
 * @date 2019/5/30 14:00
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 转换user为UserInfo
     * @param user 数据库entity
     * @return userInfo  token存储信息
     */
    UserInfo toUserInfo(User user);


}
