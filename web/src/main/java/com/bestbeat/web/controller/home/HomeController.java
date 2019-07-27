package com.bestbeat.web.controller.home;

import com.bestbeat.web.model.Room;
import com.bestbeat.web.service.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 张渠钦
 * 2019/06/22
 * 主页-控制层
 */
@RestController
@RequestMapping("/backend/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/room",method = RequestMethod.GET)
    public List<Room> getRooms(){
        return homeService.getRooms();
    }

    @RequestMapping(value = "/room",method = RequestMethod.POST)
    public String newRoom(@RequestBody Room room){
        return homeService.newRoom(room);
    }

}
