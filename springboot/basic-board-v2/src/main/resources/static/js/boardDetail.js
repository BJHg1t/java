$(document).ready(() => {
    checkToken();
    setupAjax();
    getUserInfo().then((userInfo) => {
        $('#hiddenUserId').val(userInfo.userId);
        $('#hiddenUserName').val(userInfo.userName);
        loadBoardDetail();
    }).catch((error) => {
        console.error('board edit user info error : ', error)
    });

});

let loadBoardDetail = () => {
    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();

    $.ajax({
        type: 'GET',
        url: '/api/board/' + hId,
        success: (response) => {
            console.log('loadBoard detail : ', response);
            $('#title').text(response.title);
            $('#content').text(response.content);
            $('#userId').text(response.userId);
            $('#created').text(response.created);

            if (hUserId !== response.userId){
                $('#editBtn').prop('disabled', true);
                $('#deleteBtn').prop('disabled', true);
            }

            if (response.filePath && response.filePath.length > 0) {
                let filePath = response.filePath;
                $('#hiddenFilePath').val(filePath);
                let fileName = filePath.substring(filePath.lastIndexOf('\\') + 1); // 파일명 추출

                let fileElement = `
                            <li>
                                <!-- 다운로드 링크
                                 작동 방식이 ajax가 아닌 <a>라서 헤더에 토큰 없기 때문에 security 정책적으로 허용시켜야 함 --> 
                                <a href="/api/board/file/download/${fileName}">${fileName}</a>
                            </li>`;
                $('#fileList').append(fileElement);
            } else {
                $('#fileList').append('<li>첨부된 파일이 없습니다.</li>');
            }
        },
        error: (error) => {
            console.error('board detail error :: ', error)
        }
    })
}