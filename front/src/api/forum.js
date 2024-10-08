import { localAxios } from '@/util/http-commons';

const local = localAxios();

// 게시글 생성 API
function createPost(payload, success, fail) {
    local.post(`/api/posts`, payload).then(success).catch(fail);
}

// 게시글 목록 조회 API (페이지네이션 적용)
function getPostListByBoard(boardId, page, size, success, fail) {
    console.log('API 요청을 보낼 boardId:', boardId, 'page:', page, 'size:', size); // boardId, page, size 확인

    local
        .get(`/api/posts/${boardId}/${page}/${size}`)
        .then((response) => {
            console.log('API 응답 성공:', response); // API 성공 응답 내용 콘솔에 출력
            success(response);
        })
        .catch((error) => {
            console.error('API 응답 에러:', error); // API 실패 응답 내용 콘솔에 출력
            fail(error);
        });
}

// 게시글 상세 조회 API
function getPostDetail(postId, success, fail) {
    local
        .get(`/api/posts/${postId}`)
        .then((response) => {
            console.log('API 응답:', response);
            success(response);
        })
        .catch((error) => {
            console.log('API 호출 에러:', error);
            fail(error);
        });
}

// 게시글 수정 API
function updatePost(postId, payload, success, fail) {
    local.put(`/api/posts/${postId}`, payload).then(success).catch(fail);
}

// 게시글 삭제 API
function deletePost(postId, success, fail) {
    // deletePostAPI -> deletePost
    local.delete(`/api/posts/${postId}`).then(success).catch(fail);
}

// 댓글 등록 API
function createReply(postId, payload, success, fail) {
    // submitReplyAPI -> createReply
    local.post(`/api/posts/${postId}/replies`, payload).then(success).catch(fail);
}

// 댓글 목록 조회 API
function getReplyList(postId, replyId, success, fail) {
    // getRepliesAPI -> getReplyList
    local.get(`/api/posts/${postId}/replies/${replyId}`).then(success).catch(fail);
}

// 댓글 수정 API
function updateReply(postId, replyId, payload, success, fail) {
    local.put(`/api/posts/${postId}/replies/${replyId}`, payload).then(success).catch(fail);
}

// 댓글 삭제 API
function deleteReply(postId, replyId, success, fail) {
    console.log('삭제할 댓글 API 호출 - Post ID:', postId, 'Reply ID:', replyId); // 확인용 로그
    local.delete(`/api/posts/${postId}/replies/${replyId}`).then(success).catch(fail);
}

export {
    createPost,
    getPostListByBoard,
    getPostDetail,
    updatePost,
    deletePost,
    createReply,
    getReplyList,
    updateReply,
    deleteReply,
};
