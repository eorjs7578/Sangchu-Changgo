import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getType(success,fail) {
  local.get(`/api/franchises/type`).then(success).catch(fail);
}

function getFranchisesByType(type,success,fail) {
  local.get(`/api/franchises/type/${type}`).then(success).catch(fail);
}


export {
  getType,
  getFranchisesByType
}
