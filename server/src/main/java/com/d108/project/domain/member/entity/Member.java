package com.d108.project.domain.member.entity;

import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
@ToString(exclude = {"favoriteBusinessAreas", "favoriteFranchises"})
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // 상속받은 엔티티의 기본 키를 지정
public class Member extends LoginCredential{

    private String nickname;

    private String email;

    // 관심 상권
    @OneToMany(mappedBy = "member")
    private List<FavoriteBusinessArea> favoriteBusinessAreas = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FavoriteFranchise> favoriteFranchises = new ArrayList<>();

    public static Member createMember(MemberRegisterDto memberRegisterDto, String passwordEncode) {
        Member member = new Member();
        member.setNickname(memberRegisterDto.getNickname());
        member.setUsername(memberRegisterDto.getUsername());
        member.setEmail(memberRegisterDto.getEmail());
        member.setPassword(passwordEncode);

        return member;
    }
}
