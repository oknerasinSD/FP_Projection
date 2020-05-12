package com.example.mvc1.controller;

import com.example.mvc1.domain.Customer;
import com.example.mvc1.domain.User;
import com.example.mvc1.repository.CustomerRepository;
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
    private CustomerRepository customerRepo;
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
        Iterable<Customer> customers = user.getCustomers();
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
        Customer customer = new Customer(name, address, phoneNumber, user);
        customerRepo.save(customer);
        Set<Customer> customers = user.getCustomers();
        customers.add(customer);
        model.addAttribute("customers", customers);
        return "redirect:/clients/{user}";
    }
}
