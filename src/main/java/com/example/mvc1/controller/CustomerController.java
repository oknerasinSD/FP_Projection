package com.example.mvc1.controller;

import com.example.mvc1.domain.Test;
import com.example.mvc1.domain.User;
import com.example.mvc1.repos.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@Controller
public class CustomerController {
    @Autowired
    private TestRepo customerRepo;
    /** Значение upload.path из properties */
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main";
    }

    @GetMapping("/clients/{user}")
    public String open(@RequestParam(required = false, defaultValue = "") String filter,
                       @AuthenticationPrincipal User user,
                       Model model) {
        Iterable<Test> customers = user.getCustomers();
        if (filter != null && !filter.isEmpty()) {
            customers = customerRepo.findByName(filter);
        }
        model.addAttribute("customers", customers);
        model.addAttribute("filter", filter);
        return "clientslist";
    }

    @PostMapping("/clients/{user}")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String phoneNumber,
            Model model
    ) throws InterruptedException {
        Test customer = new Test(name, address, phoneNumber, user);
        customerRepo.save(customer);
        Set<Test> customers = user.getCustomers();
        customers.add(customer);
        model.addAttribute("customers", customers);
        return "redirect:/clients/{user}";
    }
}
