package letscode.sarafan.controller;

import letscode.sarafan.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/message")
public class MessageController {
    private int counter = 4;

    public List<Map<String, String>> messages = new ArrayList<>();
    {
        messages.add(new HashMap<>() {{
            put("id", "1");
            put("text", "First message");
        }});
        messages.add(new HashMap<>() {{
            put("id", "2");
            put("text", "2 message");
        }});
        messages.add(new HashMap<>() {{
            put("id", "3");
            put("text", "3 message");
        }});
    }

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getById(@PathVariable String id) {

        return getMessageById(id);
    }

    @PostMapping()
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@RequestBody Map<String, String> message,
                                      @PathVariable String id) {
        Map<String, String> messageFromDb = getMessageById(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> messageFromDb = getMessageById(id);
        messages.remove(getMessageById(id));
    }

    private Map<String, String> getMessageById(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }


}
