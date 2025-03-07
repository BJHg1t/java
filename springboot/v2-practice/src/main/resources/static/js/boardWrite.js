let selectedFile = null;

$(document).ready(() => {
    checkToken();
    setupAjax();
    getUserInfo().then((userInfo) => {
        console.log('user info :: ', userInfo)
        $('#hiddenUserName').val(userInfo.userName);
        $('#hiddenUserId').val(userInfo.userId);
        $('#userId').val(userInfo.userId);
    }).catch((error) => {
        console.error('Error get user info : ', error)
    });

    $('#file').on('change', (event) => {
        selectedFile = event.target.files[0];
        updateFileList();
    });

    $('#submitBtn').on('click', (event) => {

        event.preventDefault();

        let formData = new FormData($('#writeForm')[0]);

        $.ajax({
            type: 'POST',
            url: '/api/board',
            data: formData,
            contentType: false,
            processData: false,
            success: (response) => {
                alert('게시글이 성공적으로 등록되었습니다!')
                window.location.href = '/'
            },
            error: (error) => {
                console.log('오류발생 : ', error)
                alert('게시글 등록 중 오류가 발생했습니다.')
            }
        });

    });

});

let updateFileList = () => {
    $('#fileList').empty();

    if (selectedFile) {
        $('#fileList').append(`
                    <li>
                        ${selectedFile.name} <button type="button" class="remove-btn">X</button>
                    </li>
                `);

        $('.remove-btn').on('click', function() {
            selectedFile = null;
            $('#file').val('');
            updateFileList();
        });
    }
}