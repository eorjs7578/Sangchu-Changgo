# 상추:창고 <br>상권 추천 창업 레츠고
SSAFY 11기 특화 프로젝트 

## 📜 Contents
 1. [프로젝트 개요](#1️⃣프로젝트-개요)
 2. [주요 기능](#2️⃣주요-기능)
 3. [개발 환경](#3️⃣개발-환경)
 4. [기술 특이점](#4️⃣기술-특이점)
 5. [산출물](#5️⃣산출물)
 6. [팀원 소개](#6️⃣팀원-소개)

## 1️⃣프로젝트 개요
### 프로젝트 기간
2024.08.19 ~ 2024.10.11 (8주)

### 프로젝트 소개
상추:창고는 프랜차이즈 창업을 희망하는 사람들에게 원하는 지역 상권 및 프랜차이즈에 대한 다양한 정보와 분석을 제공하여 성공적인 창업의 길을 더욱 쉽게 열어갈 수 있습니다.

### 기획의도
프랜차이즈 시장의 급성장으로 인해 경쟁이 심화되고 **진입장벽이 높아지고 있습니다**. 많은 사람들이 유행을 따라 창업에 나서지만, 충분한 조사 없이 성급하게 시작할 경우 실패할 위험이 큽니다. 유행의 속도가 가속화되면서 신규 프랜차이즈 업체의 수도 급증한 지금, 내가 선택한 프랜차이즈가 장기간 안정적인 수익을 창출할 수 있을지를 철저히 검토하는 것이 필요합니다.

 `상추창고`는 **신중한 시장 진입과 철저한 상권 분석**에 중점을 둡니다. 또한, 정확한 분석과 함께 같은 고민을 공유하는 사람들과의 커뮤니티를 형성하여 생생한 정보를 업데이트하고 교류하며 창업자들이 보다 안정적인 환경에서 사업을 시작할 수 있도록 돕는 것을 목표로 합니다.

## 2️⃣주요 기능 
- 빅데이터를 활용한 상권 분석
- 프랜차이즈 창업 비용 예측
- 상권 및 프랜차이즈 커뮤니티
- 상권 및 게시판 검색어 추천 


## 3️⃣개발 환경

|   TYPE    | SKILL           | VERSION          |
| :-------: | :-------------- | :--------------- |
| LANGUAGES | Java            | 17           |
|           | Python          | 3.12.4              |
|           | Javascript      | ES6              |
| BE | Sprint boot     | 3.3.2           |
|           | Spring Data JPA |                  |
|           | querydsl |       5.0.0           |
|  FE         | Vue           | 3.4.29               |
| DATABASE  | MariaDB           | 8.0              |
|           | Redis           |                  |
|   INFRA   | AWS EC2         | ubuntu(x86, arm) |
|           | Jenkins         |        2.479          |
|           | Docker          |        24.0.7         |
|           | redis-cli           |      7.4.0            |
|           | hadoop           |      3.3.1           |
|           | spark           |         3.5.2         |




## 4️⃣기술 특이점 


- ### 하둡을 사용한 분산 처리
    - Hadoop 클러스터 서버에서 Spark를 사용하여 데이터 전처리 및 데이터 저장
    
    - HDFS(Hadoop Distributed File System)는 하둡 분산 파일 시스템으로, Hadoop 에서는 파일(데이터) 를 Block 단위로 관리하게 됨. 하나의 파일을 여러개의 Block으로 쪼개고 이 Block들을 여러 nodes 들에 분산하여 저장하는 원리로, 이 프로젝트에서는 아래 정보들에 대해 분산 저장시킴.
    
    - 상권 분석 정보
        - 유동인구: 상권 별 연령별, 분기별 유동인구
        - 매출: 상권과 서비스 업종 별 연령별, 분기별 매출
        
    - Spark의 사용 : Hadoop 의 Map Reduce 모델은 대용량 데이터 분석을 쉽게 만들어주었지만,  ML이나 Graph 와 같은 복잡한 처리에 부족하고 HDFS(스토리지)에 데이터를 읽고 쓰는 연산을 해 반복 작업을 하는 경우 성능 저하가 일어난다는 단점이 있다. 
    
        따라서 Disk 대신 가격이 저렴해지고 빠른 RAM을 사용하여 데이터를 처리해보자는 아이디어가 Spark이고, 이러한 RAM의 특성에 최적화하여 fault-tolerant 하고 효율적인 방식으로 데이터를 처리하기 위해, RAM을 read-only 방식으로 사용하기 위해 등장한 것이 바로 RDD(Resilient Distributed Dataset).
    
        하지만 버전이 올라감에 따라 RDD와 같은 저수준 API보다 DataFrame, DataSet과 같은 고수준 API를 사용하며 이 프로젝트에선 DataFrame API를 사용.
    
    - 아래 코드는 Spark Session으로 시작해 분산처리 환경에서 데이터를 읽고, 처리하는 코드입니다. 특히 Spark의 `groupBy`와 `pivot` 연산은 기본적으로 많은 데이터를 처리할 때 각 워커 노드에서 병렬로 연산이 이루어지며, 그 결과를 셔플링(shuffling)하여 집계합니다. Spark 클러스터의 노드가 여러 개 있을 경우, 이 코드의 각 연산이 여러 노드에서 분산되어 처리됨.
    
        
    
        ```python
        # Spark 세션 시작
        spark = SparkSession.builder \
            .appName("CSV to MariaDB") \
            .getOrCreate()
            
        # CSV 파일 읽기
        area_df = spark.read.option("encoding", "cp949").csv("hdfs://master:9000/input/서울시_상권분석서비스(추정매출-상권).csv", header=True, inferSchema=True)
        
        # 필요한 컬럼 선택
        area_2023_2024 = area_df.select('기준_년분기_코드', '상권_코드', '상권_코드_명', '서비스_업종_코드', '서비스_업종_코드_명', '당월_매출_금액')
        
        # groupBy와 pivot을 사용해 기준_년분기_코드별 매출 합계 계산
        pivot_area = area_2023_2024.groupBy('상권_코드', '상권_코드_명', '서비스_업종_코드', '서비스_업종_코드_명') \
                                     .pivot('기준_년분기_코드') \
                                     .agg(F.sum('당월_매출_금액'))
        ```
    
        

- ### Elastic Search를 활용한 검색어 추천
    - 상권 검색어 추천 인덱스 
        - 색인 시, 접두사 부터 접근하는 방식과 형태소로 접근하는 방식에 모두 대응하기 위해서 `nori 분석기`와 `edge ngram` 분석기 사용
        - 검색 시, 상권의 경우 어느 정도 정보를 알고 단어의 앞부터 완벽한 단어로 검색할 것으로 예상하여 `edge ngram 분석기` 사용
    - 게시판 검색어 추천 인덱스
        - 프랜차이즈의 이름을 통해 검색하는 경우:
            - 색인 시, 프랜차이즈의 경우 이름이 정확하게 기억이 안날 확률이 높으므로 `표준 토크나이저`, `n-gram`, `edge ngram`, `nori 분석기`로 다양하게 사용 
            - 검색 시, 프랜차이즈 이름은 사전 정보의 한계 때문에 형태소 기반 분석이 어려움 그러하여 `ngram` 분석기와 `edge ngram` 분석기 사용
        - 상권의 이름을 통해 검색하는 경우:
            - 색인 시, 접두사 부터 접근하는 방식과 형태소로 접근하는 방식에 둘다 대응하기 위해서 `nori 분석기`와 `edge ngram 분석기` 사용
            - 검색 시, 상권의 경우 어느 정도 정보를 알고 단어의 앞부터 완벽한 단어로 검색할 것으로 예상하여 `edge ngram 분석기` 사용
        - 여러 분석기를 통해 나온 값을 정확도를 높히기 위해 가장 관련성 높은 결과를 우선순위로 두는 `Dis Max 쿼리` 사용 
    - 이후 검색의 다양성을 위해 두 인덱스 결과 중 **하나의 인덱스의 결과에서 최소 20퍼센트를 보장.**



## 5️⃣산출물 

### 아키텍처
![시스템 구조도](/image/시스템구조도.png)

### 피그마
[figma](https://www.figma.com/design/tde91Y45Rt5Eoyb0AAtLZy/D108-%ED%8A%B9%ED%99%94%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0-1&node-type=canvas&t=2XqcXYVPhUk2vsCN-0)

### 기술 스택
[기술 스택 상세보기](https://radial-detective-510.notion.site/4cd2ec1bd67442e1a89a33d81e0accd3)
![기술 스택](/image/기술스택.PNG)

### ERD
[ERD 상세 보기](https://www.erdcloud.com/d/3kkFXSxpjhoWZ3LYJ) 
![ERD](/image/ERD.png)


## 6️⃣팀원 소개

|  이름  | 역할                                                                             |
| :----: | :------------------------------------------------------------------------------- |
| 조시현 |  팀장, Backend, Frontend          |
| 박동현 |  Backend, Frontend                |
| 조민기 |  Backend, Frontend, Data Infra    |
| 이승봉 |  Backend, Frontend, Infra         |
| 곽대건 |  Backend, Frontend                |
| 정진규 |  Backend, Frontend, Data Engineer |
| 김민솔 |  Backend, Frontend, Data Engineer |

