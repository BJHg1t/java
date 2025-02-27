package com.example.spring.essentials.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CookieController {

    @GetMapping("/set-cookie")
    public String setCookie() {
        return "set-cookie";
    }

    @GetMapping("/get-cookie")
    public String getCookieExc(
            HttpServletRequest request,
            Model model
    ) {
        Cookie[] cookies = request.getCookies();
        String username = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("message",  "쿠키에서 사용자 정보를 읽었습니다.");
        } else {
            model.addAttribute("message","쿠키가 존재하지 않습니다.");
        }
        return "result-cookie";
    }

    @PostMapping("/set-cookie")
    public String setCookieExc(
            @RequestParam String username,
            HttpServletResponse response,
            Model model
    ) {

        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 유효기간 : 1주일
        cookie.setPath("/"); // 저장 경로
        cookie.setHttpOnly(true); 
        // 보안적으로 열쇠를 클라이언트에게 줬을 때, 침입자가 javascript로 제어 불가능하게 만듬

        response.addCookie(cookie);

        model.addAttribute("message", "쿠키가 설정되었습니다.");
        model.addAttribute("username", username);
        return "result-cookie";
    }

    @GetMapping("/delete-cookie")
    public String deleteCookieExc(
            HttpServletResponse response,
            Model model
    ) {

        // 쿠키에는 invalidate가 없기 때문에 설정한 username에 관한 쿠키를 빈 값으로 덮어씌워 버림
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        model.addAttribute("message", "쿠키가 삭제되었습니다.");
        
        return "result-cookie";
    }

}

