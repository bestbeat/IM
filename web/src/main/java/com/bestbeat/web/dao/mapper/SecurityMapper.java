package com.bestbeat.web.dao.mapper;

import com.bestbeat.web.model.Permission;
import com.bestbeat.web.model.Role;
import com.bestbeat.web.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author 张渠钦
 * 2019/6/19
 *
 */
public interface SecurityMapper {

    @Select("select u.user_id userId,\n" +
            "       u.user_name userName,\n" +
            "       u.login_name loginName,\n" +
            "       u.mobile_phone mobilePhone,\n" +
            "       u.user_password password\n" +
            "  from t_user u\n" +
            " where u.user_name= #{name}\n" +
            "   and u.user_status= 'on'")
    User getUserByName(@Param("name") String name);

    @Select("select ur.role_code roleCode  from t_user_role ur where ur.user_id = #{userId} and ur.state = 'on'")
    Set<Role> getRolesById(@Param("userId")String userId);

    @Select("select rp.permission_code permissionCode from t_role_permission rp where rp.role_code = #{roleCode} and rp.state ='on'")
    Set<Permission> getPermissionsById(@Param("roleCode") String roleCode);
}
