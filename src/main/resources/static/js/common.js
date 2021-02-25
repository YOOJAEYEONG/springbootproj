console.log("common.js");

$(function () {
    $('.modal-dialog').draggable({
        handle: ".modal-header"
    });
});







var pagingUtil = {

    pageSize : 10		//한 페이지당 게시글 수
    ,rangeSize : 5	    //한 블럭당 페이지 수


    /** 현재 페이지 **/
    ,curPage : 1
    /** 현재 블럭(range) **/
    ,curRange:1
    /** 총 게시글 수 **/
    ,totalCnt:0
    /** 총 페이지 수 **/
    ,pageCnt:0
    /** 총 블럭(range) 수 **/
    ,rangeCnt:0
    /** 시작 페이지 **/
    ,startPage : 1
    /** 끝 페이지 **/
    ,endPage : 1
    /** 시작,끝 index **/
    ,startIndex : 0
    ,endIndex : 0
    /** 이전 페이지 **/
    ,prevPage : 1
    /** 다음 페이지 **/
    ,nextPage : 1

    ,pagingUnit : function (curPage, totalCnt) {
        /**
         * 페이징 처리 순서
         * 1. 총 페이지수
         * 2. 총 블럭(range)수
         * 3. range setting
         */

        // 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.
        /** 현재페이지 **/
        this.setCurPage(curPage)
        /** 총 게시물 수 **/
        this.setTotalCnt(totalCnt)
        /** 1. 총 페이지 수 **/
        this.setPageCnt(totalCnt)
        /** 2. 총 블럭(range)수 **/
        this.setRangeCnt(this.pageCnt)
        /** 3. 블럭(range) setting **/
        this.rangeSetting(this.curPage)

        /** DB 질의를 위한 Index 설정 **/
        this.setStartIndex(curPage)
        this.setEndIndex(curPage)

    }
    ,getCurPage : function () {
        return this.curPage
    }
    ,setCurPage : function (curPage) {
        this.curPage = curPage
    }
    ,getCurRange : function () {
        return this.curRange
    }
    ,setCurRange : function (curRange) {
        this.curRange = Math.ceil((curRange-1) / this.rangeSize) + 1
    }
    ,getTotalCnt : function () {
        return this.totalCnt
    }
    ,setTotalCnt : function (totalCnt) {
        this.totalCnt = totalCnt
    }
    ,getPageCnt : function () {
        return this.pageCnt
    }

    /** 1. 총 페이지 수 **/
    ,setPageCnt : function (totalCnt) {
        this.pageCnt = Math.ceil(totalCnt * 1.0 / this.pageSize)
    }
    ,getRangeCnt : function () {
        return this.rangeCnt
    }
    /** 2. 총 블럭(range)수 **/
    ,setRangeCnt : function (pageCnt) {
        this.rangeCnt = Math.ceil(pageCnt * 1.0 / this.rangeSize)
    }

    /** 3. 블럭(range) setting **/
    ,rangeSetting : function (curPage){
        this.setCurRange(curPage)
        this.startPage = (this.curRange - 1) * this.rangeSize + 1
        this.endPage = this.startPage + this.rangeSize - 1
        console.log("startPage:"+this.startPage)
        console.log("endPage1:"+this.endPage)
        console.log("pageCnt:"+this.pageCnt)
        if(this.endPage > this.pageCnt){
            this.endPage = this.pageCnt
            console.log("endPage2:"+this.endPage)
        }
        this.prevPage = curPage - 1
        this.nextPage = curPage + 1
    }

    ,getStartPage : function () {
        return this.startPage
    }
    ,setStartPage : function (startPage) {
        this.startPage = startPage
    }
    ,getEndPage : function () {
        return this.endPage
    }
    ,setEndPage : function(endPage) {
        this.endPage = endPage
    }
    ,getStartIndex : function () {
        return this.startIndex
    }
    ,getPageSize : function () {
        return this.pageSize
    }
    ,setPageSize : function (pageSize) {
        this.pageSize = pageSize
    }
    ,setStartIndex : function (curPage) {
        this.startIndex = (curPage-1) * this.pageSize + 1
    }
    ,getPrevPage : function () {
        return this.prevPage
    }
    ,setPrevPage : function (prevPage) {
        this.prevPage = prevPage
    }
    ,getNextPage : function () {
        return this.nextPage
    }
    ,setNextPage : function (nextPage) {
        this.nextPage = nextPage
    }
    ,getEndIndex : function () {
        return this.endIndex
    }
    ,setEndIndex : function (curPage) {
        this.endIndex = curPage * this.pageSize
    }


    // ,toString : function () {
    //     return "PagingUtil [curPage:" + curPage + ", curRange:" + curRange + ", totalCnt:" + totalCnt + ", pageCnt:"
    //         + pageCnt + ", rangeCnt:" + rangeCnt + ", startPage:" + startPage + ", endPage:" + endPage
    //         + ",\n startIndex:" + startIndex + ", endIndex:" + endIndex + ", prevPage:" + prevPage + ", nextPage:"
    //         + nextPage + "]"
    // }
}


