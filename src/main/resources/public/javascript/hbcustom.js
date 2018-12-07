var mainCate = $('.main_cate');
var subCate = $('.sub_cate');


$('.main_cate').change(function () {
	var selectedCate = $(this).val();

	$(".sub_cate").addClass('none');
	subCate.removeClass('display');
	subCate.attr('name', '');

	switch (selectedCate) {
		case "상의" :
			$('.sub_cate1').addClass('display');
			$('.sub_cate1').attr('name', 'categorySub');
			break;
		case "하의" :
			$('.sub_cate2').addClass('display');
			$('.sub_cate2').attr('name', 'categorySub');
			break;
		case "아우터" :
			$('.sub_cate3').addClass('display');
			$('.sub_cate3').attr('name', 'categorySub');
			break;
		case "신발" :
			$('.sub_cate4').addClass('display');
			$('.sub_cate4').attr('name', 'categorySub');
			break;
	}

});


// 파 일  업 로 드
$('.preview > .box').html("<p>대표 이미지</p>");

$('.fileUploadBtn').click(function (e) {
	e.preventDefault();
	$("input:file").click();
});

$('input:file').change(function (e) {
	var urlImg = $("input:file").val();
	$('.filename').html(urlImg);
});

var upload = $('input:file')[0],
	holder = $('.preview > .box');

upload.onchange = function (e) {
	e.preventDefault();

	var file = upload.files[0],
		reader = new FileReader();
	reader.onload = function (event) {
		var img = new Image();
		img.src = event.target.result;
		if (img.width > 560) {
			img.width = 560;
		}
		holder.html('');
		holder.css('background-image', 'url("' + img.src + '")');
		holder.css('background-size', 'cover');
	};
	reader.readAsDataURL(file);

	return false;
};

