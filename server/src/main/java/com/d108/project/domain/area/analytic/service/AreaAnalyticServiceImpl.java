package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.dto.*;
import com.d108.project.domain.area.analytic.repository.*;
import com.d108.project.domain.area.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AreaAnalyticServiceImpl implements AreaAnalyticService {

    private final AreaRepository areaRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final PopulationHistoryRepository populationHistoryRepository;
    private final PopulationRepository populationRepository;

    @Override
    public Long getFootTrafficByDaily(Long areaId) {
        return populationRepository.getPopulationByAreaId(areaId);
    }

    private static int getMaxIndex(List<Long> footTrafficByHourByArea) {
        if (footTrafficByHourByArea == null || footTrafficByHourByArea.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }

        return IntStream.range(0, footTrafficByHourByArea.size())
                .reduce((i, j) -> {
                    Long valueI = (footTrafficByHourByArea.get(i));
                    Long valueJ = (footTrafficByHourByArea.get(j));

                    return valueI > valueJ ? i : j;
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    private Long convertToLong(Number number) {
        if (number instanceof Long) {
            return (Long) number;
        } else if (number instanceof Integer) {
            return number.longValue();  // Convert Integer to Long
        }
        // 이외에는 0으로 처리 (에러 처리 로직)
        return 0L;
    }

    @Override
    public FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long areaId) {
        // 쿼리에서 반환된 Object[] 배열을 처리
        List<Long[]> rawFootTrafficData = populationRepository.getFootTrafficByDayOfWeekByAreaId(areaId);
        List<String> dayOfWeekList = List.of("월", "화", "수", "목", "금", "토", "일");
        List<Long> footTrafficByDayOfWeekByArea = getList(rawFootTrafficData, dayOfWeekList.size());

        int maxIndex = getMaxIndex(footTrafficByDayOfWeekByArea);

        return new FootTrafficByDayOfWeekDto(footTrafficByDayOfWeekByArea, dayOfWeekList, dayOfWeekList.get(maxIndex));
    }



    @Override
    public FootTrafficByHourDto getFootTrafficByHour(Long areaId) {
        List<String> hourList = List.of("0시~06시", "06시~11시", "11시~14시", "14시~17시", "17시~21시", "21시~24시");
        List<Long> footTrafficByHourByArea = getList(populationRepository.getFootTrafficByHourByAreaId(areaId), hourList.size());

        System.out.println(footTrafficByHourByArea);
        int maxIndex = getMaxIndex(footTrafficByHourByArea);

        return new FootTrafficByHourDto(footTrafficByHourByArea, hourList, hourList.get(maxIndex));
    }

    // 이거 2개가 null인 값이 존재함.
    // 3110946
    // 3110948
    @Override
    public FootTrafficByMonthDto getFootTrafficByQuarterly(Long areaId) {
        List<String> quarterList = List.of("2023/1", "2023/2", "2023/3", "2023/4", "2024/1");
        List<Long> footTrafficByQuarterlyByArea = getList(populationHistoryRepository.getPopulationHistoryByAreaId(areaId), quarterList.size());

        String QOQ = compareLast(quarterList, footTrafficByQuarterlyByArea);

        filterZeroValues(footTrafficByQuarterlyByArea, quarterList);

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(footTrafficByQuarterlyByArea, quarterList);
        footTrafficByQuarterlyByArea = listListPair.getFirst();
        quarterList = listListPair.getSecond();

        if (footTrafficByQuarterlyByArea.isEmpty()){
            return new FootTrafficByMonthDto(footTrafficByQuarterlyByArea, quarterList, null);
        }
        return new FootTrafficByMonthDto(footTrafficByQuarterlyByArea, quarterList, QOQ);
    }


    @Override
    public FootTrafficByAgeDto getFootTrafficByAge(Long areaId) {
        List<String> ageList = List.of("10대", "20대", "30대", "40대", "50대", "60대");
        List<Long> footTrafficByAgeByArea = getList(populationRepository.getFootTrafficByAgeByAreaId(areaId), ageList.size());

        int maxIndex = getMaxIndex(footTrafficByAgeByArea);
        return new FootTrafficByAgeDto(footTrafficByAgeByArea, ageList, ageList.get(maxIndex));
    }

    @Override
    public FootTrafficByGenderDto getFootTrafficByGender(Long areaId) {
        List<String> genderList = List.of("남", "여");
        List<Long> footTrafficByGenderByArea = getList(populationRepository.getFootTrafficByGenderByAreaId(areaId), genderList.size());

        int maxIndex = getMaxIndex(footTrafficByGenderByArea);


        return new FootTrafficByGenderDto(footTrafficByGenderByArea, genderList, genderList.get(maxIndex));
    }

    @Override
    public Long getSaleByMonth(Long areaId, String service) {
        Optional<Long> sale = saleRepository.getSaleByMonthByAreaId(areaId, service);

        return sale.orElse(0L);
    }

    @Override
    public SalesByDayOfWeekDto getSaleByDayOfWeek(Long areaId, String service) {
        List<Long> saleByDayOfWeekByArea = getList(saleRepository.getSaleByDayOfWeek(areaId, service), 7);
        List<String> dayOfWeekList = List.of("월", "화", "수", "목", "금", "토", "일");

        int maxIndex = getMaxIndex(saleByDayOfWeekByArea);
        System.out.println(saleByDayOfWeekByArea);
        System.out.println(dayOfWeekList);
        System.out.println(maxIndex);

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(saleByDayOfWeekByArea, dayOfWeekList);
        saleByDayOfWeekByArea = listListPair.getFirst();
        dayOfWeekList = listListPair.getSecond();


        if (dayOfWeekList.isEmpty()) {
            return new SalesByDayOfWeekDto(saleByDayOfWeekByArea, dayOfWeekList, null );
        }

        return new SalesByDayOfWeekDto(saleByDayOfWeekByArea, dayOfWeekList, dayOfWeekList.get(maxIndex));
    }

    @Override
    public SalesByHourDto getSaleByHour(Long areaId, String service) {
        List<Long> saleByHourByArea = getList(saleRepository.getSaleByHour(areaId, service), 6);
        List<String> hourList = List.of("0시~06시", "06시~11시", "11시~14시", "14시~17시", "17시~21시", "21시~24시");

        int maxIndex = getMaxIndex(saleByHourByArea);

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(saleByHourByArea, hourList);
        saleByHourByArea = listListPair.getFirst();
        hourList = listListPair.getSecond();

        if (hourList.isEmpty()) {
            return new SalesByHourDto(saleByHourByArea, hourList, null );
        }

        return new SalesByHourDto(saleByHourByArea, hourList, hourList.get(maxIndex));
    }

    @Override
    public SalesByQuarterlyDto getSaleByQuarterly(Long areaId, String service) {
        List<Long[]> salesHistoryData = salesHistoryRepository.getSalesHistoryByDongId(areaId, service);
        List<Long> saleByQuarterlyByArea = getList(salesHistoryData, 5);
        List<String> quarterList = List.of("2023/1", "2023/2", "2023/3", "2023/4", "2024/1");

        String QOQ = compareLast(quarterList, saleByQuarterlyByArea);

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(saleByQuarterlyByArea, quarterList);
        saleByQuarterlyByArea = listListPair.getFirst();
        quarterList = listListPair.getSecond();

        if (quarterList.isEmpty()) {
            return new SalesByQuarterlyDto(saleByQuarterlyByArea, quarterList, null );
        }

        return new SalesByQuarterlyDto(saleByQuarterlyByArea, quarterList, QOQ);

    }

    @Override
    public SalesByAgeDto getSaleByAge(Long areaId, String service) {
        List<Long> saleByAgeByArea = getList(saleRepository.getSaleByAge(areaId, service), 6);
        List<String> ageList = List.of("10대", "20대", "30대", "40대", "50대", "60대");

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(saleByAgeByArea, ageList);
        saleByAgeByArea = listListPair.getFirst();
        ageList = listListPair.getSecond();
        int manIndex = getMaxIndex(saleByAgeByArea);
        if (saleByAgeByArea.isEmpty()) {
            return new SalesByAgeDto(saleByAgeByArea, ageList, null );
        }

        return new SalesByAgeDto(saleByAgeByArea, ageList, ageList.get(manIndex));
    }

    @Override
    public SalesByGenderDto getSaleByGender(Long areaId, String service) {
        List<Integer[]> saleByGender = saleRepository.getSaleByGender(areaId, service);

        // saleByGender에서 Integer[] 값을 Long[]으로 변환
        List<Long[]> saleByGenderByArea = saleByGender.stream()
                .map(array -> Arrays.stream(array)  // Integer[] 배열을 스트림으로 변환
                        .map(this::convertToLong)   // 각 Integer를 Long으로 변환
                        .toArray(Long[]::new))      // Long[] 배열로 다시 변환
                .toList();                          // List<Long[]>로 변환

        List<Long> result = getList(saleByGenderByArea, 2);
        List<String> genderList = List.of("남", "여");

        int manIndex = getMaxIndex(result);

        if (result.isEmpty()) {
            return new SalesByGenderDto(result, genderList, null );
        }


        return new SalesByGenderDto(result, genderList, genderList.get(manIndex));
    }

    @Override
    public WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long areaId, String service) {
        List<Long> weekendAndWeekdaySaleByArea = getList(saleRepository.getSaleByWeekendAndWeekDay(areaId, service), 2);
        List<String> dayOfWeek = List.of("평일", "주말");

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(weekendAndWeekdaySaleByArea, dayOfWeek);
        weekendAndWeekdaySaleByArea = listListPair.getFirst();
        dayOfWeek = listListPair.getSecond();


        return new WeekendAndWeekdaySalesDto(weekendAndWeekdaySaleByArea, dayOfWeek);
    }

    @Override
    public Integer getNumberOfSimilarStores(Long areaId, String service) {
        List<Integer> numberOfSimilarStores = storeRepository.getNumberOfSimilarStores(areaId, service);

        if (numberOfSimilarStores.isEmpty()) {
            return 0;
        }

        return numberOfSimilarStores.get(0);
    }

    @Override
    public IndustryInfoDto getIndustryInfo(Long areaId, String service) {
        List<Long> industryInfo = getList(storeRepository.getIndustryInfo(areaId, service), 3);
        List<String> info = List.of("점포수", "개업률", "폐업률");

        if (industryInfo.isEmpty()) {
            return new IndustryInfoDto(List.of(0L, 0L, 0L), info);
        }

        Pair<List<Long>, List<String>> listListPair = filterZeroValues(industryInfo, info);
        industryInfo = listListPair.getFirst();
        info = listListPair.getSecond();

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
            return 0.0;
        }
        // 퍼센트 변화 계산 공식: ((현재 매출 - 전년도 매출) / 전년도 매출) * 100
        return ((double) (nowSale - lastSale) / lastSale) * 100;
    }

    //    private static <T extends Number> List<T> getList(List<T[]> objects) {
//        List<T> list = new ArrayList<>();
//        for (T[] row : objects) {
//            list.addAll(Arrays.asList(row));
//        }
//        return list;
//    }

    private static List<Long> getList(List<? extends Number[]> objects, int expectedSize) {
        List<Long> list = new ArrayList<>();

        // 값이 전부 비어 있으면 전부 0으로 돌려줌.
        if (objects.isEmpty()) {
            while (list.size() < expectedSize) {
                list.add(0L);  // Long 타입의 0 추가
            }
            return list;
        }

        // 중간에 값이 없으면 그 값을 0으로 채움.
        // 각 배열의 값을 Long으로 변환하여 리스트에 추가
        for (Number[] row : objects) {
            for (Number num : row) {
                System.out.println(num);
                if (num == null) {
                    list.add(0L);
                } else {
                    list.add(num.longValue());  // 모든 Number를 Long으로 변환하여 추가
                }
            }
        }

        // 리스트가 비어있거나 예상 크기보다 작은 경우 0으로 채움
        if (list.isEmpty()) {
            while (list.size() < expectedSize) {
                list.add(0L);  // Long 타입의 0 추가
            }
        }

        return list;
    }


    // 불가피한 타입 캐스팅 경고를 억제합니다.
    @SuppressWarnings("unchecked")
    private static <T extends Number> T getZeroValue(List<T[]> objects) {
        if (!objects.isEmpty() && objects.get(0).length > 0) {
            T sample = objects.get(0)[0];
            if (sample instanceof Long) {
                return (T) Long.valueOf(0L);
            } else if (sample instanceof Integer) {
                return (T) Integer.valueOf(0);
            } else if (sample instanceof Double) {
                return (T) Double.valueOf(0.0);
            } else if (sample instanceof Float) {
                return (T) Float.valueOf(0.0f);
            }
        }
        // 기본값으로 Integer 0 반환
        return (T) Integer.valueOf(0);
    }

    private static String compareLast(List<String> quarterList, List<Long> footTrafficByQuarterlyByArea) {
        String currentQuarter = getCurrentQuarter();
        String lastYear = String.valueOf(getLastYear());
        String nowYear = String.valueOf(getLastYear() + 1);
        String last = String.format("%s/1", lastYear);
        String now = String.format("%s/1", nowYear);

        int nowIndex = quarterList.indexOf(now);
        int lastIndex = quarterList.indexOf(last);

        Long lastSale = footTrafficByQuarterlyByArea.get(lastIndex);
        Long nowSale = footTrafficByQuarterlyByArea.get(nowIndex);

        return getString(lastSale, nowSale);
    }

    private static String getString(Long lastSale, Long nowSale) {
        double percentageChange = calculatePercentageChange(lastSale, nowSale);

        // 이전 분기에 비해 ~~ 하고 있습니다.
        // 해당업종의 매출이 이전분기에 비해 {{ QuarterlySales.qoq }}하고 있습니다.
        // 이전 연도는 이전 매출가 있습니다.

        String QOQ = "유지";
        if (nowSale == 0) {
            QOQ = "올해 없음";
        }
        else if (lastSale == 0) {
            QOQ = "현재 없음";
        }
        else if (percentageChange > 0) {
            QOQ = String.format("%.2f%% 상승", percentageChange);
        } else if (percentageChange < 0) {
            QOQ = String.format("%.2f%% 하락", Math.abs(percentageChange));
        }
        return QOQ;
    }

    private Pair<List<Long>, List<String>> filterZeroValues(List<Long> trafficList, List<String> hourList) {
        List<Long> filteredTrafficList = new ArrayList<>();
        List<String> filteredHourList = new ArrayList<>();

        // 두 리스트의 크기가 같다는 가정하에 인덱스를 순회
        for (int i = 0; i < trafficList.size(); i++) {
            if (trafficList.get(i) != 0) {
                filteredTrafficList.add(trafficList.get(i)); // 값이 0이 아닌 경우에만 추가
                filteredHourList.add(hourList.get(i));       // 대응하는 hourList 값도 추가
            }
        }
        return Pair.of(filteredTrafficList, filteredHourList);  // 두 리스트를 함께 반환
    }

}
