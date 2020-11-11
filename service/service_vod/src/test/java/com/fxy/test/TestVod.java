package com.fxy.test;

import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI4FzMqhJYHh9jfzX7H3CN";
        String accessKeySecret = "VvRnPtp9uCg3hxeQeJcwEoUJ6O6Yjz";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
        String fileName = "C:\\Users\\14257\\Videos\\Captures\\20200708153241.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }
    //获取凭证
    public  static  void getPlayAuth() throws ClientException {
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI4FzMqhJYHh9jfzX7H3CN", "VvRnPtp9uCg3hxeQeJcwEoUJ6O6Yjz");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("01446c11dab8465ab4a969aa1a0f2cc4");
        response = defaultAcsClient.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }
    //获取地址
    public  static  void getPlayUrl() throws ClientException {
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI4FzMqhJYHh9jfzX7H3CN", "VvRnPtp9uCg3hxeQeJcwEoUJ6O6Yjz");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("01446c11dab8465ab4a969aa1a0f2cc4");
        GetPlayInfoResponse response=defaultAcsClient.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
