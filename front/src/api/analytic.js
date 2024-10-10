import { localAxios } from "@/util/http-commons";

const local = localAxios();

// 인구관련

function getDailyPopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/daily/${areaId}`) // areaId를 URL에 동적으로 삽입
      .then(success) // 성공 시 콜백 함수 호출
      .catch(fail); // 실패 시 콜백 함수 호출
  }

function getWeeklyPopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/day-of-week/${areaId}`) // areaId를 URL에 동적으로 삽입
        .then(success) // 성공 시 콜백 함수 호출
        .catch(fail); // 실패 시 콜백 함수 호출
}

function getHourlyPopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/hour/${areaId}`) // areaId를 URL에 동적으로 삽입
      .then(success) // 성공 시 콜백 함수 호출
      .catch(fail); // 실패 시 콜백 함수 호출
  }

function getAgePopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/age/${areaId}`) // areaId를 URL에 동적으로 삽입
      .then(success) // 성공 시 콜백 함수 호출
      .catch(fail); // 실패 시 콜백 함수 호출
  }

  function getGenderPopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/gender/${areaId}`) // areaId를 URL에 동적으로 삽입
      .then(success) // 성공 시 콜백 함수 호출
      .catch(fail); // 실패 시 콜백 함수 호출
  }

  function getQuarterlyPopulation(areaId, success, fail) {
    local.get(`/api/areas/analytic/foot-traffics/quarterly/${areaId}`) // areaId를 URL에 동적으로 삽입
      .then(success) // 성공 시 콜백 함수 호출
      .catch(fail); // 실패 시 콜백 함수 호출
  } 
  

// 매출 관련
function getSalesByMonth(areaId, service, success, fail) {
  local.get(`/api/areas/analytic/sales/month/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getSalesByWeek(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/sales/day-of-week/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getSalesByHour(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/sales/hour/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getSalesByAge(areaId, service, success, fail) {
  local.get(`/api/areas/analytic/sales/age/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getSalesByGender(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/sales/gender/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 
function getSalesByWeekend(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/sales/weekend-weekday/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 
function getSalesByQuarterly(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/sales/quarterly/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getNumberOfSimilarStores(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/similar-industry/number/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getIndustryInfo(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/industries/info/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
} 

function getSimilarServiceCount(areaId, service, success, fail)  {
  local.get(`/api/areas/analytic/similar-industry/number/${areaId}/${service}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail); // 실패 시 콜백 함수 호출
}

// 평가 관련
function postAreaEvaluation(areaId, evaluationData, success, fail) {
  local.post(`/api/area-evaluation/${areaId}`, evaluationData) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail) // 실패 시 콜백 함수 호출
}

function putAreaEvaluation(areaId, evaluationId, evaluationData, success, fail) {
  local.put(`/api/area-evaluation/${evaluationId}`, evaluationData) // evaluationId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail) // 실패 시 콜백 함수 호출
}

function deleteAreaEvaluation(areaId, evaluationId, success, fail) {
  local.delete(`/api/area-evaluation/${evaluationId}`) // evaluationId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail) // 실패 시 콜백 함수 호출
}

function getMyAreaEvaluation(areaId, evaluationId, success, fail) {
  local.get(`/api/area-evaluation/${areaId}`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail) // 실패 시 콜백 함수 호출
}

function getAllAreaEvaluationType(areaId, evaluationId, success, fail) {
  local.get(`/api/area-evaluation/type`) // areaId를 URL에 동적으로 삽입
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail) // 실패 시 콜백 함수 호출
}

function getStatisticsAreaEvaluation(areaId, evaluationId, success, fail) {
  local.get(`/api/area-evaluation/statistics/${areaId}`)
    .then(success) // 성공 시 콜백 함수 호출
    .catch(fail);
}

export {
    getDailyPopulation,
    getWeeklyPopulation,
    getHourlyPopulation,
    getAgePopulation,
    getGenderPopulation,
    getQuarterlyPopulation,
    getSalesByMonth,
    getSalesByWeek,
    getSalesByHour,
    getSalesByAge,
    getSalesByGender,
    getSalesByWeekend,
    getSalesByQuarterly,
    getNumberOfSimilarStores,
    getIndustryInfo,
    getSimilarServiceCount,
    postAreaEvaluation,
    putAreaEvaluation,
    deleteAreaEvaluation,
    getMyAreaEvaluation,
    getAllAreaEvaluationType,
    getStatisticsAreaEvaluation
}
