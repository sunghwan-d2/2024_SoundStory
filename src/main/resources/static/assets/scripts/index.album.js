var player;
var currentSongIndex = 0;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '360',
        width: '640',
        videoId: '',
        playerVars: {
            'rel': 0, // 추천 영상 비활성화
        },
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
}

function onPlayerReady(event) {
    var playButton = document.querySelector('.play');
    var pauseButton = document.querySelector('.pause');
    var prevButton = document.querySelector('#prevButton');
    var nextButton = document.querySelector('#nextButton');
    var volumeBar = document.querySelector('#volumeBar');
    var progressBar = document.querySelector('#progressBar');

    playButton.addEventListener('click', function() {
        player.playVideo();
        playButton.style.display = 'none';
        pauseButton.style.display = 'inline-block';
    });
    pauseButton.addEventListener('click', function() {
        player.pauseVideo();
        playButton.style.display = 'inline-block';
        pauseButton.style.display = 'none';
    });

    var musicItems = document.querySelectorAll('.music-item');

    function playByItem(item) {
        var videoId = item.getAttribute('data-video-id');
        var title = item.querySelector('.title').textContent;
        var channel = item.querySelector('.channel').textContent;
        if (videoId) {
            document.getElementById('songTitle').textContent = title;
            document.getElementById('songArtist').textContent = channel;
            player.loadVideoById(videoId);
            player.playVideo();
            getVideoInfo(videoId);
        }
    }

    prevButton.addEventListener('click', function() {
        if (--currentSongIndex < 0) {
            currentSongIndex = musicItems.length - 1;
        }
        playByItem(musicItems[currentSongIndex]);
    });
    nextButton.addEventListener('click', function() {
        if (++currentSongIndex >= musicItems.length) {
            currentSongIndex = 0;
        }
        playByItem(musicItems[currentSongIndex]);
    });

    volumeBar.addEventListener('input', function() {
        var volume = volumeBar.value;
        player.setVolume(volume);
    });

    progressBar.addEventListener('input', function() {
        var seekToTime = (player.getDuration() * (progressBar.value / 1000));
        player.seekTo(seekToTime, true);
    });

    musicItems.forEach(function(item, index) {
        item.addEventListener('click', function() {
            currentSongIndex = index;
            playByItem(item);
        });
    });

    // 첫 번째 비디오 자동 재생
    if (musicItems.length > 0) {
        playByItem(musicItems[0]);
    }
}

function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.ENDED) {
        document.querySelector('.play').style.display = 'inline-block';
        document.querySelector('.pause').style.display = 'none';
        if (++currentSongIndex >= document.querySelectorAll('.music-item').length) {
            currentSongIndex = 0;
        }
        playByItem(document.querySelectorAll('.music-item')[currentSongIndex]);
    } else if (event.data == YT.PlayerState.PLAYING) {
        updateProgressBar();
    }
}

function getVideoInfo(videoId) {
    // var xhr = new XMLHttpRequest();
    // xhr.open('GET', 'https://www.googleapis.com/youtube/v3/videos?id=' + videoId + '&part=contentDetails&key=AIzaSyAF_lPBRV2FoLiK4tLdACk8C1NCrv_6DU8', true);
    // xhr.onreadystatechange = function() {
    //     if (xhr.readyState == 4 && xhr.status == 200) {
    //         var response = JSON.parse(xhr.responseText);
    //         var duration = response.items[0].contentDetails.duration;
    //         var totalTimeDisplay = document.getElementById('durationDisplay');
    //         totalTimeDisplay.textContent = formatDuration(duration);
    //     }
    // };
    // xhr.send();
}

function formatDuration(duration) {
    var match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/);
    var hours = (parseInt(match[1]) || 0);
    var minutes = (parseInt(match[2]) || 0);
    var seconds = (parseInt(match[3]) || 0);
    return (hours ? hours + ':' : '') + (minutes ? minutes + ':' : '0:') + (seconds < 10 ? '0' : '') + seconds;
}

function updateProgressBar() {
    var progressBar = document.getElementById('progressBar');
    var currentTimeDisplay = document.getElementById('currentTimeDisplay');
    var durationDisplay = document.getElementById('durationDisplay');
    setInterval(function() {
        var currentTime = player.getCurrentTime();
        var duration = player.getDuration();
        progressBar.value = (currentTime / duration) * 1000;
        currentTimeDisplay.textContent = formatTime(currentTime);
        if (duration) {
            durationDisplay.textContent = formatTime(duration);
        }
    }, 1000);
}

function formatTime(seconds) {
    var minutes = Math.floor(seconds / 60);
    seconds = Math.floor(seconds % 60);
    return minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
}


// 아래는 관리자 페이지를 위한 것1
function showEditForm() {
    document.getElementById('editForm').style.display = 'block';
} // 수정폼 보여지는 것

// 중복 선언 제거
const deleteAlbumElement = document.getElementById('deleteAlbum');
if (deleteAlbumElement) {
    deleteAlbumElement.onclick = e => {
        e.preventDefault();
        if (!confirm('앨범을 삭제하시면 앨범에 있는 노래들도 함께 삭제가 됩니다. 정말로 삭제하겠습니까?')) return;

        const albumId = deleteAlbumElement.dataset['albumId'];
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

        xhr.open('DELETE', `/album/AlbumDelete?albumId=${albumId}`);
        xhr.send();
    };
        document.addEventListener('DOMContentLoaded', function() {
            // 모든 add-playlist 버튼을 선택
            const addButtons = document.querySelectorAll('.add-playlist');

            addButtons.forEach(button => {
                button.addEventListener('click', function() {
                    // data- 속성에서 값 가져오기
                    const songId = this.getAttribute('data-song-id');
                    const songTitle = this.getAttribute('data-song-title');
                    const artistName = this.getAttribute('data-artist-name');

                    // 데이터 출력 (디버깅 용도)
                    console.log(`Song ID: ${songId}`);
                    console.log(`Song Title: ${songTitle}`);
                    console.log(`Artist Name: ${artistName}`);
                });
            });
        });

}