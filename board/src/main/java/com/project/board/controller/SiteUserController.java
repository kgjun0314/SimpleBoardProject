package com.project.board.controller;

import com.project.board.entity.SiteUserForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/siteuser")
public class SiteUserController {

    private final RestTemplate restTemplate;

    @GetMapping("/signup")
    public String signup(SiteUserForm siteUserForm, Model model) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String singup(@Valid SiteUserForm siteUserForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if(!siteUserForm.getPassword1().equals(siteUserForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        String url = "http://localhost:8080/api/siteuser/signup";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", siteUserForm.getUsername());
        requestBody.put("password", siteUserForm.getPassword1());
        requestBody.put("email", siteUserForm.getEmail());

        // 유저 생성 API 호출
        try {
            restTemplate.postForObject(url, requestBody, String.class);
        } catch(Exception e) {
            if(e.getMessage().contains("DataIntegrityViolationException"))
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
