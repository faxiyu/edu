package com.fxy.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.EduChapter;
import com.fxy.eduservice.entity.chapter.ChapterVo;
import com.fxy.eduservice.entity.subject.OneSubject;
import com.fxy.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/eduservice/chapter")

public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public Rsponse getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list =  chapterService.getChapterVideoById(courseId);
        return Rsponse.ok().data("chapterVideo",list);
    }
    @PostMapping("addChapter")
    public Rsponse addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return Rsponse.ok();
    }
    @GetMapping("getChapterInfo/{chapterId}")
    public Rsponse getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return Rsponse.ok().data("chapter",chapter);
    }
    @PostMapping("updateChapter")
    public Rsponse updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Rsponse.ok();
    }

    @DeleteMapping("{chapterId}")
    public Rsponse deleteChapter(@PathVariable String chapterId){
        boolean b =chapterService.deleteChapter(chapterId);
        if (b){
            return Rsponse.ok();
        }else {
            return Rsponse.error();
        }
    }


}

