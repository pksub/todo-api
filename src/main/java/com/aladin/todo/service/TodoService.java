package com.aladin.todo.service;

import com.aladin.todo.entity.Member;
import com.aladin.todo.entity.Todo;
import com.aladin.todo.dto.TodoDto;
import com.aladin.todo.exception.BusinessException;
import com.aladin.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public Todo createTodo(TodoDto dto, Member member) {
        Todo todo = Todo.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .member(member)
                .build();
        return todoRepository.save(todo);
    }

    @Transactional
    public List<Todo> getTodosByMember(Member member) {
        List<Todo> todos = todoRepository.findByMember(member);
        return  todoRepository.findByMember(member);
    }

    public Optional<Todo> getTodoByIdAndMember(Long id, Member member) {
        return todoRepository.findByIdAndMember(id, member);
    }

    @Transactional
    public Todo updateTodo(Long id, TodoDto dto, Member member) {
        Todo todo = todoRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new BusinessException("Todo not found or access denied"));

        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(dto.isCompleted());
        return todoRepository.save(todo);
    }

    @Transactional
    public void deleteTodo(Long id, Member member) {
        Todo todo = todoRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new BusinessException("Todo not found or access denied"));
        todoRepository.delete(todo);
    }

    public List<Todo> searchTodosByKeywordAndMember(String keyword, Member member) {
        return todoRepository.findByTitleContainingOrDescriptionContainingAndMember(keyword, keyword, member);
    }

}
