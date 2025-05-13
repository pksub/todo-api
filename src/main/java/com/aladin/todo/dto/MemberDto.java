package com.aladin.todo.dto;

import com.aladin.todo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String username;
    private String password;
    private String phone;

    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .phone(member.getPhone()).build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .phone(phone).build();
    }

    public Member toEntity(String encodedPassword, String role) {

        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .phone(phone)
                .role(role)
                .build();
    }
}