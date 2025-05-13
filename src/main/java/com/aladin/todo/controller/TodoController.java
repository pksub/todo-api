package com.aladin.todo.controller;

import com.aladin.todo.entity.Member;
import com.aladin.todo.entity.Todo;
import com.aladin.todo.dto.TodoDto;
import com.aladin.todo.security.CustomUserDetails;
import com.aladin.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Todo 생성
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDto dto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(todoService.createTodo(dto, member));
    }

    // 로그인한 사용자의 Todo 전체 조회
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(todoService.getTodosByMember(member));
    }

    // 특정 Todo 조회 (로그인한 사용자 기준)
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return todoService.getTodoByIdAndMember(id, member)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Todo 수정 (로그인한 사용자 기준)
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id,
                                           @RequestBody TodoDto dto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(todoService.updateTodo(id, dto, member));
    }

    // Todo 삭제 (로그인한 사용자 기준)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        todoService.deleteTodo(id, member);
        return ResponseEntity.noContent().build();
    }

    // 키워드로 Todo 검색 (로그인한 사용자 기준)
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String keyword,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(todoService.searchTodosByKeywordAndMember(keyword, member));
    }
}
