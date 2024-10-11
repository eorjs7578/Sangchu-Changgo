import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getType(success,fail) {
  local.get(`/api/v1/franchises/type`).then(success).catch(fail);
}

function getFranchisesByType(type,success,fail) {
  local.get(`/api/v1/franchises/type/${type}`).then(success).catch(fail);
}

function getFranchisesFee(id,dongCode,size,floor,success,fail) {
  local.get(`/api/v1/franchises/fee/${id}?dongCode=${dongCode}&size=${size}&floor=${floor}`).then(success).catch(fail);
}

function getFavoriteFranchises(success,fail) {
  local.get(`/api/v1/favorite/franchises/list`).then(success).catch(fail);
}

function deleteFavoriteFranchise(id,success,fail) {
  local.delete(`/api/v1/favorite/franchises/${id}`).then(success).catch(fail);
}

function addFavoriteFranchise(payload,success,fail) {
  local.post(`/api/v1/favorite/franchises`,payload).then(success).catch(fail);
}

export {
  getType,
  getFranchisesByType,
  getFranchisesFee,
  getFavoriteFranchises,
  deleteFavoriteFranchise,
  addFavoriteFranchise
}
