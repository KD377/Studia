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

