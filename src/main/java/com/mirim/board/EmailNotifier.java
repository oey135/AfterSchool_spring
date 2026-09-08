package com.mirim.board;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component //bin으로 등록해줘!
public class EmailNotifier implements Notifier{
    @Override
    public void send(String message) {
        System.out.println("[이메일 발송] " + message);
    }
}
