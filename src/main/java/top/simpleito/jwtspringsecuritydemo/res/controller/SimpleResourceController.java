package top.simpleito.jwtspringsecuritydemo.res.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SimpleResourceController {

    @GetMapping("info")
    public String info(){
        return "username:" + AuthUserUtil.currentAuthenticatedUser().getUsername() + "\n" +
                "id:" + AuthUserUtil.currentAuthenticatedUser().getId();
    }
}
