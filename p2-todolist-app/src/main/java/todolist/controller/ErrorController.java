package todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/errorAcceso")
    public String errorAcceso() {
        return "errorAcceso"; // nombre de la plantilla Thymeleaf
    }
}
