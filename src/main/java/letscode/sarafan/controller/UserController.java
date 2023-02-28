package letscode.sarafan.controller;

import letscode.sarafan.domain.Usr;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public Map<String, Object> user(@AuthenticationPrincipal Usr principal) {
        String name = Optional.of(principal)
                .map(Usr::getName)
                .orElseGet(()->"unknown");
        return Collections.singletonMap("name", name);
    }
}
