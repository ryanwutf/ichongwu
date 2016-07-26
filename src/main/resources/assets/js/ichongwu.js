
//	$("#report").click(function(){
//		$("#floatMask").show();
//	});
//	
//	$("#rpCont .rpReason").click(function(){
//		$(this).addClass('rpReasonActive');
//		$(this).siblings().removeClass('rpReasonActive');
//	});
//	
//	$("#closeBtn").click(function(){
//		$("#floatMask").hide();
//	});
//	
//	$("#reSubmit").click(function(){
//		var title = $("#rpCont .rpReasonActive").text();
//		var content = $("#inputCont").val();
//		$.ajax({
//			url: ctx+'/report/send.do',
//			type: 'post',
//			data: {'title' : title,'content' : content},
//			success: function (data) {
//				if(data.code == 200){
//					$("#floatMask").hide();
//					alert("Ìá½»³É¹¦");
//				}
//				
//			}
//		});
//	});
	
	whatBrowser();
    /*
	 * var swiper = new Swiper('.swiper-container', { pagination:
	 * '.swiper-pagination', slidesPerView: 2 });
	 */
	
  var video=document.getElementsByClassName("video")[0]
   $(window).scrollTop(0);
	var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        slidesPerView: 'auto',
        // centeredSlides: true,
        paginationClickable: true
    });
    $(window).scroll(function (e) {
        if ($(window).scrollTop() > window.screen.height) {
             $(".fixInput").eq(0).addClass("fixed")
        } else {
            $(".fixInput").eq(0).removeClass("fixed")
        }
    });
	 $(".videoBox-playBox").on("click",function(){
		var id = parseInt($('#videoidforupdate').text());
		console.log("id:" + id)
		$.ajax({url:"/video/addCount", data:{"id": id}});
        $(this).hide();
        $(".video").show()
        video.play();
    })
    video.addEventListener("pause", function () {
        $(".videoBox-playBox").show();
        $(this).hide();
    }, false);
	
	function whatBrowser() {  
		var strs= new Array(); 
		var browser = {
				versions: function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return {// 
				trident: u.indexOf('Trident') > -1, //
				presto: u.indexOf('Presto') > -1, //
				webKit: u.indexOf('AppleWebKit') > -1, //
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //
				mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // iosÖÕ¶Ë
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //
				iPad: u.indexOf('iPad') > -1, //
				webApp: u.indexOf('Safari') == -1 //
				};
				}(),
				language: (navigator.browserLanguage || navigator.language).toLowerCase()
			}
				 
			if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
				strs=navigator.appVersion.split("Mobile/"); 
			}
			else if (browser.versions.android) {
				strs=navigator.appVersion.split("Mobile");
			}
		// strs=navigator.appVersion.split("Mobile/");
		if(strs[1]){
			strs2 = strs[1].split("/"); 
			strs3 = strs2[1];
			if(typeof(strs3)=='undefined') {
				$(".appds").hide();
			}else{
				$(".appds").show();
				$(".tcs").attr('href','javascript:void(0)');
				$(".tcs").attr('onclick',"alert('click')");
				// window.location.href=
				// "itms-apps://itunes.apple.com/us/app/wu-ye-qing-yuan-ye-wan-liang/id1053737921?mt=8";
			}
		}
	}
	function tss(){
		var ua = navigator.userAgent;
		if (/MicroMessenger/.exec(ua)) {
			$('.shade').show();
			$('.hint').show();return false;
		}
		var browser = {
				versions: function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return {// 
				trident: u.indexOf('Trident') > -1, //
				presto: u.indexOf('Presto') > -1, //
				webKit: u.indexOf('AppleWebKit') > -1, //
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //
				mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //
				iPad: u.indexOf('iPad') > -1, //
				webApp: u.indexOf('Safari') == -1 //
				};
				}(),
				language: (navigator.browserLanguage || navigator.language).toLowerCase()
			}
				 
			if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
				// window.location.href=
				// "itms-apps://itunes.apple.com/us/app/wu-ye-qing-yuan-ye-wan-liang/id1053737921?mt=8";
				var aurl = $('.tappurl').val();
				window.location.href = "itms-apps://"+aurl+'"';
			}
			else if (browser.versions.android) {
				var aurl = $('.taappurl').val();
				// window.location.href =
				// 'http://api.rwjd007.com/androids/wyqy.apk';
				window.location.href = aurl;
			}
		
	}	