<template>
  <div class="container">
  
    <div class="custom-dropdown-input">
      <div class="input-dropdown-container">
        <div class="dropdown-container">
          <button class="custom-button" @click="toggleDropdown">
            <span class="button-text">{{ selectedOption }}</span>
            <span class="dropdown-icon" :class="{ 'open': isOpen }">▼</span>
          </button>
          <transition name="fade">
            <div v-if="isOpen" class="dropdown-menu">
              <button v-for="option in options" :key="option" @click="selectOption(option)" class="dropdown-item">
                {{ option }}
              </button>
            </div>
          </transition>
        </div>
        <input 
          type="text" 
          v-model="inputValue" 
          class="custom-input" 
          placeholder="검색어를 입력하세요"
        />
        <button class="search-button" @click="handleSearch">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </button>
      </div>
    </div>
  
      <div class="category-container">
        
        <div v-for="category in categories" :key="category.name" class="category">
          <h2>{{ category.name }}</h2>
          <ul>
            <li v-for="item in category.items" :key="item.title">
              <a href="#">
                {{ item.title }}
                <span v-if="item.count" class="count">{{ item.count }}</span>
              </a>
              <span class="views">| 새글 {{ item.views }}</span>
            </li>
          </ul>
          <div class="pagination">
            <button v-for="n in 9" :key="n">{{ n }}</button>
          </div>
        </div>
      </div>
      <div class="category-container">
        
              <div v-for="board in hotBoard" :key="board.name" class="category">
                <h2>{{ board.name }}</h2>
                <ul>
                  <li v-for="item in board.items" :key="item.title">
                    <a href="#">
                      {{ item.title }}
                      <span v-if="item.count" class="count">{{ item.count }}</span>
                    </a>
                    <span class="views">| 새글 {{ item.views }}</span>
                  </li>
                </ul>
              </div>
  
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  let categories = ref([
    { name: '상권', items: [
      { title: '정글소설', count: 16, views: '3,677/9,333,012' },
      { title: '여장', count: 50, views: '1,448/3,508,182' },
      { title: '먹장', views: '566/1,059,175' },
      { title: '장사의 신 (드라마)', views: '360/265,288' },
      { title: '식이장애', count: 300, views: '212/336,653' },
      { title: '아이돌 인건극장', views: '167/123,147' },
      { title: '번개장터', views: '96/40,845' },
      { title: '활수 화장품', views: '88/1,473,038' },
    ]},
    { name: '프랜차이즈', items: [
      { title: '정글소설', count: 16, views: '3,677/9,333,012' },
      { title: '여장', count: 50, views: '1,448/3,508,182' },
      { title: '먹장', views: '566/1,059,175' },
      { title: '장사의 신 (드라마)', views: '360/265,288' },
      { title: '식이장애', count: 300, views: '212/336,653' },
      { title: '아이돌 인건극장', views: '167/123,147' },
      { title: '번개장터', views: '96/40,845' },
      { title: '활수 화장품', views: '88/1,473,038' },
    ]}
  ])
  
  let hotBoard = ref([  
    { name: '인기 상권', items: [
      { title: '정글소설', count: 16, views: '3,677/9,333,012' },
      { title: '여장', count: 50, views: '1,448/3,508,182' },
      { title: '먹장', views: '566/1,059,175' },
      { title: '장사의 신 (드라마)', views: '360/265,288' },
      { title: '식이장애', count: 300, views: '212/336,653' },
      { title: '아이돌 인건극장', views: '167/123,147' },
      { title: '번개장터', views: '96/40,845' },
      { title: '활수 화장품', views: '88/1,473,038' },
    ]},
    { name: '인기 프랜차이즈', items: [
      { title: '정글소설', count: 16, views: '3,677/9,333,012' },
      { title: '여장', count: 50, views: '1,448/3,508,182' },
      { title: '먹장', views: '566/1,059,175' },
      { title: '장사의 신 (드라마)', views: '360/265,288' },
      { title: '식이장애', count: 300, views: '212/336,653' },
      { title: '아이돌 인건극장', views: '167/123,147' },
      { title: '번개장터', views: '96/40,845' },
      { title: '활수 화장품', views: '88/1,473,038' },
    ]}
  ]);
  
  const searchQuery = ref('')
  
  const options = ['전체', '상권', '프랜차이즈']
  const selectedOption = ref('전체')
  const isOpen = ref(false)
  
  const toggleDropdown = () => {
    isOpen.value = !isOpen.value
  }
  
  const selectOption = (option) => {
    selectedOption.value = option
    isOpen.value = false
  }
  
  const search = async () => {
  
  
  }
  
  </script>
  
  <script>
  
  </script>
  
  <style scoped>
  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    font-family: Arial, sans-serif;
    text-align: center;
    overflow-y: auto;
  }
  
  .container::-webkit-scrollbar {
    display: none;
  }
  
  .category-container {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  
  .category {
    width: 48%;
    background-color: #ffffff;
    border-radius: 5%;
    padding: 2%;
    margin: 2vh auto;
  }
  
  h2 {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 10px;
  }
  
  a {
    color: #1a0dab;
    text-decoration: none;
  }
  
  a:hover {
    text-decoration: underline;
  }
  
  .count {
    color: #666;
    font-size: 14px;
    margin-left: 5px;
  }
  
  .views {
    color: #666;
    font-size: 14px;
    margin-left: 5px;
  }
  
  .pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
  
  .pagination button {
    margin: 0 5px;
    padding: 5px 10px;
    border: 1px solid #ddd;
    background-color: #fff;
    cursor: pointer;
  }
  
  .pagination button:hover {
    background-color: #f0f0f0;
  }
  
  
  
  /* //////////////////// */
  .custom-dropdown-input {
    display: flex;
    align-items: center;
    max-width: 600px;
    margin: 5vh auto;
  }
  
  .input-dropdown-container {
    display: flex;
    flex-grow: 1;
    background-color: white;
    border-radius: 24px;
    overflow: visible;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1), 0 1px 2px rgba(0, 0, 0, 0.06);
  }
  
  .dropdown-container {
    position: relative;
    z-index: 10;
  }
  
  .custom-button {
    display: flex;
    align-items: center;
    background-color: transparent;
    border: none;
    padding: 8px 16px;
    font-size: 16px;
    color: #333;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .custom-button:hover {
    background-color: rgba(0, 0, 0, 0.05);
  }
  
  .button-text {
    margin-right: 8px;
  }
  
  .dropdown-icon {
    font-size: 12px;
    transition: transform 0.3s ease;
  }
  
  .dropdown-icon.open {
    transform: rotate(180deg);
  }
  
  .dropdown-menu {
    position: absolute;
    top: 100%;
    left: 0;
    z-index: 1000;
    display: block;
    min-width: 10rem;
    padding: 0.5rem 0;
    margin-top: 0.5rem;
    font-size: 1rem;
    color: #212529;
    text-align: left;
    list-style: none;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid rgba(0,0,0,.15);
    border-radius: 0.25rem;
    box-shadow: 0 0.5rem 1rem rgba(0,0,0,.175);
  }
  
  .dropdown-item {
    display: block;
    width: 100%;
    padding: 0.25rem 1.5rem;
    clear: both;
    font-weight: 400;
    color: #212529;
    text-align: inherit;
    white-space: nowrap;
    background-color: transparent;
    border: 0;
    cursor: pointer;
  }
  
  .dropdown-item:hover, .dropdown-item:focus {
    color: #16181b;
    text-decoration: none;
    background-color: #f8f9fa;
  }
  
  .custom-input {
    flex-grow: 1;
    border: none;
    padding: 8px 16px;
    font-size: 16px;
    outline: none;
  }
  
  .search-button {
    background-color: transparent;
    border: none;
    padding: 8px;
    cursor: pointer;
    margin-left: 8px;
  }
  
  .search-button:hover {
    background-color: rgba(0, 0, 0, 0.05);
    border-radius: 50%;
  }
  
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s ease;
  }
  
  .fade-enter-from, .fade-leave-to {
    opacity: 0;
  }
  
  @media (max-width: 480px) {
    .custom-dropdown-input {
      flex-direction: column;
      align-items: stretch;
    }
  
    .input-dropdown-container {
      flex-direction: column;
      border-radius: 12px;
    }
  
    .custom-button, .custom-input {
      width: 100%;
      border-radius: 0;
    }
  
    .search-button {
      margin-left: 0;
      margin-top: 8px;
      align-self: center;
    }
  
    .dropdown-menu {
      position: static;
      width: 100%;
      margin-top: 0;
      border-radius: 0;
    }
  }
  </style>
  