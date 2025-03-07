let checkToken = () => {
    let token = localStorage.getItem('accessToken');
    if (token == null || token.trim() === '') {
        window.location.href = '/member/login'
    }
}

let setupAjax = () => {
    $.ajaxSetup({
        beforeSend: (xhr) => {
            let token = localStorage.getItem('accessToken');
            if (token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            }
        }
    })
}

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
    })
}