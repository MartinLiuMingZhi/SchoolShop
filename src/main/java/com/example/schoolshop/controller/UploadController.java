package com.example.schoolshop.controller;

import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.model.until.UploadResponse;
import com.example.schoolshop.utils.AliOSSUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin
@Slf4j  //log的注解
@RestController
public class UploadController {

    @Resource
    private AliOSSUtils aliOSSUtils;

    /**
     * 上传图片
     * @param image
     * @return
     * @throws IOException
     */

    @PostMapping("/upload")
    public BaseResponse<UploadResponse> upload(MultipartFile image) throws IOException {

//        //获取原始文件名
//        String originalFilename = image.getOriginalFilename();
//        // 构造唯一的文件名--uuid
//            int index = originalFilename.lastIndexOf(".");
//            String extname = originalFilename.substring(index);
//            String newFileName = UUID.randomUUID().toString()+extname;
//
//        //将文件存储在服务器的磁盘目录中 E:\image
//        image.transferTo(new File("E:\\image\\"+newFileName));

        //调用阿里云oss工具类继续文件上传
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setUrl(aliOSSUtils.upload(image));
        return ResultUtils.success(uploadResponse);
    }
}
