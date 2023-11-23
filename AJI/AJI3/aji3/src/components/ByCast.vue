<template>
    <div>
        <h1>Filmy wg obsady</h1>
        <template v-if="!isSorting">
            <button @click="toggleSorting">Sortuj A-Z</button>
        </template>
        <template v-else>
            <button @click="toggleSorting">Wyłącz sortowanie</button>
        </template>


        <template v-if="!isSorting">
            <div v-for="(movies, cast) in moviesByCast" :key="cast">
                <h2>{{ cast }}</h2>
                <ul style="list-style-type: decimal;">
                    <li v-for="movie in movies" :key="movie.title">
                        {{ movie.title }}
                    </li>
                </ul>
            </div>
        </template>

        <template v-else>
            <div v-for="(movies, cast) in sortedMovies" :key="cast">
                <h2>{{ cast }}</h2>
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
    name: 'ByCastVue',
    data() {
        return {
            movies: moviesData.slice(199, 300),
            moviesByCast: {},
            sortedMovies: {},
            isSorting: false,
        };
    },

    created() {
        this.updateMovies();
    },

    methods: {
        updateMovies() {
            const moviesByCast = {};

            _.each(this.movies, (movie) => {
                if (movie.cast && movie.cast.length > 0) {
                    _.each(movie.cast, (cast) => {
                        moviesByCast[cast] = _.union(moviesByCast[cast] || [], [movie]);
                    });
                } else {
                    const defaultCast = 'Brak obsady';
                    moviesByCast[defaultCast] = _.union(moviesByCast[defaultCast] || [], [movie]);
                }
            });

            this.moviesByCast = moviesByCast;
        },
        sortMovies() {

            _.each(this.moviesByCast, (movies, cast) => {
                this.moviesByCast[cast] = _.sortBy(movies, (movie) => movie.title.toLowerCase());
            });
            this.sortedMovies = _.clone(this.moviesByCast);
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