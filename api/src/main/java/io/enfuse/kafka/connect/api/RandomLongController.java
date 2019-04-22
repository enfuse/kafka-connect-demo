package io.enfuse.kafka.connect.api;

import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomLongController {
    private static Random random = new Random();

    @GetMapping("/random/long")
    java.lang.Long randomLong() {
        return random.nextLong();
    }
}
