"use strict";
console.log("common.js");

var DatePicker = tui.DatePicker;

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
    var hours = date.getHours()
    var minutes = date.getMinutes()
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
    $('.date-picker').datepicker({
        format: 'yyyy-mm-dd',
        language:"ko",
        todayHighlight:true,
        autoclose:true
    });
    $("div.date-box").each(function(){
        var $inputs = $(this).find('input');
        $inputs.datepicker();
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
    $(".date-picker").attr({
        maxlength: "10",
        autocomplete: "off",
    });
    $(".date-picker").keyup(function(e){
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

        // if (input_length == 4) {
        //  $(this).val( $(this).val() + "-" );
        // } else if (input_length == 7) {
        //  $(this).val( $(this).val() + "-" );
        // }        
        // 날짜형식 이외 focusout 막기
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


