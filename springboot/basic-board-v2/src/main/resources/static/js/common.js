let checkToken = () => {
    let token = localStorage.getItem('accessToken');
    if (token == null || token.trim() === '') {
        window.location.href = '/member/login'
    }
}

let setupAjax = () => {
    // 모든 Ajax 요청에 JWT Access Token을 포함
    $.ajaxSetup({
        beforeSend: (xhr) => {
            let token = localStorage.getItem('accessToken');
            if (token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            }
        }
    })
}

// 토큰을 분해해서 얻은 정보
let getUserInfo = () => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'GET',
            url: '/user/info',
            success: (response) => {
                resolve(response)
            },
            error: (xhr) => {
                reject(xhr)
            }
        })
    });
}