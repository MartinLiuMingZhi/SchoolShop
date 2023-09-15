package com.example.schoolshop.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.example.schoolshop.common.BaseResponse;
import com.example.schoolshop.common.ResultUtils;
import com.example.schoolshop.model.until.UploadResponse;
import com.example.schoolshop.model.until.VerificationCode;
import com.example.schoolshop.utils.AliOSSUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
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
     * @param file
     * @return
     * @throws IOException
     */

    @PostMapping("/upload")
    public BaseResponse<UploadResponse> upload(MultipartFile file) throws IOException {
        if (file == null){
            throw new RuntimeException("File is null");
        }

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
        uploadResponse.setUrl(aliOSSUtils.upload(file));
        return ResultUtils.success(uploadResponse);
    }

    /**
     * 生成验证码图片返回给前端图片地址
     * @param request
     * @param resp
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    @ResponseBody
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);

        VerificationCode.output(image,resp.getOutputStream());
    }

}
