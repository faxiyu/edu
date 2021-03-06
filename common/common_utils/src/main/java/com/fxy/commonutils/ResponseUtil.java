package com.fxy.commonutils;


import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public static void out(HttpServletResponse response, Rsponse r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getOutputStream(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
