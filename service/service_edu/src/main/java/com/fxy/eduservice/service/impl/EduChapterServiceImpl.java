package com.fxy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.eduservice.entity.EduChapter;
import com.fxy.eduservice.entity.EduVideo;
import com.fxy.eduservice.entity.chapter.ChapterVo;
import com.fxy.eduservice.entity.chapter.VideoVo;
import com.fxy.eduservice.mapper.EduChapterMapper;
import com.fxy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.eduservice.service.EduVideoService;
import com.fxy.servicebase.exceptionHandler.FxyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private  EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        QueryWrapper chapterWrapper = new QueryWrapper();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);

        QueryWrapper videoWrapper = new QueryWrapper();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = eduVideoService.list(videoWrapper);

        List<ChapterVo> all = new ArrayList<>();
        for(int i=0;i<chapterList.size();i++){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapterList.get(i),chapterVo);
            List<VideoVo> vv = new ArrayList<>();
            for (int j=0;j<videoList.size();j++){
                if(videoList.get(j).getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(videoList.get(j),videoVo);
                    vv.add(videoVo);
                }
            }
            chapterVo.setChildren(vv);
            all.add(chapterVo);
        }
        return all;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count>0){
            throw new FxyException(20001,"有小节无法进行删除");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper wrapper  = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
