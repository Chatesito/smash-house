// Función para mostrar/ocultar contraseña
function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
    input.setAttribute('type', type);
    const icon = event.currentTarget.querySelector('i');
    icon.classList.toggle('fa-eye');
    icon.classList.toggle('fa-eye-slash');
}

// Validación del formulario
(function () {
    'use strict'
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }
            form.classList.add('was-validated')

            // Validar que las contraseñas coincidan
            const newPassword = form.querySelector('#newPassword').value;
            const confirmPassword = form.querySelector('#confirmPassword').value;
            if (newPassword !== confirmPassword) {
                event.preventDefault();
                alert('Las contraseñas no coinciden');
            }
        }, false)
    })
})()