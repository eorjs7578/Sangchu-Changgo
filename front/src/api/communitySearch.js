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



async  function getAll(page,size,str) {
    return await getRequest(`/api/v1/boards/search/${page}/${size}?keyword=${str}`);
}
async  function getFranchise(page,size,str) {
    return await getRequest(`/api/v1/boards/search/franchise/${page}/${size}?keyword=${str}`);
}
async  function getArea(page,size,str) {
    return await getRequest(`/api/v1/boards/search/area/${page}/${size}?keyword=${str}`);
}

async  function getHotArea() {
    return await getRequest(`/api/v1/boards/popular/area`);
}
async  function getHotFranchise() {
    return await getRequest(`/api/v1/boards/popular/franchise`);
}

async  function getALLArea(page,size) {
    return await getRequest(`/api/v1/boards/all/area/${page}/${size}`);
}
async  function getALLFranchise(page,size) {
    return await getRequest(`/api/v1/boards/all/franchise/${page}/${size}`);
}

// 게시판 이름 조회 API
async function getBoardName(boardId) {
    return await getRequest(`/api/v1/boards/${boardId}`);
}


export {
    getAll,
    getFranchise,
    getArea,
    getHotArea,
    getHotFranchise,
    getALLArea,
    getALLFranchise,
    getBoardName
}