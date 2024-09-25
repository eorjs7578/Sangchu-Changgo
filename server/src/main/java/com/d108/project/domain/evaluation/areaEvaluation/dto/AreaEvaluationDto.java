package com.d108.project.domain.evaluation.areaEvaluation.dto;

public class AreaEvaluationDto {
    private Long areaId;
    private Long memberId;
    private String ageGroup;
    private String footTraffic;
    private String atmosphere;
    private String nearbyPrices;

    // 생성자
    public AreaEvaluationDto(Long areaId, Long memberId, String ageGroup, String footTraffic, String atmosphere, String nearbyPrices) {
        this.areaId = areaId;
        this.memberId = memberId;
        this.ageGroup = ageGroup;
        this.footTraffic = footTraffic;
        this.atmosphere = atmosphere;
        this.nearbyPrices = nearbyPrices;
    }

    // Getter 및 Setter
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getFootTraffic() {
        return footTraffic;
    }

    public void setFootTraffic(String footTraffic) {
        this.footTraffic = footTraffic;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }

    public String getNearbyPrices() {
        return nearbyPrices;
    }

    public void setNearbyPrices(String nearbyPrices) {
        this.nearbyPrices = nearbyPrices;
    }
}
