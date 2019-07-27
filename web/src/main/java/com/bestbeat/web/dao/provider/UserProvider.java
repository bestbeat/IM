package com.bestbeat.web.dao.provider;

import com.bestbeat.web.model.Role;
import com.bestbeat.web.model.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @author zqq
 * 2019/6/19
 * sql提供类 对应 UserMapper
 */
public class UserProvider {

    public String getAddUserSql(Map<String,Object> params){
        User user = (User) params.get("user");
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user")
                .INTO_COLUMNS("user_id","user_name","login_name","mobile_phone","user_password")
                .INTO_VALUES("'"+user.getUserId()+"'","'"+user.getUserName()+"'","'"+user.getLoginName()+"'","'"+user.getMobilePhone()+"'","'"+user.getPassword()+"'");
        return sql.toString();

    }

    public String getAddUserRoleSql(Map<String,Object> params){
        User user = (User) params.get("user");
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user_role")
                .INTO_COLUMNS("user_id","role_code");

        StringBuffer returnSql = new StringBuffer(sql.toString());
        returnSql.append(" values ");
        for (Role role : user.getRoleSet()) {

            returnSql.append("(");
            returnSql.append("'"+user.getUserId()+"'");
            returnSql.append(",");
            returnSql.append("'"+role.getRoleCode()+"'");
            returnSql.append(")");
        }


        return returnSql.toString();
    }

}
