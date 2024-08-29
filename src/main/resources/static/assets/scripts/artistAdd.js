const ArtistAdd = document.getElementById('artistAdd');

ArtistAdd.form = ArtistAdd.querySelector('.form');
ArtistAdd.form.thumbnailLabel = new LabelObj(ArtistAdd.querySelector('[rel="thumbnailLabel"]'));
ArtistAdd.form.nameLabel = new LabelObj(ArtistAdd.querySelector('[rel="nameLabel"]'));
ArtistAdd.form.genreLabel = new LabelObj(ArtistAdd.querySelector('[rel="genreLabel"]'));
ArtistAdd.form.grpLabel = new LabelObj(ArtistAdd.querySelector('[rel="grpLabel"]'));
ArtistAdd.form.entLabel = new LabelObj(ArtistAdd.querySelector('[rel="entLabel"]'));

ArtistAdd.form['thumbnail'].onchange = () => {
    const imageEl = ArtistAdd.form.thumbnailLabel.element.querySelector('.image');
    const emptyEl = ArtistAdd.form.thumbnailLabel.element.querySelector('.empty');
    if (ArtistAdd.form['thumbnail'].files.length === 0) {
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
    fileReader.readAsDataURL(ArtistAdd.form['thumbnail'].files[0]);
}

// const addAnchor = document.getElementById('addAnchor');

ArtistAdd.form.onsubmit = (e) => {
    e.preventDefault();
    if (!confirm('아티스트를 등록 하시겠습니까?')) return;

    const formData = new FormData();
    formData.append('_thumbnail', ArtistAdd.form['thumbnail'].files[0]);
    formData.append('name', ArtistAdd.form['name'].value);
    formData.append('genre', ArtistAdd.form['genre'].value);
    formData.append('grp', ArtistAdd.form['grp'].value);
    formData.append('ent', ArtistAdd.form['ent'].value);

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status < 200 || xhr.status >= 300) {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
            return;
        }

        const response = JSON.parse(xhr.responseText);
        if (response.result === 'success') {
            alert('아티스트가 성공적으로 등록되었습니다.');
            const artistId = response.artistId;
            location.href = `/artist?artistId=${artistId}`;
        } else {
            alert('등록에 실패하였습니다. 잠시 후 다시 시도해 주세요.');
        }
    };

    xhr.open('POST', '/artist/add');
    xhr.send(formData);
};
// main