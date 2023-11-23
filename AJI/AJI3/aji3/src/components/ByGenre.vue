<template>
    <div>
        <h1>Filmy wg gatunku</h1>
        <template v-if="!isSorting">
            <button @click="toggleSorting">Sortuj A-Z</button>
        </template>
        <template v-else>
            <button @click="toggleSorting">Wyłącz sortowanie</button>
        </template>


        <template v-if="!isSorting">
            <div v-for="(movies, genre) in moviesByGenre" :key="genre">
                <h2>{{ genre }}</h2>
                <ul style="list-style-type: decimal;">
                    <li v-for="movie in movies" :key="movie.title">
                        {{ movie.title }}
                    </li>
                </ul>
            </div>
        </template>

        <template v-else>
            <div v-for="(movies, genre) in sortedMovies" :key="genre">
                <h2>{{ genre }}</h2>
                <ul style="list-style-type: decimal;">
                    <li v-for="movie in movies" :key="movie.title">
                        {{ movie.title }}
                    </li>
                </ul>
            </div>
        </template>
    </div>
</template>
  
  
<script>
import moviesData from '../assets/movies.json';
import _ from 'underscore';

export default {
    name: 'ByGenreVue',
    data() {
        return {
            movies: moviesData.slice(0, 100),
            moviesByGenre: {},
            sortedMovies: {},
            isSorting: false,
        };
    },
    created() {
        this.updateMovies();
    },
    methods: {
        updateMovies() {
            const moviesByGenre = {};

            _.each(this.movies, (movie) => {
                if (movie.genres && movie.genres.length > 0) {
                    _.each(movie.genres, (genre) => {
                        moviesByGenre[genre] = _.union(moviesByGenre[genre] || [], [movie]);
                    });
                } else {
                    const defaultGenre = 'Brak gatunku';
                    moviesByGenre[defaultGenre] = _.union(moviesByGenre[defaultGenre] || [], [movie]);
                }
            });

            this.moviesByGenre = moviesByGenre;
        },
        sortMovies() {

            _.each(this.moviesByGenre, (movies, genre) => {
                this.moviesByGenre[genre] = _.sortBy(movies, (movie) => movie.title.toLowerCase());
            });
            this.sortedMovies = _.clone(this.moviesByGenre);
        },
        toggleSorting() {
            this.isSorting = !this.isSorting;

            if (this.isSorting) {
                this.sortMovies();
            } else {
                this.updateMovies();
            }
        },
    },
};
</script>
  