package com.bestbeat.web.service.home;

import com.bestbeat.web.dao.mapper.home.HomeMapper;
import com.bestbeat.web.model.Room;
import com.bestbeat.web.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张渠钦
 * 2019/06/22
 */
@Service
public class HomeService {

    @Autowired
    private HomeMapper homeMapper;

    public List<Room> getRooms(){
        return homeMapper.getRooms();
    }

    public String newRoom(Room room){
        String roomId = IdUtils.createRoomId();
        room.setRoomId(roomId);
        homeMapper.newRoom(room);
        return  roomId;
    }

}
