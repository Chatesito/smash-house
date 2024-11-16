document.addEventListener('DOMContentLoaded', function() {
    const tournamentsPlayedInput = document.getElementById('tournamentsPlayed');
    const tournamentsWonInput = document.getElementById('tournamentsWon');

    tournamentsPlayedInput.addEventListener('change', function() {
        const maxWins = this.value;
        tournamentsWonInput.max = maxWins;

        // Si el valor actual es mayor que el máximo permitido, ajustarlo
        if (parseInt(tournamentsWonInput.value) > parseInt(maxWins)) {
            tournamentsWonInput.value = maxWins;
        }
    });
});