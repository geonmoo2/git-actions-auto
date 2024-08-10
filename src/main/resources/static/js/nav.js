// 네비게이션 바 요소 가져오기
const navbar = document.getElementById('navbar');
// 스크롤 이벤트 리스너 추가
window.addEventListener('scroll', function () {
    // 현재 스크롤 위치 확인
    const scrollPosition = window.scrollY;

    // 일정 스크롤 이상 스크롤했을 때
    if (scrollPosition > 600) { // 여기서 100은 예시로 설정한 값입니다. 필요에 따라 변경하세요.
        // 네비게이션 바에 클래스 추가하여 투명하지 않은 스타일로 변경
        navbar.classList.add('navbar-opaque');
    } else {
        // 스크롤이 일정 이하일 때는 다시 투명한 스타일로 변경
        navbar.classList.remove('navbar-opaque');
    }
});

