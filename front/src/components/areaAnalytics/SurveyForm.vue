<template>
    <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl overflow-hidden">
        <div class="p-6">
          <h2 class="text-2xl font-bold mb-6">평가하기</h2>
          
          <div class="space-y-6">
            <div>
              <h3 class="text-lg font-semibold mb-2">주 연령층</h3>
              <div class="grid grid-cols-3 gap-2 mb-2">
                <button v-for="option in ageOptions.slice(0, 6)" :key="option"
                        @click="selectOption('age', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.age === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
              <div class="grid grid-cols-3 gap-2">
                <button v-for="option in ageOptions.slice(6)" :key="option"
                        @click="selectOption('age', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.age === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
            </div>
            
            <div>
              <h3 class="text-lg font-semibold mb-2">유동인구</h3>
              <div class="flex gap-2">
                <button v-for="option in mobilityOptions" :key="option"
                        @click="selectOption('mobility', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.mobility === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
            </div>
            
            <div>
              <h3 class="text-lg font-semibold mb-2">물가</h3>
              <div class="flex gap-2">
                <button v-for="option in priceOptions" :key="option"
                        @click="selectOption('price', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.price === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
            </div>
            
            <div>
              <h3 class="text-lg font-semibold mb-2">분위기</h3>
              <div class="grid grid-cols-3 gap-2 mb-2">
                <button v-for="option in ageOptions.slice(0, 6)" :key="option"
                        @click="selectOption('atmosphere', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.atmosphere === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
              <div class="grid grid-cols-3 gap-2">
                <button v-for="option in ageOptions.slice(6)" :key="option"
                        @click="selectOption('atmosphere', option)"
                        :class="['px-3 py-2 rounded-md text-sm', 
                                 selectedOptions.atmosphere === option ? 'bg-green-500 text-white' : 'bg-gray-200']">
                  {{ option }}
                </button>
              </div>
            </div>
          </div>
          
          <div class="flex justify-between mt-6">
            <button class="px-4 py-2 bg-gray-200 rounded-md">수정하기</button>
            <button class="px-4 py-2 bg-green-500 text-white rounded-md">삭제하기</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const ageOptions = [
    '10대 남성', '20대 남성', '30대 남성', '40대 남성', '50대 남성', '60대 이상 남성',
    '10대 여성', '20대 여성', '30대 여성', '40대 여성', '50대 여성', '60대 이상 여성'
  ]
  
  const mobilityOptions = ['많다', '적당하다', '적다']
  const priceOptions = ['싸다', '적당하다', '비싸다']
  
  const selectedOptions = ref({
    age: '',
    mobility: '',
    price: '',
    atmosphere: ''
  })
  
  const selectOption = (category, option) => {
    selectedOptions.value[category] = option
  }
  </script>