package com.aladin.todo;

import com.aladin.todo.controller.TodoController;
import com.aladin.todo.repository.TodoRepository;
import com.aladin.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class TodoApplicationTests {

	@Autowired
	private MockMvc mockMvc;  // MockMvc를 사용해 HTTP 요청을 모의할 수 있음

	@Autowired
	private TodoRepository todoRepository;  // TodoRepository 주입

	@Autowired
	private TodoService todoService;  // TodoService 주입

	@Autowired
	private TodoController todoController;  // TodoController 주입

	@BeforeEach
	public void setUp() {
		// 테스트 전에 필요한 초기화 작업을 여기서 할 수 있음
		todoRepository.deleteAll();  // 이전에 남아있는 데이터 삭제
	}

	@Test
	public void contextLoads() throws Exception {
		// 애플리케이션 컨텍스트가 잘 로드되는지 테스트
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.status().isOk()); // 기본적인 GET 요청에 대해 200 OK 응답을 확인
	}



}