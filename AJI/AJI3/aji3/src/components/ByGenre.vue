<template>
    <div>
        <h1>Filmy wg gatunku</h1>
        <div v-for="(movies, genre) in moviesByGenre" :key="genre">
            <h2>{{ genre }}</h2>
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
    name: 'ByGenreVue',
    data() {
        return {
            movies: moviesData.slice(0, 100),
        };
    },
    computed: {
        moviesByGenre() {
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

            return moviesByGenre;
        },
    },
};
</script>
