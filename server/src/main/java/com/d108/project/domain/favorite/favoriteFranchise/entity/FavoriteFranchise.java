package com.d108.project.domain.favorite.favoriteFranchise.entity;

import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
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
    private Gu gu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dong_code")
    private Dong dong;

    @Column(name = "size")
    private Long size;

    @Column(name = "floor")
    private Boolean floor;

    @Builder
    public FavoriteFranchise(Member member, Franchise franchise, LocalDateTime createdAt,
                             LocalDateTime updatedAt, Gu gu, Dong dong, Long size, Boolean floor) {
        this.member = member;
        this.franchise = franchise;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gu = gu;
        this.dong = dong;
        this.size = size;
        this.floor = floor;
    }

    public static FavoriteFranchise toFavoriteFranchise(Member member, FavoriteFranchiseRequestDto favoriteFranchiseRequestDto,
                                                        Franchise franchise, Dong dong, Gu gu) {

        return FavoriteFranchise.builder()
                .member(member)
                .franchise(franchise)
                .dong(dong)
                .gu(gu)
                .size(favoriteFranchiseRequestDto.storeSize())
                .floor(favoriteFranchiseRequestDto.floor())
                .build();
    }

}
