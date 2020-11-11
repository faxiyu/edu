package com.fxy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-10-26
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getHotTeacher();

    Map<String, Object> pageTeacherFront(Page<EduTeacher> pageTeacher);
}
