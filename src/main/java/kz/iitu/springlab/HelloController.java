package kz.iitu.springlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    @GetMapping("/factorial")
    public ResponseEntity<?> factorial(@RequestParam(defaultValue = "0") Integer n) {
        if (n == null || n < 0 || n > 20) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("n must be between 0 and 20"));
        }

        long r = 1;
        for (int i = 2; i <= n; i++) {
            r *= i;
        }

        return ResponseEntity.ok(new FactorialResponse(n, r));
    }

    public record FactorialResponse(int n, long factorial) {}

    public record ErrorResponse(String error) {}

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }

    public record Info(String owner, String javaVersion, int cpuCores) { }
}
