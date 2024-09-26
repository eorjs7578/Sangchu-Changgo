import { localAxios } from "@/util/http-commons";

const local = localAxios();

function createPost(postData, success, fail) {
  // area_id를 URL에 포함시켜 PathVariable로 전달
  local.post(`/api/posts`, postData)
    .then(success)
    .catch(fail);
}

export {
  createPost
}