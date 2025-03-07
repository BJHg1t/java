$(document).ready(() => {
    checkToken();
    setupAjax();
    getUserInfo().then((userInfo) => {
        $('#welcome-message').text(userInfo.userName + '님 환영합니다.')
        $('#hiddenUserId').val(userInfo.userId);
        $('#hiddenUserName').val(userInfo.userName);
    }).catch((error) => {
        console.log('board list user info error : ', error)
    });
    getBoards();
});

let getBoards = () => {
    let currentPage = 1;
    const pageSize = 10;

    loadBoard(currentPage, pageSize);

    $('#nextPage').on('click', () => {
        currentPage++;
        loadBoard(currentPage, pageSize)
    });
    $('#prevPage').on('click', () => {
        if (currentPage > 1) {
            currentPage--;
            loadBoard(currentPage, pageSize)
        }
    });
}

let loadBoard = (page, size) => {
    $.ajax({
        type: 'GET',
        url: 'api/board',
        data: {
            page: page,
            size: size
        },
        success: (response) => {
            console.log('loadBoard : ', response);
            $('#boardContent').empty();
            if (response.articles.length <= 0) {
                $('#boardContent').append(
                    `<tr>
                        <td colspan="4" style="text-align: center;">글이 존재하지 않습니다.</td>
                    </tr>`
                );
            } else {
                response.articles.forEach((article) => {
                    $('#boardContent').append(
                        `
                            <tr>
                                <td>${article.id}</td>
                                <td><a href="/detail?id=${article.id}">${article.title}</a></td>
                                <td>${article.userId}</td>
                                <td>${article.created}</td>
                            </tr>
                    `
                    );
                });
            }

            $('#pageInfo').text(page);
            $('#prevPage').prop('disabled', page === 1);
            $('#nextPage').prop('disabled', response.last);
        },
        error: (error) => {
            console.error('board list error :: ', error)
        }
    })
}

let logout = () => {
    $.ajax({
        type: 'POST',
        url: '/logout',
        success: (response) => {
            alert('로그아웃이 성공했습니다.');
            localStorage.removeItem("accessToken")
            window.location.href = '/member/login'
        },
        error: (error) => {
            console.log('오류발생 : ', error);
            alert('로그아웃 중 오류가 발생했습니다.');
        }
    });
}