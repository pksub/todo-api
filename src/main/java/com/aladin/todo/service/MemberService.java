package com.aladin.todo.service;

import com.aladin.todo.dto.JwtToken;
import com.aladin.todo.dto.MemberDto;
import com.aladin.todo.entity.Member;
import com.aladin.todo.exception.BusinessException;
import com.aladin.todo.repository.MemberRepository;
import com.aladin.todo.util.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class MemberService{


    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, AuthenticationManager authenticationManager,
                         JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(username, password)
//        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional
    public MemberDto signUp(MemberDto memberDto) {
        if (memberRepository.findByUsername(memberDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        String role = "USER";// USER 권한 부여
        return MemberDto.toDto(memberRepository.save(memberDto.toEntity(encodedPassword, role)));
    }

    // 사용자의 정보 업데이트
    @Transactional
    public Member updateUser(String username, MemberDto updatedUser) {
        Member user = memberRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getPassword() != null ) {
            user.setPassword(updatedUser.getPassword());
        }

        return memberRepository.saveAndFlush(user);
    }

    // 사용자의 삭제
    @Transactional
    public void deleteUser(String username) {
        Member user = memberRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
        memberRepository.delete(user);
    }

    public Member getUserDetail(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
    }
}