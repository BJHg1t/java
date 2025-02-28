$(document).ready(() => {

    $('#signin').click(() => {
        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            userId : userId,
            password : password
        }

        $.ajax({
            type : 'POST',
            url : '/login'
        });

    });

});