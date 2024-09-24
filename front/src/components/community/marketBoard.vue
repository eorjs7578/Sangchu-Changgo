<script setup lang="ts">
import { ref } from 'vue'
import { Search, ThumbsUp } from 'lucide-vue-next'

const searchQuery = ref('')
const popularPosts = ref([
  { id: 1, title: 'Gangnam District (Gathering of Gangnam Business Owners)', author: 'user1', likes: 24, totalLikes: 50 },
  { id: 2, title: 'Yeongdong District (Gathering of Yeongdong Business Owners)', author: 'user2', likes: 24, totalLikes: 50 },
])
</script>

<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Community</h1>
    
    <div class="bg-gray-100 p-4 rounded-lg mb-6">
      <h2 class="text-xl font-semibold mb-2">Popular Posts</h2>
      <div class="relative">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search posts..."
          class="w-full p-2 pr-10 rounded border border-gray-300"
        />
        <Search class="absolute right-3 top-2.5 text-gray-400" />
      </div>
      <button class="mt-2 px-4 py-1 bg-blue-500 text-white rounded">Search by region</button>
    </div>

    <div class="bg-gray-200 p-4 rounded-lg mb-6">
      <div class="bg-gray-300 p-2 rounded mb-2">Gangnam District</div>
      <div class="bg-gray-300 p-2 rounded">Gangbuk District</div>
    </div>

    <h2 class="text-xl font-semibold mb-4">Popular Posts</h2>
    <p class="mb-4">You can check the most popular posts today.</p>

    <div class="space-y-4">
      <div v-for="post in popularPosts" :key="post.id" class="border p-4 rounded-lg">
        <h3 class="text-lg font-semibold mb-2">{{ post.title }}</h3>
        <p class="text-gray-600 mb-2">{{ post.author }}, welcome to the Gangnam business community! :)</p>
        <div class="flex items-center text-gray-500">
          <ThumbsUp class="mr-1" />
          <span>{{ post.likes }} / {{ post.totalLikes }}</span>
        </div>
      </div>
    </div>

    <h2 class="text-xl font-semibold mt-6 mb-4">Recent Posts</h2>
    <p class="mb-4">You can check the most recent posts today.</p>

    <div class="bg-gray-100 p-4 rounded-lg">
      <h3 class="text-lg font-semibold mb-2">To business owners in Gangnam...</h3>
      <p class="text-gray-600 mb-2">
        Hello, business owners! I'm considering opening a business in Gangnam. 
        Is there anything I should be particularly careful about? 
        I've heard that the competition is fierce...
      </p>
      <div class="text-gray-500">Business Inquiry</div>
    </div>
  </div>
</template>