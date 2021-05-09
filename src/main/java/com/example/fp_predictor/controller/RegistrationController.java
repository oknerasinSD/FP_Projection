package com.example.fp_predictor.controller;

import com.example.fp_predictor.domain.User;
import com.example.fp_predictor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    /**
     * Бин, внедряемый фреймворком Spring.
     */
    @Autowired
    private UserService userService;

    /**
     * Возвращаем Представление registration при Get запросе по маппингу /registration.
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Добавляем пользователя в систему при Post запросе на странице регистрации.
     * @param user - новый экземпляр класса User.
     * @param bindingResult - объект, из которого извлекаются ошибки валидаци.
     * @param model - модель.
     */
    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPasswordConfirmation())) {
            model.addAttribute("passwordConfirmationError", "Passwords are different.");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        userService.addUser(user);
        return "redirect:/";
    }
}
