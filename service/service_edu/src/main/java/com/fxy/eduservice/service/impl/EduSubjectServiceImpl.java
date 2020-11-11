package com.fxy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.eduservice.entity.EduSubject;
import com.fxy.eduservice.entity.excel.SubjectData;
import com.fxy.eduservice.entity.subject.OneSubject;
import com.fxy.eduservice.entity.subject.TwoSubject;
import com.fxy.eduservice.listener.SubjectExcelListener;
import com.fxy.eduservice.mapper.EduSubjectMapper;
import com.fxy.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-10-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubject() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id",0);
        List<EduSubject> onelist = baseMapper.selectList(wrapper);

        QueryWrapper twowrapper = new QueryWrapper();
        twowrapper.ne("parent_id",0);
        List<EduSubject> twolist = baseMapper.selectList(twowrapper);

        List<OneSubject> all = new ArrayList<>();
        for (int i=0;i<onelist.size();i++){
            OneSubject os = new OneSubject();
//            os.setId(onelist.get(i).getId());
//            os.setTitle(onelist.get(i).getTitle());

            BeanUtils.copyProperties(onelist.get(i),os);
            List<TwoSubject> listTwo = new ArrayList<>();
            for (int j=0;j<twolist.size();j++){
                TwoSubject ts = new TwoSubject();
                if(twolist.get(j).getParentId().equals(os.getId())){
                    BeanUtils.copyProperties(twolist.get(j),ts);
                    listTwo.add(ts);
                }
            }
            os.setChildren(listTwo);
            all.add(os);
        }




        return all;
    }
}
