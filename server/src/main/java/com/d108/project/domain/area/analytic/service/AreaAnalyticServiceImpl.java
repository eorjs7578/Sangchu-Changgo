package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.repository.*;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.interfaces.api.analytics.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AreaAnalyticServiceImpl implements AreaAnalyticService {

    private final AreaRepository areaRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final ServiceRepository serviceRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final PopulationHistoryRepository populationHistoryRepository;
    private final PopulationRepository populationRepository;

    @Override
    public Long getFootTrafficByDaily(Long areaId) {
        return populationRepository.getPopulationByAreaId(areaId);
    }

    private static int getMaxIndex(List<Long> footTrafficByHourByArea) {
        if (footTrafficByHourByArea == null || footTrafficByHourByArea.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return IntStream.range(0, footTrafficByHourByArea.size())
                .reduce((i, j) -> footTrafficByHourByArea.get(i) > footTrafficByHourByArea.get(j) ? i : j)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static <T extends Number> List<T> getList(List<T[]> objects) {
        List<T> list = new ArrayList<>();
        for (T[] row : objects) {
            list.addAll(Arrays.asList(row));
        }
        return list;
    }


    @Override
    public FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long areaId) {
        // 쿼리에서 반환된 Object[] 배열을 처리
        List<Long[]> rawFootTrafficData = populationRepository.getFootTrafficByDayOfWeekByAreaId(areaId);
        List<Long> footTrafficByDayOfWeekByArea = getList(rawFootTrafficData);
        List<String> dayOfWeekList = List.of("월", "화", "수", "목", "금", "토", "일");

        int maxIndex = getMaxIndex(footTrafficByDayOfWeekByArea);

        return new FootTrafficByDayOfWeekDto(footTrafficByDayOfWeekByArea, dayOfWeekList, dayOfWeekList.get(maxIndex));
    }


    @Override
    public FootTrafficByHourDto getFootTrafficByHour(Long areaId) {
        List<Long> footTrafficByHourByArea = getList(populationRepository.getFootTrafficByHourByAreaId(areaId));
        List<String> hourList = List.of("0시~06시", "06시~11시", "11시~14시", "14시~17시", "17시~21시", "21시~24시");

        System.out.println(footTrafficByHourByArea);
        int maxIndex = getMaxIndex(footTrafficByHourByArea);

        return new FootTrafficByHourDto(footTrafficByHourByArea, hourList, hourList.get(maxIndex));
    }

    @Override
    public FootTrafficByMonthDto getFootTrafficByQuarterly(Long areaId) {
        List<Long> footTrafficByQuarterlyByArea = getList(populationHistoryRepository.getPopulationHistoryByAreaId(areaId));
        List<String> quarterList = List.of("2023/1", "2023/2", "2023/3", "2023/4", "2024/1");

        String currentQuarter = getCurrentQuarter();
        String lastYear = String.valueOf(getLastYear());
        String last = String.format("%s/1", lastYear);
        String now = String.format("%s/1", lastYear+1);

        int nowIndex = quarterList.indexOf(now);
        int lastIndex = quarterList.indexOf(last);

        double percentageChange = calculatePercentageChange(footTrafficByQuarterlyByArea.get(lastIndex), footTrafficByQuarterlyByArea.get(nowIndex));

        String QOQ = "유지";
        if (percentageChange > 0) {
            QOQ = String.format("%.2f%% 상승", percentageChange);
        } else if (percentageChange < 0) {
            QOQ = String.format("%.2f%% 하락", Math.abs(percentageChange));
        }

        return new FootTrafficByMonthDto(footTrafficByQuarterlyByArea, quarterList, QOQ);
    }

    @Override
    public FootTrafficByAgeDto getFootTrafficByAge(Long areaId) {
        List<Long> footTrafficByAgeByArea = getList(populationRepository.getFootTrafficByAgeByAreaId(areaId));
        List<String> ageList = List.of("10대", "20대", "30대", "40대", "50대", "60대");

        int maxIndex = getMaxIndex(footTrafficByAgeByArea);
        return new FootTrafficByAgeDto(footTrafficByAgeByArea, ageList, ageList.get(maxIndex));
    }

    @Override
    public FootTrafficByGenderDto getFootTrafficByGender(Long areaId) {
        List<Long> footTrafficByGenderByArea = getList(populationRepository.getFootTrafficByGenderByAreaId(areaId));
        List<String> genderList = List.of("남", "여");

        int maxIndex = getMaxIndex(footTrafficByGenderByArea);
        return new FootTrafficByGenderDto(footTrafficByGenderByArea, genderList, genderList.get(maxIndex));
    }

    @Override
    public Long getSaleByMonth(Long areaId, String service) {
        return saleRepository.getSaleByMonthByAreaId(areaId, service);
    }

    @Override
    public SalesByDayOfWeekDto getSaleByDayOfWeek(Long areaId, String service) {
        List<Long> saleByDayOfWeekByArea = getList(saleRepository.getSaleByDayOfWeek(areaId, service));
        List<String> dayOfWeekList = List.of("월", "화", "수", "목", "금", "토", "일");

        int maxIndex = getMaxIndex(saleByDayOfWeekByArea);

        return new SalesByDayOfWeekDto(saleByDayOfWeekByArea, dayOfWeekList, dayOfWeekList.get(maxIndex));
    }

    @Override
    public SalesByHourDto getSaleByHour(Long areaId, String service) {
        List<Long> saleByHourByArea = getList(saleRepository.getSaleByHour(areaId, service));
        List<String> hourList = List.of("0시~06시", "06시~11시", "11시~14시", "14시~17시", "17시~21시", "21시~24시");

        int maxIndex = getMaxIndex(saleByHourByArea);
        return new SalesByHourDto(saleByHourByArea, hourList, hourList.get(maxIndex));
    }

    @Override
    public SalesByQuarterlyDto getSaleByQuarterly(Long areaId, String service) {
        List<Long> saleByQuarterlyByArea = getList(salesHistoryRepository.getSalesHistoryByDongId(areaId, service));
        List<String> quarterList = List.of("2023/1", "2023/2", "2023/3", "2023/4", "2024/1");

        String currentQuarter = getCurrentQuarter();
        String lastYear = String.valueOf(getLastYear());
        String last = String.format("%s/1", lastYear);
        String now = String.format("%s/1", lastYear+1);

        int nowIndex = quarterList.indexOf(now);
        int lastIndex = quarterList.indexOf(last);

        double percentageChange = calculatePercentageChange(saleByQuarterlyByArea.get(lastIndex), saleByQuarterlyByArea.get(nowIndex));

        String QOQ = "유지";
        if (percentageChange > 0) {
            QOQ = String.format("%.2f%% 상승", percentageChange);
        } else if (percentageChange < 0) {
            QOQ = String.format("%.2f%% 하락", Math.abs(percentageChange));
        }

        return new SalesByQuarterlyDto(saleByQuarterlyByArea, quarterList, QOQ);

    }

    @Override
    public SalesByAgeDto getSaleByAge(Long areaId, String service) {
        List<Long> saleByAgeByArea = getList(saleRepository.getSaleByAge(areaId, service));
        List<String> ageList = List.of("10대", "20대", "30대", "40대", "50대", "60대");

        int manIndex = getMaxIndex(saleByAgeByArea);

        return new SalesByAgeDto(saleByAgeByArea, ageList, ageList.get(manIndex));
    }

    @Override
    public SalesByGenderDto getSaleByGender(Long areaId, String service) {
        List<Long> saleByGenderByArea = getList(saleRepository.getSaleByGender(areaId, service));
        List<String> genderList = List.of("남", "여");

        int manIndex = getMaxIndex(saleByGenderByArea);
        return new SalesByGenderDto(saleByGenderByArea, genderList, genderList.get(manIndex));
    }

    @Override
    public WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long areaId, String service) {
        List<Long> weekendAndWeekdaySaleByArea = getList(saleRepository.getSaleByWeekendAndWeekDay(areaId, service));
        List<String> dayOfWeek = List.of("평일","주말");

        return new WeekendAndWeekdaySalesDto(weekendAndWeekdaySaleByArea, dayOfWeek);
    }

    @Override
    public Integer getNumberOfSimilarStores(Long areaId, String service) {
        List<Integer> numberOfSimilarStores = storeRepository.getNumberOfSimilarStores(areaId, service);

        return numberOfSimilarStores.get(0);
    }

    @Override
    public IndustryInfoDto getIndustryInfo(Long areaId, String service) {
        List<Integer> industryInfo = getList(storeRepository.getIndustryInfo(areaId, service));
        List<String> info = List.of("점포수","개업률","폐업률");

        return new IndustryInfoDto(industryInfo, info);
    }
    public static String getCurrentQuarter() {
        // 서울 타임존 기준으로 현재 시간을 구함
        ZonedDateTime seoulTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        int month = seoulTime.getMonthValue();

        if (month >= 1 && month <= 3) {
            return "1";
        } else if (month >= 4 && month <= 6) {
            return "2";
        } else if (month >= 7 && month <= 9) {
            return "3";
        } else {
            return "4";
        }
    }

    // 작년의 연도를 구하는 메서드
    private static int getLastYear() {
        // 서울 타임존 기준으로 현재 시간을 구함
        ZonedDateTime seoulTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        // 작년의 연도를 반환
        return seoulTime.getYear() - 1;
    }

    public static double calculatePercentageChange(Long lastSale, Long nowSale) {
        if (lastSale == 0) {
            throw new IllegalArgumentException("Last year's sale cannot be zero.");
        }
        // 퍼센트 변화 계산 공식: ((현재 매출 - 전년도 매출) / 전년도 매출) * 100
        return ((double)(nowSale - lastSale) / lastSale) * 100;
    }
}
