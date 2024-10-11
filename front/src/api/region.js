import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getGu(success,fail) {
  local.get(`/api/v1/map/info/gu`).then(success).catch(fail);
}

function getDong(regCode,success,fail) {
  local.get(`/api/v1/map/info/dong?code=${regCode}`).then(success).catch(fail);
}

function getGuRentFee(regCode,success,fail){
  local.get(`/api/v1/rent/gu/${regCode}`).then(success).catch(fail);
}

function getDongRentFee(regCode,success,fail){
  local.get(`/api/v1/rent/dong/${regCode}`).then(success).catch(fail);
}

function getAreaServiceType(regCode,success,fail){
  local.get(`/api/v1/service/area/${regCode}`).then(success).catch(fail);
}


export {
  getGu,
  getDong,
  getGuRentFee,
  getDongRentFee,
  getAreaServiceType
}
