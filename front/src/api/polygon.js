import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function getRequest(url, headers = {}) {
    return await local.get(url, { headers })
      .then(response => response.data)
      .catch(error => {
        console.error(`Error fetching ${url}:`, error);
        throw error;
      });
  }


async function getGu(headers) {
    return await getRequest(`/api/map/gu`, headers);
  }
  
  /**
   * 동 데이터를 가져오는 함수
   * @param {string} code - 동 코드
   * @param {object} headers - 추가할 헤더 정보 (선택 사항)
   * @returns {Promise} - 동 데이터를 가져오는 Promise
   */
  async  function getDong(code, headers) {
    return await getRequest(`/api/map/dong/${code}`, headers);
  }
  
  /**
   * 지역 데이터를 가져오는 함수
   * @param {string} code - 지역 코드
   * @param {object} headers - 추가할 헤더 정보 (선택 사항)
   * @returns {Promise} - 지역 데이터를 가져오는 Promise
   */
  async  function getArea(code, headers) {
    return await getRequest(`/api/map/area/${code}`, headers);
  }

export {
    getGu,
    getDong,
    getArea
}