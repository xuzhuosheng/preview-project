
$(function () {
    
});

/**
 *
 * @param con swiper容器
 * @param srcArr 图片路径
 * @param swiperParam swiper参数
 */
function newSwiper(con, srcArr, swiperParam) {
    var $container = $(con);
    $container.show();
    var swiperWrapper = $container.find(".swiper-wrapper");
    var swiperSlider = '';

    $.each(srcArr, function (idx, item) {

        var mediumContainer = ' <img src="' + item + '">';

        //判断是否为视频
        var videoFormat = ["mp4", "flv", "rm", "rmvb", "wmv"];
        var fileType = (item.substring(item.lastIndexOf(".") + 1, item.length)).toLowerCase();

        if ($.inArray(fileType, videoFormat) >= 0) {

            mediumContainer = '<video style="width: 100%; height: auto;" controls="true" preload="auto" src="' + item + '"></video>';
        }

        swiperSlider += '<div class="swiper-slide"><div class="swiper-zoom-container">' + mediumContainer + '</div></div>';

    });
    swiperWrapper.append(swiperSlider);
    swiperWrapper.empty().append(swiperSlider);

    $container.find(".closs-btn").on("click", function () {
        $container.hide();

           });

    return new Swiper(con, swiperParam);

}


/**
 * 判断用户长时间未操作 并显示轮播
 * @param isNotInit （true:还没没初始化 false:已经初始化）
 * @param swiper
 * @param imgSrc 轮播图片路径集
 */
function overTime(isNotInit,swiper,imgSrc) {
    var lastTime = new Date().getTime();
    var currTime = new Date().getTime();
    // var outTime = 1 * 60 *1000; //设置超时时间 1分钟
    var outTime = .5 * 60 * 1000; //设置超时时间
    $(document).on("mouseover", function () {//监听鼠标移动事件
        lastTime = new Date().getTime();//更新时间
    });
    $(document).on("touchstart", function () {
        lastTime = new Date().getTime();//更新时间
    });
    $(document).on("touchmove", function () {
        lastTime = new Date().getTime();//更新时间
    });
    var inter = setInterval(function () {//定时器 间隔1 秒检测是否长时间未操作页面
        currTime = new Date().getTime();//更新当前时间
        if (currTime - lastTime > outTime) {//判断是否超时
            clearInterval(inter);//清除定时器
            console.log("长时间未操作");

            $("#slideshow").show();
            var slideshowSwiperParam = {
                //设置宽度为全屏
                width: window.innerWidth,
                on: {
                    resize: function(){
                        this.params.width = window.innerWidth;
                        this.update();
                    },
                } ,
                loop: true, // 循环模式选项
                autoplay: {
                    delay: 2000,
                    // disableOnInteraction: false,
                },

                // 如果需要分页器
                pagination: {
                    el: '.swiper-pagination',
                }

            };
            if(isNotInit){
                swiper = newSwiper("#slideshow",imgSrc,slideshowSwiperParam);
                isNotInit = false;
            }else {
                swiper.autoplay.start();
            }
            $("#slideshow").off("click").on("click",function () {
                $(this).hide();
                swiper.autoplay.stop();
                overTime(isNotInit,swiper,imgSrc)
            })


        }
    }, 1000)
}