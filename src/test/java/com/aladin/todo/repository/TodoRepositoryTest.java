package com.aladin.todo.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.aladin.todo.entity.Todo;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testFindById() {
        Optional<Todo> todo = todoRepository.findById(1L);
        assertTrue(todo.isPresent());
    }
}
