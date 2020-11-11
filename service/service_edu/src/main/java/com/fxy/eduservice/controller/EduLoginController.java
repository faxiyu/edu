package com.fxy.eduservice.controller;


import com.fxy.commonutils.Rsponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
//@CrossOrigin  //解决跨域
public class EduLoginController {

    //login
    @PostMapping("login")
    public Rsponse login(){

        return Rsponse.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public  Rsponse info(){
        return Rsponse.ok().data("roles","[damin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
