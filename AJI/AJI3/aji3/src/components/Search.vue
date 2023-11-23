<template>
    <div class="container">
        <h1>Baza filmów</h1>
        <form @submit.prevent="search">
            <div class="form">
                <div class="mb-3">
                    <label>Tytuł</label>
                    <div>
                        <input class="form-control" v-model="titleInput" type="text"
                            placeholder="Podaj tytuł lub fragment tytułu filmu" />
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-4 col-form-label"><label>Rok produkcji od:</label></div>
                    <div class="col-sm-8"><input class="form-control" v-model="fromYearInput" type="text"
                            placeholder="Liczba naturalna z przedziału 1900-2023" /></div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-4 col-form-label"><label>Rok produkcji do:</label></div>
                    <div class="col-sm-8"><input class="form-control" v-model="toYearInput" type="text"
                            placeholder="Liczba naturalna z przedziału 1900-2023" /></div>
                </div>
                <div class="mb-3">
                    <label>Obsada</label>
                    <div>
                        <input class="form-control" v-model="castInput" type="text" placeholder="Imię i nazwisko" />
                    </div>
                </div>
                <div class="mb-3">
                    <button class="btn btn-primary col-sm-12" type="submit">Szukaj</button>
                </div>
            </div>
        </form>
        <h4 v-if="filteredItems > 0">Znalezione wyniki: {{ filteredItems }}</h4>
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
            filteredItems: 0,
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
            if (title == '' && fromYear == '' && toYear == '' && cast == '') {
                this.filteredItems = 0;
            } else {
                this.filteredItems = moviesStore.filteredItems;
            }
        }
    }
}
</script>