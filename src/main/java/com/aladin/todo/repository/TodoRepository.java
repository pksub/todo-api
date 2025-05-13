package com.aladin.todo.repository;

import com.aladin.todo.dto.TodoDto;
import com.aladin.todo.entity.Member;
import com.aladin.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMember(Member member);
    Optional<Todo> findByIdAndMember(Long id, Member member);
    List<Todo> findByTitleContainingAndDescriptionContainingAndMember(String title, String description, Member member);

    List<Todo> findByTitleContainingOrDescriptionContainingAndMember(String keyword, String keyword1, Member member);
}
