package top.simpleito.jwtspringsecuritydemo.res.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.simpleito.jwtspringsecuritydemo.res.AuthUserUtil;

@RestController
@RequestMapping("/user")
public class SimpleResourceController {

    @GetMapping("info")
    public String info(){
        return "current User:" + AuthUserUtil.currentUserName();
    }
}
