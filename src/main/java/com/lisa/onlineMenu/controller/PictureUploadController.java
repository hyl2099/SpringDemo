package com.lisa.onlineMenu.controller;

import com.lisa.onlineMenu.businessController.PictureUploadBusinessController;
import com.lisa.onlineMenu.documents.menu.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/pictures")
public class PictureUploadController {
    private PictureUploadBusinessController pictureUploadBusinessController;

    @Autowired
    public PictureUploadController(PictureUploadBusinessController pictureUploadBusinessController) {
        this.pictureUploadBusinessController = pictureUploadBusinessController;
    }

    /**
     * 新建页面添加数据
     * 使用MultipartFile接口接收前台传的file（文件），其他的参数用实体类接收就可以了
     * 前台传到controller中的附件要以MultipartFile类型
     */
    @PostMapping("/addfile")
    @ResponseBody
    public Picture addFile(@RequestParam("file") MultipartFile file, Picture picture) throws IOException {
        if(file.isEmpty()){
            System.out.println("file为空");
        }
        //使用时间给上传的文件命名，这种方式没有用uuid命名好，因为同一时间有可能会上传多个文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        String originaFilename = file.getOriginalFilename();
        //获取文件的后缀名
        String newFileName = res+originaFilename.substring(originaFilename.lastIndexOf("."));
        // 跟目录设置是在SpringServerApplication 中的MultipartConfigElement中设置的。
        String rootPath = "/pictures/";
        File newFile = new File(rootPath+newFileName);
        System.out.println(rootPath+newFileName);
        //定义向数据库中存取的文件路径
        String src=rootPath+newFileName;
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }else{
            System.out.println(newFile.getParentFile());
        }
        if(!newFile.exists()){
            file.transferTo(newFile);
        }
        return pictureUploadBusinessController.add(src,picture);
    }
}