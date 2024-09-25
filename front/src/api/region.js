import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getGu(success,fail) {
  local.get(`/api/map/info/gu`).then(success).catch(fail);
}

function getDong(regCode,success,fail) {
  local.get(`/api/map/info/dong?code=${regCode}`).then(success).catch(fail);
}


export {
  getGu,
  getDong
}
