import { defineStore } from "pinia";
import _ from 'underscore'; // Import Underscore.js library

export const useMoviesStore = defineStore('movies', {
  state: () => ({
    moviesData: [],
    filteredMovies: [],
    filteredItems: 0,
  }),
  actions: {
    setMoviesData(data) {
      this.moviesData = data;
      this.filteredMovies = data; // Initialize filteredMovies with all movies
    },
    filterMovies({ title, fromYear, toYear, cast }) {
      let filtered = _.clone(this.moviesData); // Cloning to avoid mutating the original data

      if (title) {
        filtered = _.filter(filtered, movie =>
          movie.title.toLowerCase().includes(title.toLowerCase())
        );
      }

      if (fromYear) {
        filtered = _.filter(filtered, movie =>
          movie.year >= fromYear
        );
      }

      if (toYear) {
        filtered = _.filter(filtered, movie =>
          movie.year <= toYear
        );
      }

      if (cast) {
        filtered = _.filter(filtered, movie =>
          _.some(movie.cast, actor => actor.toLowerCase().includes(cast.toLowerCase()))
        );
      }

      this.filteredMovies = filtered;
      this.filteredItems = filtered.length;
    },
  },
});