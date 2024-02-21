package app.module.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    //http://localhost:8088/api/test

    @GetMapping("/test")
    public String test() {;
        return "test success";
    }
}
