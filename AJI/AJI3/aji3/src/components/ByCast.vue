<template>
    <div>
        <h1>Filmy wg obsady</h1>
        <div v-for="(movies, cast) in moviesByCast" :key="cast">
            <h2>{{ cast }}</h2>
            <ul style="list-style-type: decimal;">
                <li v-for="movie in movies" :key="movie.title">
                    {{ movie.title }}
                </li>
            </ul>
        </div>
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

        };
    },
    computed: {
        moviesByCast() {
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

            return moviesByCast;
        },
    },

};
</script>