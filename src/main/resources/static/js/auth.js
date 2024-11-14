document.addEventListener('DOMContentLoaded', function() {
    var contenedor_ingresar_registrarse = document.querySelector(".contenedor__ingresar-registrarse");
    var formulario_ingresar = document.querySelector(".formulario__ingresar");
    var formulario_registrarse = document.querySelector(".formulario__registrarse");
    var caja_trasera_ingresar = document.querySelector(".caja__trasera-ingresar");
    var caja_trasera_registrarse = document.querySelector(".caja__trasera-registrarse");

    // Variable para mantener el estado actual
    var formularioActual = 'ingresar';

    function anchopagina() {
        if (window.innerWidth > 850) {
            caja_trasera_ingresar.style.display = "block";
            caja_trasera_registrarse.style.display = "block";
        } else {
            caja_trasera_registrarse.style.display = "block";
            caja_trasera_registrarse.style.opacity = "1";
            caja_trasera_ingresar.style.display = "block";

            // Mostrar el formulario según el estado actual
            if (formularioActual === 'ingresar') {
                formulario_ingresar.style.display = "block";
                formulario_registrarse.style.display = "none";
            } else {
                formulario_ingresar.style.display = "none";
                formulario_registrarse.style.display = "block";
            }

            contenedor_ingresar_registrarse.style.left = "0px";
        }
    }

    function ingresar() {
        formularioActual = 'ingresar';
        if (window.innerWidth > 850) {
            formulario_registrarse.style.display = "none";
            contenedor_ingresar_registrarse.style.left = "10px";
            formulario_ingresar.style.display = "block";
            caja_trasera_registrarse.style.opacity = "1";
            caja_trasera_ingresar.style.opacity = "0";
        } else {
            formulario_ingresar.style.display = "block";
            formulario_registrarse.style.display = "none";
            caja_trasera_registrarse.style.display = "block";
            caja_trasera_ingresar.style.display = "block";
            contenedor_ingresar_registrarse.style.left = "0";
        }
    }

    function registrarse() {
        formularioActual = 'registrar';
        if (window.innerWidth > 850) {
            formulario_registrarse.style.display = "block";
            contenedor_ingresar_registrarse.style.left = "410px";
            formulario_ingresar.style.display = "none";
            caja_trasera_registrarse.style.opacity = "0";
            caja_trasera_ingresar.style.opacity = "1";
        } else {
            formulario_registrarse.style.display = "block";
            formulario_ingresar.style.display = "none";
            caja_trasera_registrarse.style.display = "block";
            caja_trasera_ingresar.style.display = "block";
            contenedor_ingresar_registrarse.style.left = "0";
        }
    }


    // Event Listeners
    document.getElementById("btn__ingresar").addEventListener("click", function(e) {
        e.preventDefault();
        ingresar();
    });

    document.getElementById("btn__registrarse").addEventListener("click", function(e) {
        e.preventDefault();
        registrarse();
    });

    window.addEventListener("resize", anchopagina);

    // Al Inicializar
    anchopagina();
});