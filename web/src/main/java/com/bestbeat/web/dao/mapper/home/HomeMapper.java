package com.bestbeat.web.dao.mapper.home;

import com.bestbeat.web.model.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 张渠钦
 * 2019/06/22
 */
public interface HomeMapper {

    @Select("select room_id roomId,room_name roomName from t_room ")
    List<Room> getRooms();

    @Insert("insert into t_room (room_id,room_name,create_user_id,create_time)" +
            "values(#{roomId},#{roomName},#{creater},current_timestamp) ")
    int newRoom(Room room);

}
