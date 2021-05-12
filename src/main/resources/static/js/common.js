"use strict";
console.log("common.js");

const DatePicker = tui.DatePicker;

$(function () {
    $('.modal-dialog').draggable({
        handle: ".modal-header"
    });
});

/**
 * UTCTimestamp -> LocalTime
 * @param {string} timestamp
 */
function convertUTCtoLocal(timestamp) {
    const date = new Date(timestamp + " UTC");
    var aaaa = date.getFullYear();
    var gg = date.getDate();
    var mm = (date.getMonth() + 1);
    if (gg < 10)
        gg = "0" + gg;
    if (mm < 10)
        mm = "0" + mm;
    var cur_day = aaaa + "-" + mm + "-" + gg;
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (hours < 10)
        hours = "0" + hours;
    if (minutes < 10)
        minutes = "0" + minutes;
    if (seconds < 10)
        seconds = "0" + seconds;
    return cur_day + " " + hours + ":" + minutes + ":" + seconds;
}

/**
 * bootstrap date-picker
 */
$(function () {
    $('.datePicker').datepicker({
        format: 'yyyy-mm-dd',
        language:"ko",
        todayHighlight:true,
        autoclose:true
    });
    $('.dateRange').datepicker({
        format: "yyyy-mm-dd",
        startDate: "-10y",
        calendarWeeks: true,
        todayHighlight: true
    });
    $(".date-box").each(function(){
        var $inputs = $(this).find('input');
        $inputs.datepicker({
            format: 'yyyy-mm-dd',
            startDate : '-10y', //달력에서 선택할 수 있는 가장 이른 날짜
            //,language : "ko" //달력의 언어 선택, 그에 맞는 js로 교체해줘야함.
            todayHighlight: true,
            autoclose: true,
            //다음달 이전달로 넘어가는 화살표 모양 커스터마이징
            // templates : {
            //     leftArrow: '&laquo;', // <<
            //     rightArrow: '&raquo;'
            // }
        });
        if($inputs.length >= 2){
            var $from = $inputs.eq(0);
            var $to = $inputs.eq(1);
            $from.on('changeDate',function (e){
                var d = new Date(e.date.valueOf());
                $to.datepicker('setStartDate', d);
            });
            $to.on('changeDate',function(e){
                var d = new Date(e.date.valueOf());
                $from.datepicker('setEndDate',d);
            });
        }
    });
    var input_length;
    $(".datePicker").keyup(function(e){
        var input_length = $(this).val().length;
        //숫자,'-'만 입력
        $(this).val($(this).val().replace(/[^0-9-]/gi,""));
        //날짜 형식 변환
        if(e.keyCode != 8){
            if(input_length == 4){
                $(this).val($(this).val()+"-");
            }else if(input_length==7){
                $(this).val($(this).val()+"-");
            }
        }

        //날짜형식 이외 focusout 막기
        $(this).focusout(function(){
            var validformat=/^\d{4}\-\d{2}\-\d{2}$/;
            if(($(this).val() != '') && !validformat.test($(this).val())){
                $(this).select();
            }
        });

        if(e.keyCode == 13){
            e.preventDefault();
            e.stopPropagation();
            return false;
        }
    });
});

/**
 * Toast Grid 필터가 화면 밖에 생성되는 케이스 수정
 *
 * @param gridId {string}
 */
function setTuiGridFilterPosition(gridId) {
    let gridWidth,filterWidth,filterLeft,tuiDatepickerLeft;
    try {
        $(".tui-grid-filter-container").draggable();
        gridWidth = $("#"+gridId).offset().left + $("#"+gridId).width();
        filterWidth = $(".tui-grid-filter-container").offset().left + $(".tui-grid-filter-container").width();
        filterLeft = $(".tui-grid-filter-container").offset().left;
        tuiDatepickerLeft = $(".tui-datepicker").offset().left;
    }catch (e) {
        if(filterWidth > gridWidth){
            $(".tui-grid-filter-container").offset({left : (filterLeft - (filterWidth - gridWidth + 19))});
            $(".tui-datepicker").offset({left: (tuiDatepickerLeft - 57)});
        }
    }
}

/**
 * 파일 크기를 반환
 * @example getFileSize(1500) => 1.5 kb
 * @param {number} fileSize
 * @returns {string}
 */
function getFileSize(fileSize) {
    const unit = ["Byte","KB","MB","GB","TB","PB","EB","ZB","YB"];
    const size = 1024;
    let sizeStr;
    for (let i = 1; fileSize >= size; i++) {
        fileSize /= size;
        if (fileSize < size){
            sizeStr = (Math.floor(fileSize * 100) / 100) + " " + unit[i];
        }
    }
    if(fileSize < size){
      sizeStr = (Math.floor(fileSize * 100) / 100) + " " + unit[0]
    }
    return sizeStr;
}



