package ru.mephi.reqsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mephi.reqsystem.domain.administration.User;
import ru.mephi.reqsystem.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        // Переменная, которая хранит флаг совпадения паролей
        boolean isPasswordDifferent =
                user.getPassword() != null && !user.getPassword().equals(passwordConfirm);

        // Если пароли не совпали
        if (isPasswordDifferent){
            model.addAttribute("passwordError", "Passwords are different!");
        }

        // Если пусто
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cancelled!");
        }

        // Ещё проверки
        if (isConfirmEmpty || bindingResult.hasErrors() || isPasswordDifferent) {
            Map<String, String> errorsMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }

        // Всё проверили и теперь наконец пытаемся добавить пользователя.
        // Если добавить не удалось -> помещаем в модель сообщение об этомы
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exists!");
            return "registration";
        }

        model.addAttribute("isRegisterForm", true);

        return "redirect:/login";
    }

}
