package com.mirim.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 근데 클래스는 인스턴스화 해야하잖아?? 근데 우린 안했어. 그럼 누가?? - 스프링부트가!

@RestController
@RequestMapping("/posts")
public class PostController {
    private final Notifier notifier;
    // private SmsNotifier notifier = new SmsNotifier(); // 강결합, 하지만 객체지향은 낮은 결합도를 지향
    // @RequestParam : 주소에서 ?로 쿼리파라미터를 받아옴!
    // - required : 필수 여부 옵션, 기본값은 true

    public PostController(Notifier notifier) { // 왜 오류나나? postcontroller가 bin 파일이 필요한데, bin이 없으요
        this.notifier = notifier;
    }

    @GetMapping
    public String getPosts(@RequestParam(required = false) String keyword) {
        if(keyword != null) {
            return keyword + "로 검색한 결과입니다.";
        }
        return "게시글의 목록입니다.";
    }

    // @PathVariable : 주소에서 값을 하나 가져올 수 있음
    // @ResponseEntity<> :
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        if(id > 10) {
            // 게시글 번호가 10번 보다 크면 게시글이 없는 것임 - 404
            // 상태 not found로 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        } else if(id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 게시글입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(id+"번 게시글입니다.");
    }

    @GetMapping("/count")
        public String getPostCount() {
        return "게시글 개수 : 0개";
    }

    // @RequesBody : 요청 데이터 받아오기
    // createPost()처럼 무언가를 생성하는 코드의 경우, 201(created)로 응답받는 것이 좀 더 직관적
    @PostMapping// 브라우저에서는 get이외의 요청이 불가ㅜ
    public ResponseEntity<?> createPost(@RequestBody Map<String, Object> request) {
        // Map은 논리적 오류를 잡기 힘들어서 실무에서는 클래스로 만들어 사용 -> 그렇기에 프론트와 협업하기 위해 API문서화가 필요한 것
        String title = (String) request.get("title");
        String content = (String) request.get("content");

        // db에다가 저장한다고 치고~
        Map<String, Object> response = new HashMap<>();
        response.put("title",title);
        response.put("content", content);
        response.put("message", "게시글이 등록되었습니다.");

        //이메일 발송 (로그로 대체)
        notifier.send(title + " 게시글이 등록되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> putPosts(@PathVariable long id, @RequestBody Map<String, Object> request) {
        if(id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 번호는 1 이상이여야 합니다.");
        } else if(id > 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시물입니다.");
        }

        String title = (String) request.get("title");
        String content = (String) request.get("content");

        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("title", title);
        response.put("content", content);
        response.put("message", "게시글이 수정되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosts(@PathVariable long id) {
        if(id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 번호는 1이상이어야 합니다.");
        } else if(id > 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }

        //db에서 삭제한다고 치고

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
