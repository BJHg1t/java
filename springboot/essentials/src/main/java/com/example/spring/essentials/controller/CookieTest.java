package com.example.spring.essentials.controller;

import com.example.spring.essentials.SessionResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CookieTest {
    private Map<String, SessionResponseDTO> account = new HashMap<>();

    @PostMapping("/sign2")
    public String create(@RequestParam String id, @RequestParam String pw, @RequestParam String username) {
        SessionResponseDTO createAccount = SessionResponseDTO.builder()
                .id(id)
                .pw(pw)
                .username(username)
                .build();

        account.put(id, createAccount);

        return "redirect:setT";
    }

    @GetMapping("/setT")
    public String setCookie() {
        return "setT";
    }

    @PostMapping("/login2")
    public String login2Exc (
        @RequestParam String id,
        @RequestParam String pw,
        HttpServletResponse response,
        Model model
    ) {
        SessionResponseDTO getAccount = account.get(id);
        if (getAccount != null && getAccount.getPw().equals(pw)) {
            Cookie cookie = new Cookie("username", getAccount.getUsername());
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            model.addAttribute("username", getAccount.getUsername());
        }

        return "resultT";
    }

    @GetMapping("/api/logout2")
    public String deleteCookieExc(
            HttpServletResponse response,
            Model model
    ) {

        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "setT";
    }

    @GetMapping("/list2")
    @ResponseBody
    public List<SessionResponseDTO> getList() {
        List<SessionResponseDTO> list2 = new ArrayList<>();

        for (SessionResponseDTO cookie : account.values()) {
            list2.add(cookie);
        }

        return list2;
    }

}
