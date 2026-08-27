package com.mirim.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    @GetMapping
    public String getPosts() {
        return "게시글의 목록입니다.";
    }

    @GetMapping("/count")
        public String getPostCount() {
        return "게시글 개수 : 0개";
    }

    @PostMapping// 브라우저에서는 get이외의 요청이 불가ㅠ
    public String createPost() {
        return "게시글이 등록되었습니다.";
    }

}
