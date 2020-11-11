package com.fxy.eduservice.client;


import com.fxy.commonutils.Rsponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name="service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //定义方法路径
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public Rsponse removeVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/delete-batch")
    public Rsponse deleteBatch(@RequestParam("videoList") List<String> videoList);

}
