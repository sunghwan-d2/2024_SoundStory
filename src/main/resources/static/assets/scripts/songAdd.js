const SongAdd = document.getElementById('songAdd');

SongAdd.form = SongAdd.querySelector('.form');
SongAdd.form.thumbnailLabel = new LabelObj(SongAdd.querySelector('[rel="thumbnailLabel"]'));
SongAdd.form.nameLabel = new LabelObj(SongAdd.querySelector('[rel="nameLabel"]'));
SongAdd.form.albumLabel = new LabelObj(SongAdd.querySelector('[rel="albumLabel"]'));
SongAdd.form.videoIdLabel = new LabelObj(SongAdd.querySelector('[rel="videoIdLabel"]'));
SongAdd.form.durationLabel = new LabelObj(SongAdd.querySelector('[rel="durationLabel"]'));


SongAdd.form['thumbnail'].onchange = () => {
    const imageEl = SongAdd.form.thumbnailLabel.element.querySelector('.image');
    const emptyEl = SongAdd.form.thumbnailLabel.element.querySelector('.empty');
    if (SongAdd.form['thumbnail'].files.length === 0) {
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
    fileReader.readAsDataURL(SongAdd.form['thumbnail'].files[0]);
}

// const addAnchor = document.getElementById('addAnchor');

SongAdd.form.onsubmit = (e) => {
    e.preventDefault();
    if (!confirm('노래를 등록 하시겠습니까?')) return;

    const formData = new FormData();
    formData.append('_thumbnail', SongAdd.form['thumbnail'].files[0]);
    formData.append('title', SongAdd.form['title'].value);
    formData.append('albumId', SongAdd.form['albumId'].value);
    formData.append('videoId', SongAdd.form['videoId'].value);
    formData.append('duration', SongAdd.form['duration'].value);
    formData.append('artistId', SongAdd.form['artistId'].value);


    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status < 200 || xhr.status >= 300) {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
            return;
        }

        const response = JSON.parse(xhr.responseText);
        if (response.result === 'success') {
            alert('노래가 성공적으로 등록되었습니다.');
            const albumId = response.albumId;
            location.href = `/album?albumId=${albumId}`;
        } else {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
        }
    };

    xhr.open('POST', '/song/add');
    xhr.send(formData);
};
// main