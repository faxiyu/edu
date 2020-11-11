package com.fxy.eduservice.service;

import com.fxy.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);

    boolean deleteChapter(String chapter);

    void removeByCourseId(String courseId);
}
