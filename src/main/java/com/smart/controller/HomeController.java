package com.smart.controller;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import com.smart.dao.UserRepository;

@Controller
public class HomeController {

    
     @GetMapping("/home")
    public String home(Model m) {
        m.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }
     @GetMapping("/about")
    public String abput(Model m) {
        m.addAttribute("title", "About-Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(){

        return "form";
    }
}
