package com.d108.project.domain.franchise.entity;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "franchises")
@NoArgsConstructor
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @Column(name = "franchise_fee")
    private Long franchiseFee;

    @Column(name = "education_fee")
    private Long educationFee;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "other_fee")
    private Long otherFee;

    @Column(name = "interior")
    private Long interior;

    @Size(max = 511)
    @Column(name = "link", length = 511)
    private String link;

    @OneToMany(mappedBy = "franchise")
    private List<FavoriteFranchise> FavoriteFranchises = new ArrayList<>();

    @Builder
    public Franchise(Long id, String name, String type, Long franchiseFee, Long educationFee,
                     Long deposit, Long otherFee, Long interior, String link) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.franchiseFee = franchiseFee;
        this.educationFee = educationFee;
        this.deposit = deposit;
        this.otherFee = otherFee;
        this.interior = interior;
        this.link = link;
    }
}
