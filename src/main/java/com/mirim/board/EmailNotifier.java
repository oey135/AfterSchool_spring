package com.mirim.board;


import org.springframework.web.bind.annotation.RestController;


public class EmailNotifier {
    public void send(String message) {
        System.out.println("[이메일 발송] " + message);
    }
}
