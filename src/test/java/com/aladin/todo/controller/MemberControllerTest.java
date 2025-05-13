package com.aladin.todo.controller;

import com.aladin.todo.dto.MemberDto;
import com.aladin.todo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class MemberControllerTest {

//    @Autowired
//    DatabaseCleanUp databaseCleanUp;
    @Autowired
    MemberService memberService;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomServerPort;

    private MemberDto memberDto;

    @BeforeEach
    void beforeEach() {
        // Member 회원가입
        memberDto = memberDto.builder()
                .username("member")
                .password("12345678")
                .phone("010-1234-5678")
                .build();
    }

    @AfterEach
    void afterEach() {
//        databaseCleanUp.truncateAllEntity();
    }

    @Test
    public void signUpTest() {

        // API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/users/signup";
        ResponseEntity<MemberDto> responseEntity = testRestTemplate.postForEntity(url, memberDto, MemberDto.class);

        // 응답 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto savedMemberDto = responseEntity.getBody();
        assertThat(savedMemberDto.getUsername()).isEqualTo(memberDto.getUsername());
    }

}