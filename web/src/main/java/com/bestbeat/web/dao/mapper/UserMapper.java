package com.bestbeat.web.dao.mapper;

import com.bestbeat.web.dao.provider.UserProvider;
import com.bestbeat.web.model.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 张渠钦
 * 2019/6/19
 */
public interface UserMapper {

    @InsertProvider(type= UserProvider.class,method = "getAddUserSql")
    int addUser(@Param("user") User user);

    @InsertProvider(type = UserProvider.class ,method = "getAddUserRoleSql")
    int addUserRole(@Param("user") User user);


}
