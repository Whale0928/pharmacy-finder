package app.module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalTime.now;

@Slf4j
@RestController
@RequestMapping("/api/oauth")
public class AuthController {
    @GetMapping("/google")
    public String google() {
        log.info("google");
        return "google";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        log.info("login success,{}",now());
        return "login success";
    }
}
