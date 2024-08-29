function showEditForm() {
    document.getElementById('editForm').style.display = 'block';
} //수정폼


const deleteAnchor = document.getElementById('deleteAnchor');
if (deleteAnchor) {
    deleteAnchor.onclick = e => {
        e.preventDefault();
        if (!confirm('정말로 삭제하시겠습니까?')) return;

        const artistId = deleteAnchor.dataset['artistId'];
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
                            alert('삭제되었습니다.');
                            location.href = '../';
                            break;
                        default:
                            alert('서버가 알 수 없는 응답을 반환하였습니다.');
                    }
                } else {
                    alert('요청을 전송하는 도중 오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            }
        };

        xhr.open('DELETE', `/artist/delete?artistId=${artistId}`);  // DELETE 요청 설정
        xhr.send();
    };
}


document.addEventListener("DOMContentLoaded", function () {
    const commentForm = document.forms['commentForm'];
    if (commentForm) {
        const content = commentForm['content'];
        const userLoggedIn = commentForm.dataset.user === "true";

        content.onclick = function () {
            if (!userLoggedIn) {
                alert("로그인 후 작성해 주세요.");
            } else {
                alert("과도한 비방과 욕설은 삭제될 수 있습니다.");
            }
        };

        commentForm.onsubmit = function (e) {
            const trimmedContent = content.value.trim();

            if (!userLoggedIn) {
                e.preventDefault();
                alert("로그인 후 작성해 주세요.");
            } else if (trimmedContent === '') {
                alert('댓글을 작성해 주세요.');
                content.focus();
                e.preventDefault();
            } else if (trimmedContent.length < 1 || trimmedContent.length > 10000) {
                alert('댓글 길이는 1자 이상 10000자 이하로 입력해 주세요.');
                content.focus();
                e.preventDefault();
            }
        };
    }
});





const deleteCommentButtons = document.querySelectorAll('.deleteButton');

deleteCommentButtons.forEach(deleteCommentButton => {
    deleteCommentButton.addEventListener('click', () => {
        if (!confirm('정말 댓글을 삭제할까요?')) {
            return;
        }

        const indexElement = deleteCommentButton.closest('tr').querySelector('.index');
        const index = indexElement.textContent.trim();

        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseObject = JSON.parse(xhr.responseText);
                    switch (responseObject.result) {
                        case 'success':
                            location.reload();
                            break;
                        case 'failure':
                            alert('본인의 댓글만 삭제가 가능합니다. 다시 시도해 주세요.');
                            break;
                        default:
                            alert('오류가 발생하였습니다. 잠시 후 다시 시도해 주세요.');
                    }
                } else {
                    alert('로그인 후 이용해 주세요.');
                }
            }
        };

        xhr.open('DELETE', `/comment/?index=${index}`);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send();
    });
});



