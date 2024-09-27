import { defineStore } from "pinia";
import { ref } from "vue";
import {api} from "@/lib/api.js";

export const useAccountStore = defineStore("accounts", () => {
  const isAuthenticated = ref(false);

  /**
   * 인증 정보를 체크하는 함수
   */
  function checkAuthStatus()  {
    return api.get("/api/members/my-info")
      .then(response => {
        isAuthenticated.value = response.data
      })
      .catch(() => {
        isAuthenticated.value = false;
      })
  }
 return {
    isAuthenticated,
    checkAuthStatus
 }
})