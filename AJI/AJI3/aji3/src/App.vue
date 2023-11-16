<template>
  <SearchVue/>
  <MoviesTableVue/>
</template>

<script>
import SearchVue from './components/Search.vue';
import MoviesTableVue from './components/MoviesTable.vue';


export default {
  name: 'App',
  components: {
    SearchVue,
    MoviesTableVue,
  }
}
</script>

<!-- <style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style> -->
