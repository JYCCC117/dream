package com.jyc.dream.controller;

import com.jyc.dream.domain.DownloadVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName TestController
 * @Description TODO
 * @author jyc
 * @date 2018/10/25 14:54
 * @version 1.0
 **/
@Controller
public class TestController {

    /**
     * TODO 去测试页面
     * @return
     */
    @RequestMapping("/test")
    public String testPage(){
        return "test";
    }

    @RequestMapping({"/index","/","/index.html"})
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/downloadAll",method = RequestMethod.POST)
    public  String downloadAll(@RequestBody DownloadVo downloadVo, HttpServletResponse response) {

        File file = new File(downloadVo.getUrl());
        //判断文件父目录是否存在
        if(file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + downloadVo.getName());
            try {
                response.sendError(200);
            } catch (IOException e) {
                e.printStackTrace();
            }


            byte[] buffer = new byte[1024];
            //文件输入流
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            //输出流
            OutputStream os = null;
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
