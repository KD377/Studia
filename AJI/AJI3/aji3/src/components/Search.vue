<template>
    <div>
        <h1>Baza filmów</h1>
        <form>
            <div class="form">
                <div class="form-title">
                    <label>Tytuł</label>
                    <div>
                        <input type="text" placeholder="Podaj tytuł lub fragment tytułu filmu" />
                    </div>
                </div>
                <div>
                    <label>Rok produkcji od:</label>
                    <input type="text" placeholder="Liczba naturalna z przedziału 1900-2023" />
                </div>
                <div>
                    <label>Rok produkcji do:</label>
                    <input type="text" placeholder="Liczba naturalna z przedziału 1900-2023" />
                </div>
                <div>
                    <label>Obsada</label>
                    <div>
                        <input type="text" placeholder="Imię i nazwisko" />
                    </div>
                </div>
                <div>
                    <input type="button" value="Szukaj">
                </div>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: 'SearchVue',
    data() {
        return {
            // text: "Baza filmów"
        }
    }
}
</script>