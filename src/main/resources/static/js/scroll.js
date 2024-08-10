// window.addEventListener("wheel", function(e){
//     e.preventDefault();
// },{passive : false});
// let $html = $("html");
//
// let page = 1;  // 뷰포트에 표시되는 페이지의 번호
//
// let lastPage = $(".section").length; // 마지막 페이지의 번호
//
// $html.animate({scrollTop:0},10); // 문서(페이지)가 로드되면 첫 페이지 시작
// $(window).on("wheel", function(e){
//
// 	if($html.is(":animated")) return;
//
// 	if(e.originalEvent.deltaY > 0){
// 		if(page== lastPage) return;
//
// 		page++;
//     }else if(e.originalEvent.deltaY < 0){
// 		if(page == 1) return;
//
// 		page--;
//     }
//     var posTop = (page-1) * $(window).height();
//
// 	$html.animate({scrollTop : posTop});
//
// });