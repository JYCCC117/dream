package com.jyc.dream.controller;

import com.jyc.dream.domain.DownloadVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName TestComtroller
 * @Description TODO
 * @Author jyc
 * @Date 2018/10/25 14:54
 * @Version 1.0
 **/
@Controller
public class TestController {

    @RequestMapping("/test")
    public String testPage(){
        return "test";
    }


    @RequestMapping("/downloadAll")
    public  String downloadAll(@RequestBody DownloadVo downloadVo, HttpServletResponse response, HttpServletRequest request) {

        File file = new File(downloadVo.getUrl());
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + downloadVo.getName());
            try {
                response.sendError(200);
            } catch (IOException e) {
                e.printStackTrace();
            }


            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
