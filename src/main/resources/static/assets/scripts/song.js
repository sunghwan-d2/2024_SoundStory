document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.addPlaylist').forEach(button => {
        button.addEventListener('click', e => {
            e.preventDefault();
            if (!confirm('플레이리스트에 추가하시겠습니까?')) return;

            const songId = button.getAttribute('data-song-id');
            const xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        const responseObject = JSON.parse(xhr.responseText);
                        console.log(responseObject); // 응답 디버깅용 로그
                        switch (responseObject['result']) {
                            case 'failure':
                                alert('추가 중 오류가 발생하였습니다.');
                                break;
                            case 'success':
                                alert('플레이리스트에 노래가 추가되었습니다.');
                                location.href = "/";
                                break;
                            default:
                                alert('서버가 알 수 없는 응답을 반환하였습니다.');
                        }
                    } else {
                        alert('중복된 노래 삽입은 불가능 합니다.');
                    }
                }
            };
            xhr.open('POST', `/album/addPlaylist?songId=${songId}`, true);
            xhr.send();
        });
    });
});
