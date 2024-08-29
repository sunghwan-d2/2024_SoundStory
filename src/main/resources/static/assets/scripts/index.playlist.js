document.querySelectorAll('.deletePlaylist').forEach(button => {
    button.addEventListener('click', e => {
        e.preventDefault();
        if (!confirm('노래를 정말로 삭제하겠습니까?')) return;
        const songId = button.getAttribute('data-song-id');
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseObject = JSON.parse(xhr.responseText);
                    switch (responseObject['result']) {
                        case 'failure':
                            alert('삭제 중 오류가 발생하였습니다.');
                            break;
                        case 'success':
                            alert('노래가 삭제되었습니다.');
                            location.reload();
                            break;
                        default:
                            alert('서버가 알 수 없는 응답을 반환하였습니다.');
                    }
                } else {
                    alert('요청을 전송하는 도중 오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            }
        };

        xhr.open('DELETE', `/playlist/delete?songId=${songId}`);
        xhr.send();
    });
});
