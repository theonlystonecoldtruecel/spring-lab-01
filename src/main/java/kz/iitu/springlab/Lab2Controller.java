package kz.iitu.springlab.web;

import kz.iitu.springlab.lifecycle.LifecycleDemo;
import kz.iitu.springlab.notify.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;

    public Lab2Controller(NotificationService notifications, LifecycleDemo lifecycle) {
        this.notifications = notifications;
        this.lifecycle = lifecycle;
    }

    private final LifecycleDemo lifecycle;   // add it to the controller constructor

    @GetMapping("/lifecycle")
    public List<String> lifecycle() {
        return lifecycle.events();
    }


    @GetMapping("/notify")
    public Map<String, Object> notify(@RequestParam(defaultValue = "Hello") String text) {
        return Map.of("primary",  notifications.viaPrimary(text),
                "console",  notifications.viaConsole(text),
                "all",      notifications.viaAll(text),
                "beanNames", notifications.names());
    }

    @GetMapping("/custom")
    public String reverse(@RequestParam(defaultValue = "test")String text) {
        return notifications.viaReversed(text);
    }
}
