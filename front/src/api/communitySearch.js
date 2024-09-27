import { localAxios } from "@/util/http-commons";

const local = localAxios();


async function getRequest(url) {
    return await local.get(url)
      .then(response => response.data)
      .catch(error => {
        console.error(`Error fetching ${url}:`, error);
        throw error;
      });
}



async  function getAll(str) {
    return await getRequest(`/api/board/search?keyword=${str}`);
}
async  function getFranchise(str) {
    return await getRequest(`/api/board/search/franchise?keyword=${str}`);
}
async  function getArea(str) {
    return await getRequest(`/api/board/search/area?keyword=${str}`);
}

async  function getHotArea(str) {
    return await getRequest(`/api/board/popular/area?keyword=${str}`);
}
async  function getHotFranchise(str) {
    return await getRequest(`/api/board/popular/franchise?keyword=${str}`);
}

async  function getALLArea(page,size) {
    return await getRequest(`/api/board/all/area/${page}/${size}`);
}
async  function getALLFranchise(page,size) {
    return await getRequest(`/api/board/all/franchise/${page}/${size}`);
}

export {
    getAll,
    getFranchise,
    getArea,
    getHotArea,
    getHotFranchise,
    getALLArea,
    getALLFranchise
}