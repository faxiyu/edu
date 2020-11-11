package com.fxy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String upload(MultipartFile file);

    void removeListVideo(List videoList);
}
