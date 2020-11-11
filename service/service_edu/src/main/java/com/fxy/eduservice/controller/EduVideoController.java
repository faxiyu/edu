package com.fxy.eduservice.controller;


import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.client.VodClient;
import com.fxy.eduservice.entity.EduChapter;
import com.fxy.eduservice.entity.EduVideo;
import com.fxy.eduservice.entity.chapter.VideoVo;
import com.fxy.eduservice.service.EduVideoService;
import com.fxy.servicebase.exceptionHandler.FxyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService VideoService ;
    @Autowired
    private  VodClient vodClient;
    @PostMapping("addVideo")
    public Rsponse addVideo(@RequestBody  EduVideo eduVideo){
        VideoService.save(eduVideo);
        return Rsponse.ok();
    }
    @DeleteMapping("{id}")
    public Rsponse delete(@PathVariable  String id){
        EduVideo video = VideoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            Rsponse rsponse = vodClient.removeVideo(videoSourceId);
            if (rsponse.getCode()==20001){
                throw new FxyException(200001,"删除视频失败");
            }
        }
        VideoService.removeById(id);
        return Rsponse.ok();
    }
    @PostMapping("updateVideo")
    public Rsponse updateChapter(@RequestBody EduVideo video){
        VideoService.updateById(video);
        return Rsponse.ok();
    }
    @GetMapping("getVideoInfo/{videoId}")
    public Rsponse getChapterInfo(@PathVariable String videoId){
        EduVideo video = VideoService.getById(videoId);
        return Rsponse.ok().data("video",video);
    }
}

