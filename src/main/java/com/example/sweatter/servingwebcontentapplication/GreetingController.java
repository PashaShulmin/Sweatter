package com.example.sweatter.servingwebcontentapplication;

import com.example.sweatter.servingwebcontentapplication.domain.Message;
import com.example.sweatter.servingwebcontentapplication.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String submit(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message();

        message.setText(text);
        message.setTag(tag);

        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if (filter == null || filter.isEmpty()) {
            messages = messageRepository.findAll();
        } else {
            messages = messageRepository.findByTag(filter);
        }

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam String delete, Map<String, Object> model) {
        Integer id = new Integer(delete);
        messageRepository.deleteById(id);

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }
}
