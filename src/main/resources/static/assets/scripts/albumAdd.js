const AlbumAdd = document.getElementById('albumAdd');

AlbumAdd.form = AlbumAdd.querySelector('.form');
AlbumAdd.form.thumbnailLabel = new LabelObj(AlbumAdd.querySelector('[rel="thumbnailLabel"]'));
AlbumAdd.form.nameLabel = new LabelObj(AlbumAdd.querySelector('[rel="nameLabel"]'));
AlbumAdd.form.artistLabel = new LabelObj(AlbumAdd.querySelector('[rel="artistLabel"]'));
AlbumAdd.form.titleLabel = new LabelObj(AlbumAdd.querySelector('[rel="titleLabel"]'));


AlbumAdd.form['thumbnail'].onchange = () => {
    const imageEl = AlbumAdd.form.thumbnailLabel.element.querySelector('.image');
    const emptyEl = AlbumAdd.form.thumbnailLabel.element.querySelector('.empty');
    if (AlbumAdd.form['thumbnail'].files.length === 0) {
        imageEl.style.display = 'none';
        emptyEl.style.display = 'flex';
        return;
    }
    const fileReader = new FileReader();
    fileReader.onload = () => {
        imageEl.setAttribute('src', fileReader.result);
        imageEl.style.display = 'block';
        emptyEl.style.display = 'none';
    };
    fileReader.readAsDataURL(AlbumAdd.form['thumbnail'].files[0]);
}

const addAnchor = document.getElementById('addAnchor');

AlbumAdd.form.onsubmit = (e) => {
    e.preventDefault();
    if (!confirm('앨범을 등록 하시겠습니까?')) return;

    const formData = new FormData();
    formData.append('_thumbnail', AlbumAdd.form['thumbnail'].files[0]);
    // formData.append('name', AlbumAdd.form['name'].value);
    formData.append('artist', AlbumAdd.form['artist'].value);
    formData.append('title', AlbumAdd.form['title'].value);
    formData.append('artistId', AlbumAdd.form['artistId'].value);

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status < 200 || xhr.status >= 300) {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
            return;
        }

        const response = JSON.parse(xhr.responseText);
        if (response.result === 'success') {
            alert('앨범이 성공적으로 등록되었습니다.');
            const albumId = response.albumId;
            location.href = `/album?albumId=${albumId}`;
        } else {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
        }
    };

    xhr.open('POST', '/album/add');
    xhr.send(formData);
};
// main

