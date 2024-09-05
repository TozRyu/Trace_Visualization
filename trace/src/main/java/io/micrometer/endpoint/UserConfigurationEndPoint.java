package io.micrometer.endpoint;

import io.micrometer.service.MeterUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/user")
public class UserConfigurationEndPoint {

    @Autowired
    MeterUserService userService;

    @GetMapping("/{userId}")
    String userName(@PathVariable("userId") String userId) {
        log.info("Got a request");
        return userService.userName(userId);
    }

}
