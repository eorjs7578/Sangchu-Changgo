import { defineStore } from "pinia";
import { ref } from "vue";
import {api} from "@/lib/api.js";

export const useAccountStore = defineStore("accounts", () => {
  const isAuthenticated = ref(false);
  const userInfo = ref({})
  /**
   * 인증 정보를 체크하는 함수
   */
  function checkAuthStatus()  {
    return api.get("/api/members/my-info")
      .then(response => {
        isAuthenticated.value = true;
        userInfo.value = response.data;
        return isAuthenticated
      })
      .catch(() => {
        isAuthenticated.value = false;
        return isAuthenticated
      })
  }


 return {
    isAuthenticated,
    checkAuthStatus,
    userInfo
 }
})