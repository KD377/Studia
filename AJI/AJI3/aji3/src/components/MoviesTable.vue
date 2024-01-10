<template>
  <div class="container">
    <table class="table table-condensed table-hover">
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
          <td>{{ deleteBrackets(movie.cast) }}</td>
          <td>{{ deleteBrackets(movie.genres) }}</td>
        </tr>
      </tbody>
    </table>
    <button @click="showMore" v-if="this.currentIndex + this.itemsPerPage < this.filteredMovies.length"
      class="btn btn-primary">Pokaż wiecej</button>
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
    },
    deleteBrackets(kategory) {
      return kategory.map(item => item.replace(/^\[|\]$/g, '')).join(', ');
    }
  }
};
</script>