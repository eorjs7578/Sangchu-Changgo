import { localAxios } from "@/util/http-commons";

const local = localAxios();

function createPost(payload, success, fail) {
  local.post(`/api/posts`, payload)
    .then(success)
    .catch(fail);
}

// function createPost(payload, success, fail) {
//   local.post(`/api/posts`, payload)
//     .then((response) => {
//       // 성공 시 응답에서 postId 받아오기
//       const postId = response.data.postId; // 응답에서 postId 추출
//       console.log("게시글 생성 성공, Post ID:", postId);  // postId 출력
//       success(postId);  // postId를 성공 콜백으로 전달
//     })
//     .catch(fail);  // 실패 시 실패 콜백 처리
// }

function listPost(areaId, success, fail) {
  // GET 요청에서 경로 변수와 쿼리 파라미터를 함께 사용하는 방법
  console.log("API 요청을 보낼 areaId:", areaId); // areaId가 제대로 전달되고 있는지 확인
  local.get(`/api/posts/area/${areaId}`)
  .then(success)
  .catch(fail);
}

function getPostDetail(postId, success, fail) {
  local.get(`/api/posts/${postId}`)
    .then((response) => {
      console.log('API 응답:', response);
      success(response);
    })
    .catch((error) => {
      console.log('API 호출 에러:', error);
      fail(error);
    });
}

function deletePost(postId, success, fail) {
  local.delete(`/api/posts/${postId}`)
    .then(success)
    .catch(fail);
}

export {
  createPost,
  listPost,
  getPostDetail
}