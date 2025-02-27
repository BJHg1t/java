package com.example.spring.essentials.controller;

import com.example.spring.essentials.SessionResponseDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class SessionTest {
    private Map<String, SessionResponseDTO> account = new HashMap<>();

    @PostMapping("/sign")
    public String create(@RequestParam String id, String pw, String username) {
        log.info("created");
        SessionResponseDTO createAccount = SessionResponseDTO.builder()
                .id(id)
                .pw(pw)
                .username(username)
                .build();

        account.put(id, createAccount);

        return "redirect:loginT";
    }

    @GetMapping("/loginT")
    public String loginT(
            HttpSession session,
            Model model
    ) {
        String username = (String) session.getAttribute("username");

        if (username != null) {
            model.addAttribute("username", username);
        }

        return "loginT";
    }

    @PostMapping("/loginT")
    public String loginTExc(
            @RequestParam String id,
            @RequestParam String pw,
            HttpSession session
    ) {
        SessionResponseDTO getAccount = account.get(id);
        if (getAccount != null && getAccount.getPw().equals(pw)) {
        session.setAttribute("id", id);
        session.setAttribute("pw", pw);
        session.setAttribute("username", getAccount.getUsername());
        }

        return "redirect:loginT";
    }

    @GetMapping("/api/logoutT")
    public String logoutTExc(HttpSession session) {
        session.invalidate();
        return "loginT";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<SessionResponseDTO> getList() {
        log.info("list");
        List<SessionResponseDTO> list = new ArrayList<>();

        for (SessionResponseDTO session : account.values()) {
            list.add(session);
        }

        return list;
    }

}


