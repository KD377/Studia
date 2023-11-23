<template>
  <SearchVue/>
  <MoviesTableVue/>
  <ByGenreVue/>
  <ByCastVue/>
</template>

<script>
import SearchVue from './components/Search.vue';
import MoviesTableVue from './components/MoviesTable.vue';
import ByGenreVue from './components/ByGenre.vue';
import ByCastVue from './components/ByCast.vue';

export default {
  name: 'App',
  components: {
    SearchVue,
    MoviesTableVue,
    ByGenreVue,
    ByCastVue,
}
}
</script>

