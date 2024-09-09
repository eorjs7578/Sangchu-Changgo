package com.d108.project.domain.member.domain;

import com.d108.project.domain.favorite.favoriteBusinessArea.domain.FavoriteBusinessArea;
import com.d108.project.domain.favorite.favoriteFranchise.domain.FavoriteFranchise;
import com.d108.project.domain.logincredential.domain.LoginCredential;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // 상속받은 엔티티의 기본 키를 지정
public class Member extends LoginCredential{

    private String nickname;

    // 관심 상권
    @OneToMany(mappedBy = "member")
    private List<FavoriteBusinessArea> favoriteBusinessAreas = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FavoriteFranchise> favoriteFranchises = new ArrayList<>();

    public static Member createMember(MemberRegisterDto memberRegisterDto) {
        Member member = new Member();
        member.setNickname(memberRegisterDto.getNickname());

        return member;
    }
}
