package com.fxy.eduservice.client;

import com.fxy.commonutils.Rsponse;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Rsponse removeVideo(String id) {
        System.out.println("熔断");
        return Rsponse.error().message("删除视频出错");
    }

    @Override
    public Rsponse deleteBatch(List<String> videoList) {
        return Rsponse.error().message("删除课程视频出错");
    }
}
