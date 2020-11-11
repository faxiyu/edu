package com.fxy.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.eduservice.entity.EduSubject;
import com.fxy.eduservice.entity.excel.SubjectData;
import com.fxy.eduservice.service.EduSubjectService;
import com.fxy.servicebase.exceptionHandler.FxyException;


public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null) {
            throw new FxyException(20001, "文件数据为空");
        }

        EduSubject eduSubject1 = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (eduSubject1==null){
            eduSubject1 = new EduSubject();
            eduSubject1.setParentId("0");
            eduSubject1.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduSubject1);
        }
        String pid = eduSubject1.getId();
        EduSubject eduSubject2 = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (eduSubject2==null){
            eduSubject2 = new EduSubject();
            eduSubject2.setParentId(pid);
            eduSubject2.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject2);
        }
    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject = subjectService.getOne(wrapper);

        return oneSubject;
    }
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);

        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
