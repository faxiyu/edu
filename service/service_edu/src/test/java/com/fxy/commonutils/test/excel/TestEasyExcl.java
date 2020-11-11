package com.fxy.commonutils.test.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcl {
    public static void main(String[] args) {
        List<DemoData> list = new ArrayList<>();
        for (int i=0;i<10;i++ ){
            DemoData d = new DemoData();
            d.setSno(i+1);
            d.setName("fxy"+(i+1));
            list.add(d);
        }


        //写操作
//        String filename="C:\\Users\\14257\\Desktop\\easy.xlsx";
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(list);


        //读
        String filename="C:\\Users\\14257\\Desktop\\easy.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
