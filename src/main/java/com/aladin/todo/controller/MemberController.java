package com.aladin.todo.controller;

import com.aladin.todo.dto.JwtToken;
import com.aladin.todo.dto.MemberDto;
import com.aladin.todo.entity.Member;
import com.aladin.todo.security.CustomUserDetails;
import com.aladin.todo.service.MemberService;
import com.aladin.todo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String index() {
        return "/login";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> signIn(@RequestBody MemberDto memberDto) {
        String username = memberDto.getUsername();
        String password = memberDto.getPassword();
        log.info("request username = {}, password = {}", username, password);

        JwtToken jwtToken = memberService.signIn(username, password);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signUp(@RequestBody MemberDto memberDto) {
        MemberDto savedMemberDto = memberService.signUp(memberDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @PostMapping("/test")
    public ResponseEntity<String>  test() {
        log.info("request username = {}", SecurityUtil.getCurrentUsername());

        return  ResponseEntity.ok(SecurityUtil.getCurrentUsername()) ;
    }

    // GET /users/me -> 로그인된 사용자의 정보 가져오기
    @GetMapping("/me")
    public ResponseEntity<Member> getCurrentUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("request username = {}", SecurityUtil.getCurrentUsername());
        // AuthenticationPrincipal을 통해 현재 로그인된 사용자 정보를 받아옴
        String username = SecurityUtil.getCurrentUsername();
        Member user = memberService.getUserDetail(username);
        return ResponseEntity.ok(user);
    }

    // PUT /users/me -> 로그인된 사용자의 정보 수정
    @PutMapping("/me")
    public ResponseEntity<Member> updateCurrentUser(@AuthenticationPrincipal Member user, @RequestBody MemberDto updatedUser) {
        // 로그인된 사용자의 정보를 수정
        String username = SecurityUtil.getCurrentUsername();
        Member updated = memberService.updateUser(username, updatedUser);
        return ResponseEntity.ok(updated);
    }

    // DELETE /users/me -> 로그인된 사용자 삭제
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser(@AuthenticationPrincipal Member user) {
        String username = SecurityUtil.getCurrentUsername();

        memberService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}