package com.d108.project.domain.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum FranchiseType {
    KOREAN_RESTAURANT("한식 음식점"),
    CHINESE_RESTAURANT("중식 음식점"),
    JAPANESE_RESTAURANT("일식 음식점"),
    WESTERN_RESTAURANT("양식 음식점"),
    BAKERY("제과점"),
    FAST_FOOD("패스트푸드점"),
    CHICKEN_SPECIALTY("치킨 전문점"),
    SNACK_SPECIALTY("분식 전문점"),
    PUB("호프/간이주점"),
    CAFE("커피/음료"),

    GENERAL_ACADEMY("일반 교습학원"),
    LANGUAGE_ACADEMY("외국어 학원"),
    ART_ACADEMY("예술 학원"),
    COMPUTER_ACADEMY("컴퓨터 학원"),
    SPORTS_ACADEMY("스포츠 강습"),

    GENERAL_CLINIC("일반 의원"),
    DENTAL_CLINIC("치과 의원"),
    ORIENTAL_MEDICINE_CLINIC("한의원"),
    VETERINARY_CLINIC("동물 병원"),

    LAW_FIRM("변호사 사무소"),
    PATENT_OFFICE("변리사 사무소"),
    LEGAL_OFFICE("법무사 사무소"),
    OTHER_LEGAL_SERVICES("기타 법무 서비스"),

    ACCOUNTANT_OFFICE("회계사 사무소"),
    TAX_OFFICE("세무사 사무소"),

    BILLIARD_HALL("당구장"),
    GOLF_RANGE("골프 연습장"),
    BOWLING_ALLEY("볼링장"),
    PC_CAFE("PC방"),
    ARCADE("전자 게임장"),
    OTHER_AMUSEMENT("기타 오락장"),
    LOTTERY_SHOP("복권방"),

    COMMUNICATION_DEVICE_REPAIR("통신기기 수리"),
    SPORTS_CLUB("스포츠 클럽"),
    AUTO_REPAIR("자동차 수리"),
    AUTO_DETAILING("자동차 미용"),
    MOTORCYCLE_REPAIR("모터사이클 수리"),

    HAIR_SALON("미용실"),
    NAIL_SHOP("네일숍"),
    SKIN_CARE("피부 관리실"),
    LAUNDRY("세탁소"),
    APPLIANCE_REPAIR("가전제품 수리"),

    REAL_ESTATE_AGENCY("부동산 중개업"),
    INN("여관"),
    GUESTHOUSE("게스트하우스"),
    GOSIWON("고시원"),
    KARAOKE("노래방"),
    STUDY_ROOM("독서실"),
    DVD_ROOM("DVD방"),
    RECORDING_STUDIO("녹음실"),
    PHOTO_STUDIO("사진관"),
    TRANSLATION_SERVICE("통/번역 서비스"),

    CLEANING_SERVICE("건축물 청소"),
    TRAVEL_AGENCY("여행사"),
    VIDEO_BOOK_RENTAL("비디오/서적 임대"),
    CLOTHING_RENTAL("의류 임대"),
    HOUSEHOLD_ITEM_RENTAL("가정용품 임대"),

    SUPERMARKET("슈퍼마켓"),
    CONVENIENCE_STORE("편의점"),
    COMPUTER_SALES("컴퓨터 및 주변장치 판매"),
    MOBILE_PHONE("핸드폰"),

    LIQUOR_WHOLESALE("주류 도매"),
    RICE_SALES("미곡 판매"),
    MEAT_SALES("육류 판매"),
    SEAFOOD_SALES("수산물 판매"),
    FRUIT_VEGETABLES("청과 상"),
    SIDE_DISH_STORE("반찬 가게"),

    GENERAL_CLOTHING("일반 의류"),
    HANBOK_STORE("한복점"),
    CHILDREN_CLOTHING("유아 의류"),
    SHOES("신발"),
    BAGS("가방"),
    OPTICAL_STORE("안경"),
    WATCH_JEWELRY("시계 및 귀금속"),

    PHARMACY("의약품"),
    MEDICAL_EQUIPMENT("의료기기"),
    BOOKSTORE("서적"),
    STATIONERY("문구"),
    COSMETICS("화장품"),
    BEAUTY_SUPPLIES("미용 재료"),

    SPORTS_EQUIPMENT("운동/경기용품"),
    BICYCLE_SALES("자전거 및 기타 운송장비"),
    TOY_STORE("완구"),
    TEXTILE_PRODUCTS("섬유 제품"),
    FLORIST("화초"),
    PET_STORE("애완동물"),

    USED_FURNITURE("중고 가구"),
    FURNITURE("가구"),
    APPLIANCES("가전제품"),
    HARDWARE_STORE("철물점"),
    MUSICAL_INSTRUMENTS("악기"),
    INTERIOR_DESIGN("인테리어"),
    LIGHTING_SUPPLIES("조명용품"),

    USED_CAR_SALES("중고차 판매"),
    AUTO_PARTS("자동차 부품"),
    MOTORCYCLE_AND_PARTS("모터사이클 및 부품"),
    RECYCLING_SHOP("재생용품 판매점"),
    ART_STORE("예술품"),
    GAS_STATION("주유소"),

    ECOMMERCE("전자 상거래업");

    private final String description;

    FranchiseType(String description) {
        this.description = description;
    }

    public static List<String> getAllDescriptions() {
        return Arrays.stream(FranchiseType.values())
                .map(FranchiseType::getDescription)
                .toList();
    }
    
    public static FranchiseType fromDescription(String description) {
        return Arrays.stream(FranchiseType.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
