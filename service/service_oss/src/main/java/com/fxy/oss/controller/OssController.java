package com.fxy.oss.controller;

import com.fxy.commonutils.Rsponse;
import com.fxy.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")

public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Rsponse uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return Rsponse.ok().data("url",url);
    }
}
