<template>
    <div>
        <h1>Filmy wg gatunku</h1>
        <div v-for="(movies, genre) in moviesByGenre" :key="genre">
            <h2>{{ genre }}</h2>
            <ul>
                <li v-for="(movie, index) in movies" :key="movie.title">
                    {{ index + 1 }}. {{ movie.title }}
                </li>
            </ul>
            <button @click="showMore(genre)">Pokaż więcej</button>
        </div>
    </div>
</template>
  
<script>
import moviesData from '../assets/movies.json';

export default {
    name: 'ByGenreVue',
    data() {
        return {
            movies: moviesData,

        };
    },
    computed: {
        moviesByGenre() {
            const moviesByGenre = {};

            this.movies.forEach((movie) => {
                movie.genres.forEach((genre) => {
                    if (!moviesByGenre[genre]) {
                        moviesByGenre[genre] = [];
                    }
                    moviesByGenre[genre].push(movie);
                });
            });

            return moviesByGenre;
        },
    },

};
</script>