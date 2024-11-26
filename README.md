## 음악 스트리밍 서비스 SoundStory 🎶

<p align="center">
  <img src="https://github.com/user-attachments/assets/749d03e9-e1e9-4c99-90d4-71797b6cbcd7" alt="logo-color" style="width: 25rem; height: 25rem;">
</p>


- 배포 URL : https://kshh.kr
- TEST ID : ghktkf789@gmail.com
- TEST PASSWORD : 11111
   <br>

## 🙋‍♀️ 프로젝트 소개   

- SoundStory는 **<u>사용자만의 플레이리스트를 만들고 음악을 스트리밍 할 수 있게</u>** 하기 위해 만들었습니다. 
- <b> 검색을 통해 손쉽게 원하는 음악을 찾아 스트리밍하거나, 좋아하는 곡을 저장</b>할 수 있습니다.
- 노래 혹은 아티스트에 대해 생각을 공유할 수 있는 <b>댓글 기능을 만들어 각 유저들 간의 소통</b>이 이루어 지도록 했습니다.
- 사용자 취향에 맞춰 <b>추천 앨범을 제공</b>하여 새로운 음악을 발견할 수 있게 돕습니다.
- <b> 나만의 플레이리스트를 추가하고 관리</b>하며, 언제든지 원하는 음악을 쉽게 재생할 수 있습니다.

  <br>
  

## 🛠️ 개발 환경

### Front-end
- **HTML**, **SCSS**, **JavaScript**

### Back-end
- **Sprig Boot**, **MyBatis**, **MariaDB**

### 배포 및 클라우드
- **GCP (Google Cloud Platform)**, **Cloudflare**

<br>

## 💬 페이지별 기능

<b><em>[ 회원가입, 로그인 ]</b></em>

<img src="assets/images/로그인화면.png" alt="loginPage">
<br>
- 홈화면에서 로그인을 클릭하면 위와 같은 로그인화면이 표시되어 아래 그림처럼 회원가입을 할 수 있는 폼으로 넘어가진다. 

<img src="assets/images/회원가입화면.png" alt="registerPage">


<img src="assets/images/회원가입예외처리.png" alt="exceptionregister">
- 이메일 주소와 비밀번호 등의 유효성검사가 진행되며 검사를 만족하지 못하는 경우 오류 문구가 표시됩니다.
<br>

<br>
<br>
<b><em>[ 노래, 아티스트 검색 ]</b></em>
<br>

![search](https://github.com/user-attachments/assets/35f67a0f-15cb-4972-bfed-a56d7142031f)


<b><em>[ 댓글 작성 ] </em>
<br>

![comment](https://github.com/user-attachments/assets/8c1e5be4-58b5-4d85-b49b-a078ac22dbd9)


<b><em>[ 플레이리스트 ] </em>
<br>

![addSong](https://github.com/user-attachments/assets/a2ba375f-c1ce-41e9-b0ae-c7fbd2045b7c)

1. 플레이리스트 노래추가 

![duplicatesong](https://github.com/user-attachments/assets/402f52a9-736e-48cd-957e-08a026a2ac5b)

<br>


2. 플레이리스트 중복시

<img src="assets/images/플레이리스트예외처리.png">
기존 회원중 플레이리스트에 노래가 없다면 상단 경고문으로 예외처리
