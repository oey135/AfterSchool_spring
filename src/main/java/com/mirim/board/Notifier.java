package com.mirim.board;

import org.springframework.core.annotation.Order;

public interface Notifier {

    void send(String message);
}
