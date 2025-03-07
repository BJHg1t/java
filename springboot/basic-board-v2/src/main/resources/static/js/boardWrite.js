$(document).ready(() => {
    checkToken();
    setupAjax(); // 인증 받아야 함 > WebSecurityConfig 미기입
    getUserInfo().then((userInfo) => {
        $('#welcome-message').text(userInfo.userName + '님 환영합니다!')
    }).catch((error) => {
        console.error('Error get user info : ', error)
    });

    $('#submitBtn').on('click', (event) => {
        // form tag는 Controller로 제출 동작, ajax로 수행하려고 막음
        event.preventDefault();

        let formData = new FormData($('#writeForm')[0]);

        // for (let [key, value] of formData.entries()) {
        //     if (value instanceof File) {
        //         console.log('Key:', key);
        //         console.log('Name:', value.name);
        //         console.log('Size:', value.size);
        //         console.log('Type:', value.type);
        //     } else {
        //         console.log(key + ': ' + value);
        //     }
        // }
    })
});

/*
common.js > getUserInfo()
1. Promise 문법 : 개별적으로 기능을 구현할 수 있다
2. resolve : response를 then으로 보내 줌
 */