package com.mirim.board.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {
    // 아직 진짜 저장소는 없음
    // 컨트롤러에서 쓰던 가짜 규칙을 옮긴다. ex) id > 10이면 없다 등
    private final List<Map<String, Object>> posts = new ArrayList<>();
    private Long nextId = 1L;

    public Map<String, Object> save(Map<String, Object> post) {
        post.put("id", nextId++);
        posts.add(post);
        return post;
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    public List<Map<String, Object>> findAll() {
        //아직 진짜 데이터는 없다.
        return posts;
    }

    public boolean deleteById(Long id) {
        Map<String, Object> post = findById(id);
        if(post == null) {
            return false;
        }
        posts.remove(post);
        return true;
    }

    public Map<String, Object> findById(Long id) {
        //ㄱㅔ시글 전체 : posts
        for(Map<String, Object> post : posts) {
            if(post.get("id").equals(id)) {
                return post;
            }
        }
        return null;
    }

    public long count() {
        return posts.size();
    }

    public List<Map<String, Object>> findByKeyword(String keyword) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 1. 전체 게시글을 순회하면서 keyword 포함하는 지 확인
        for (Map<String, Object> post : posts) {
            String title = (String) post.get("title");
            // 2. 포함하면 result에 게시글 정보 추가
            if(title != null && title.contains(keyword)) {
                result.add(post);
            }
        }
        return result;
    }
}
