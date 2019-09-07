package com.bestbeat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author bestbeat
 */
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoController {

    @RequestMapping("/mp4")
    public void video(HttpServletResponse response){
        response.setContentType("video/mpeg4");
        try {
//            FileInputStream fis = new FileInputStream("static/video/test.mp4");
//            BufferedInputStream bis = new BufferedInputStream(fis);
            log.info("11111");
            InputStream is = this.getClass().getResourceAsStream("/static/video/test.mp4");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            char[] b = new char[1024];
            int i = 0;
            OutputStream os = response.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            while ((i=br.read(b))>0){
                bw.write(b,0,i);
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
