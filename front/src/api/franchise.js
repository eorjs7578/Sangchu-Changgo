import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getType(success,fail) {
  local.get(`/api/franchises/type`).then(success).catch(fail);
}

function getFranchisesByType(type,success,fail) {
  local.get(`/api/franchises/type/${type}`).then(success).catch(fail);
}

function getFranchisesFee(id,dongCode,size,floor,success,fail) {
  local.get(`/api/franchises/fee/${id}?dongCode=${dongCode}&size=${size}&floor=${floor}`).then(success).catch(fail);
}

function getFavoriteFranchises(success,fail) {
  local.get(`/api/favorite/franchises/list`).then(success).catch(fail);
}

function deleteFavoriteFranchise(id,success,fail) {
  local.delete(`/api/favorite/franchises/${id}`).then(success).catch(fail);
}

function addFavoriteFranchise(payload,success,fail) {
  local.post(`/api/favorite/franchises`,payload).then(success).catch(fail);
}

export {
  getType,
  getFranchisesByType,
  getFranchisesFee,
  getFavoriteFranchises,
  deleteFavoriteFranchise,
  addFavoriteFranchise
}
