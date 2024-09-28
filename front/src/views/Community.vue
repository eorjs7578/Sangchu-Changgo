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
        <button class="search-button" @click="search">
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


              <a @click="goToPostList(item.id)">
              {{ item.name }}
            </a>
              <span v-if="item.count" >| 게시물 수 {{ item.post_count }}</span>
            
            </li>
          </ul>
          <div class="pagination">
          <button @click="changeGroup(category, category.currentGroup - 1)" :disabled="category.currentGroup === 0">이전 그룹</button>
          <button v-for="n in groupPageArray(category)" :key="n" @click="changePage(category, n - 1)">
            {{ n }}
          </button>
          <button @click="changeGroup(category, category.currentGroup + 1)" :disabled="category.currentGroup >= Math.ceil(category.totalPages / category.groupSize) - 1">다음 그룹</button>
        </div>
        </div>
      </div>
      <div class="category-container">
        
              <div v-for="board in hotBoard" :key="board.name" class="category">
                <h2>{{ board.name }}</h2>
                <ul>
                  <li v-for="item in board.items" :key="item.title">
                    <a @click="goToPostList(item.id)">
                          {{ item.name }}
                    </a>
                    <span v-if="item.post_count" class="count">| 게시물 수 : {{ item.post_count }}</span>
                  </li>
                </ul>
              </div>
  
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref ,onMounted} from 'vue'
  import {
    getAll,
    getFranchise,
    getArea,
    getHotArea,
    getHotFranchise,
    getALLArea,
    getALLFranchise
} from "@/api/communitySearch.js";
import { useRouter } from 'vue-router'; // Vue Router import
  
  let categories = ref([])
  
  let hotBoard = ref([]);
  const router = useRouter(); // Vue Router 사용

  const searchQuery = ref('')
  
  const options = ['전체', '상권', '프랜차이즈']
  const selectedOption = ref('전체')
  const isOpen = ref(false)
  const inputValue = ref("");

// 페이지 정보를 포함하여 카테고리 초기화
const initCategory = () => ({
  name: "",
  items: [],
  currentPage: 0,
  totalPages: 0,
  size: 10,
  currentGroup: 0, // 현재 페이지 그룹
  groupSize: 10 // 한 번에 보여줄 페이지 수
});

const goToPostList = (boardId) => {
  console.log("boardId : ", boardId);
  router.push({ 
    name: 'PostList', // 라우터 이름으로 이동
    params: { boardId } // boardId를 params로 전달
  });
};

onMounted(() => {
  getHotData(); // 인기 데이터 불러오기
  getAllAreaData(); // 모든 상권 데이터 불러오기
  getAllFranchiseData(); // 모든 프랜차이즈 데이터 불러오기
});

// 인기 데이터 불러오기
async function getHotData() {
  try {
    const hotAreaData = await getHotArea();
    hotBoard.value.push({ name: "인기 상권", items: hotAreaData });

    const hotFranchiseData = await getHotFranchise();
    hotBoard.value.push({ name: "인기 프랜차이즈", items: hotFranchiseData });
  } catch (error) {
    console.error("Error:", error);
  }
}

// 상권 게시판 데이터 불러오기
async function getAllAreaData() {
  const newCategory = initCategory();
  newCategory.name = "상권 게시판";
  await getALLArea(newCategory.currentPage, newCategory.size)
    .then(data => {
      newCategory.items = data.content;
      newCategory.totalPages = data.total_pages;
      categories.value.push(newCategory);
    })
    .catch(error => {
      console.error("Error:", error);
    });
}

// 프랜차이즈 게시판 데이터 불러오기
async function getAllFranchiseData() {
  const newCategory = initCategory();
  newCategory.name = "프랜차이즈 게시판";
  await getALLFranchise(newCategory.currentPage, newCategory.size)
    .then(data => {
      newCategory.items = data.content;
      newCategory.totalPages = data.total_pages;
      categories.value.push(newCategory);
    })
    .catch(error => {
      console.error("Error:", error);
    });
}

  const toggleDropdown = () => {
    isOpen.value = !isOpen.value
  }
  
  const selectOption = (option) => {
    selectedOption.value = option
    isOpen.value = false
  }
  
  const search = async () => {
    categories.value =[];
    if(selectedOption.value=='전체'){
      searchArea();
      searchFranchise();
    }
    else if(selectedOption.value=='상권'){
      searchArea();
    }else{
      searchFranchise();
    }
  }

// 상권 검색
async function searchArea() {
  const newCategory = initCategory();
  newCategory.name = "상권 게시판";
  await getArea(newCategory.currentPage, newCategory.size, inputValue.value)
    .then(data => {
      newCategory.items = data.content;
      newCategory.totalPages = data.total_pages;
      categories.value.push(newCategory);
    })
    .catch(error => {
      console.error("Error:", error);
    });
}

// 프랜차이즈 검색
async function searchFranchise() {
  const newCategory = initCategory();
  newCategory.name = "프랜차이즈 게시판";
  await getFranchise(newCategory.currentPage, newCategory.size, inputValue.value)
    .then(data => {
      newCategory.items = data.content;
      newCategory.totalPages = data.total_pages;
      categories.value.push(newCategory);
    })
    .catch(error => {
      console.error("Error:", error);
    });
}

// 페이지 변경 시 호출할 함수
const changePage = async (category, newPage) => {
  if (newPage < 0 || newPage >= category.totalPages) return; // 범위 초과 시 무시
  category.currentPage = newPage;

  if (category.name === "상권 게시판") {
    await getArea(category.currentPage, category.size, inputValue.value)
      .then(data => {
        category.items = data.content;
      })
      .catch(error => {
        console.error("Error:", error);
      });
  } else if (category.name === "프랜차이즈 게시판") {
    await getFranchise(category.currentPage, category.size, inputValue.value)
      .then(data => {
        category.items = data.content;
      })
      .catch(error => {
        console.error("Error:", error);
      });
  }
}


// 페이지 그룹 변경 시 호출할 함수
const changeGroup = (category, newGroup) => {
  if (newGroup < 0 || newGroup >= Math.ceil(category.totalPages / category.groupSize)) return;
  category.currentGroup = newGroup;
}

// 현재 카테고리의 그룹에 속하는 페이지 번호 배열
const groupPageArray = (category) => {
  const startPage = category.currentGroup * category.groupSize;
  const endPage = Math.min(startPage + category.groupSize, category.totalPages);
  return Array.from({ length: endPage - startPage }, (_, i) => startPage + i + 1);
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
  