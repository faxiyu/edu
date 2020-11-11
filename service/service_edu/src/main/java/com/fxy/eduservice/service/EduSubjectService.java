package com.fxy.eduservice.service;

import com.fxy.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-10-30
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getSubject();
}
