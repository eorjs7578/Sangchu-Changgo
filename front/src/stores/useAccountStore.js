import { defineStore } from "pinia";
import { ref } from "vue";
import {api} from "@/lib/api.js";

export const useAccountStore = defineStore("accounts", () => {
  const isAuthenticated = ref(false);
  const userInfo = ref({})

  /**
   * 인증 정보를 체크하는 함수
   */
  function checkAuthStatus() {
    return api.get("/api/members/my-info")
      .then(response => {
        console.log(response.data)
        isAuthenticated.value = true;
        return response
      })
      .catch(() => {
        isAuthenticated.value = false;
      })
  }


  return {
    isAuthenticated,
    checkAuthStatus,
    userInfo
  }
},
   {
     persist: {
       paths: ['isAuthenticated', 'userInfo'],
       storage: sessionStorage,
     }
   }
)