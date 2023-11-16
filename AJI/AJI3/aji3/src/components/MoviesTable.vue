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
      <button @click="showMore" v-if="displayedMovies.length < moviesData.length">Pokaż więcej</button>
    </div>
  </template>
  
  <script>
  import moviesData from '../assets/movies.json';
  
  export default {
    name: 'MoviesTableVue',
    data() {
      return {
        moviesData,
        displayedMovies: [],
        itemsPerPage: 10,
        currentIndex: 0
      };
    },
    mounted() {
      this.showMore();
    },
    methods: {
      showMore() {
        const nextIndex = this.currentIndex + this.itemsPerPage;
        this.displayedMovies = this.moviesData.slice(0, nextIndex);
        this.currentIndex = nextIndex;
      }
    }
  };
  </script>