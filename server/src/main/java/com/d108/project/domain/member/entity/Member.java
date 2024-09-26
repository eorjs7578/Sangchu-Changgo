package com.d108.project.domain.member.entity;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
@ToString(exclude = {"favoriteAreas", "favoriteFranchises"})
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // 상속받은 엔티티의 기본 키를 지정
public class Member extends LoginCredential {

    @NotNull
    private String nickname;

    private String email;

    // 관심 상권
    @OneToMany(mappedBy = "member")
    private List<FavoriteArea> favoriteAreas = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FavoriteFranchise> favoriteFranchises = new ArrayList<>();

    // 일반 유저 회원가입
    public static Member createMember(MemberRegisterDto memberRegisterDto, String passwordEncode) {
        Member member = new Member();
        member.setUsername(memberRegisterDto.getUsername());
        member.setPassword(passwordEncode);
        member.setNickname(memberRegisterDto.getNickname());
        member.setEmail(memberRegisterDto.getEmail());
        member.setSocialUser(false);
        return member;
    }

    // 소셜 유저 회원가입
    public static Member createMember(String username, String password, String nickname, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setEmail(email);
        member.setSocialUser(true);
        return member;
    }
}

