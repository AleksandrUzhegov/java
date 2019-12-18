package com.ertelecom.lesson_14;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    // GET http://localhost:8189/app

    // главная страница
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showList() {
        return "redirect:/tasks/show";
    }

}
