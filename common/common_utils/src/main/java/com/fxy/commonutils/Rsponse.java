package com.fxy.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Rsponse {
    private boolean success;
    private Integer  code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private Rsponse(){}
    public static  Rsponse ok(){
        Rsponse r = new Rsponse();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return  r;
    }
    public static  Rsponse error(){
        Rsponse r = new Rsponse();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return  r;
    }
    public Rsponse success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Rsponse message(String message){
        this.setMessage(message);
        return this;

    }
    public Rsponse code(Integer code){
        this.setCode(code);
        return this;
    }
    public Rsponse data(String key, Object value){
        this.data.put(key, value);
        return this;
    }


    public Rsponse data(Map<String, Object> map){
        this.setData(map);
        return this;

    }
}
