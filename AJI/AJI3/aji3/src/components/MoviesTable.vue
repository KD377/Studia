<template>
    <div>
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Production Year</th>
            <th>Cast</th>
            <th>Genres</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(movie, index) in displayedMovies" :key="index">
            <td>{{ movie.title }}</td>
            <td>{{ movie.year }}</td>
            <td>{{ movie.cast }}</td>
            <td>{{ movie.genres }}</td>
          </tr>
        </tbody>
      </table>
      <button @click="showMore" v-if="this.currentIndex + this.itemsPerPage < this.filteredMovies.length">Pokaż wiecej</button>
    </div>
  </template>
  
  <script>
  import { useMoviesStore } from '@/stores/store';
  
  export default {
    name: 'MoviesTableVue',
    data() {
      return {
        itemsPerPage: 10,
        currentIndex: 0,
      }
    },
    computed: {
      moviesStore() {
        return useMoviesStore();
      },
      filteredMovies() {
        return this.moviesStore.filteredMovies;
      },
      displayedMovies() {
        const nextIndex = this.currentIndex + this.itemsPerPage;
        return this.filteredMovies.slice(0, nextIndex)

      },
    },
    methods: {
      showMore() {
        this.currentIndex = this.currentIndex + 10;
      }
    }
  };
  </script>