package com.fxy.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.fxy.commonutils.Rsponse;
import com.fxy.servicebase.exceptionHandler.FxyException;
import com.fxy.vod.service.VodService;
import com.fxy.vod.utils.ConstantVodUtils;
import com.fxy.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    VodService vodService;
    @PostMapping("uploadVideo")
    public Rsponse uploadVideo(MultipartFile file){
        String videoId = vodService.upload(file);
        return Rsponse.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeVideo/{id}")
    public Rsponse removeVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Rsponse.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new FxyException(20001,"删除视频失败");
        }
    }

    @DeleteMapping("delete-batch")
    public Rsponse deleteBatch(@RequestParam("videoList")List<String> videoList){
        vodService.removeListVideo(videoList);

        return Rsponse.ok();
    }
    //获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Rsponse getPlayAuth(@PathVariable String id){
        try{
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Rsponse.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new FxyException(20001,"获取验证失败");
        }
    }

}
