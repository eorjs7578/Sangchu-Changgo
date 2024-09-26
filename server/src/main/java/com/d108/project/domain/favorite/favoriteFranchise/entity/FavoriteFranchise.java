package com.d108.project.domain.favorite.favoriteFranchise.entity;

import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite_franchises")
public class FavoriteFranchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 관심상권 PK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gu_code")
    private Gu guCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dong_code")
    private Dong dongCode;

    @Column(name = "size")
    private Integer size;

    @Column(name = "floor")
    private Boolean floor;


    public static FavoriteFranchise toFavoriteFranchise(Member member, Franchise franchise) {
        FavoriteFranchise favoriteFranchise = new FavoriteFranchise();
        favoriteFranchise.setMember(member);
        favoriteFranchise.setFranchise(franchise);
        return favoriteFranchise;
    }

}
