import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import { useMoviesStore } from '@/stores/store' // Import the store
import moviesData from './assets/movies.json' // Import your movies data
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

const pinia = createPinia()
const app = createApp(App)
app.use(pinia)

const moviesStore = useMoviesStore() // Get the store instance
moviesStore.setMoviesData(moviesData) // Set movies data in the store

app.mount('#app')