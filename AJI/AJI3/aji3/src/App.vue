<template>
  <SearchVue/>
  <MoviesTableVue/>
  <ByGenreVue/>
</template>

<script>
import SearchVue from './components/Search.vue';
import MoviesTableVue from './components/MoviesTable.vue';
import ByGenreVue from './components/ByGenre.vue';


export default {
  name: 'App',
  components: {
    SearchVue,
    MoviesTableVue,
    ByGenreVue,
}
}
</script>

