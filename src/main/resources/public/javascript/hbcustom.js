const mainCate = $('.main_cate');
const subCate = $('.sub_cate');

mainCate.change(function () {
	let selectedCate = $(this).val();

	$(".sub_cate").addClass('none');
	subCate.removeClass('display');
	subCate.attr('name', '');

	switch (selectedCate) {
		case "상의" :
			$('.sub_cate1').addClass('display').attr('name', 'categorySub');
			break;
		case "하의" :
			$('.sub_cate2').addClass('display').attr('name', 'categorySub');
			break;
		case "아우터" :
			$('.sub_cate3').addClass('display').attr('name', 'categorySub');
			break;
		case "신발" :
			$('.sub_cate4').addClass('display').attr('name', 'categorySub');
			break;
	}
});


// 파 일  업 로 드
const holder = $('.preview > .box');
const upload = $('input:file');

holder.html("<p>대표 이미지 등록</p>");

$('.fileUploadBtn').click(function (e) {
	e.preventDefault();
	upload.click();
});

upload.change(function (e) {
	$('.filename').html(upload.val());
});


$('#price').keyup(function () {
	$(this).val($(this).val().replace(/[^0-9]/g, ""));
});


let files = upload[0];

files.onchange = function (e) {
	e.preventDefault();

	let file = files.files[0];
	let reader = new FileReader();
	reader.onload = function (event) {
		let img = new Image();
		img.src = event.target.result;
		// if (img.width > 200) {
		// 	img.width = 200;
		// }
		holder.html('');
		holder.css('background-image', 'url("' + img.src + '")');
		holder.css('background-size', 'cover');
	};
	reader.readAsDataURL(file);

	return false;
};
