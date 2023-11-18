<template>
    <div>
        <h1>Baza filmów</h1>
        <form @submit.prevent="search">
            <div class="form">
                <div class="form-title">
                    <label>Tytuł</label>
                    <div>
                        <input v-model="titleInput" type="text" placeholder="Podaj tytuł lub fragment tytułu filmu" />
                    </div>
                </div>
                <div>
                    <label>Rok produkcji od:</label>
                    <input v-model="fromYearInput" type="text" placeholder="Liczba naturalna z przedziału 1900-2023" />
                </div>
                <div>
                    <label>Rok produkcji do:</label>
                    <input v-model="toYearInput" type="text" placeholder="Liczba naturalna z przedziału 1900-2023" />
                </div>
                <div>
                    <label>Obsada</label>
                    <div>
                        <input v-model="castInput" type="text" placeholder="Imię i nazwisko" />
                    </div>
                </div>
                <div>
                   <button type="submit">Szukaj</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
import { useMoviesStore } from '@/stores/store';

export default {
    name: 'SearchVue',
    data() {
       return {
        titleInput: '',
        fromYearInput: '',
        toYearInput: '',
        castInput: '',
        filteredMovies: [],

       } 

    },
    methods: {
        search() {
      const title = this.titleInput;
      const fromYear = this.fromYearInput;
      const toYear = this.toYearInput;
      const cast = this.castInput;

      const moviesStore = useMoviesStore();
      moviesStore.filterMovies({ title, fromYear, toYear, cast });

      // Update the component's data property with filtered movies
      this.filteredMovies = moviesStore.filteredMovies;
    }
    }
}
</script>