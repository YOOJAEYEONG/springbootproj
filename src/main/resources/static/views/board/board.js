"use strict";
let grid;
let Grid = tui.Grid;
let pagination;
let dbData;
let selectContent;//선택한 게시물의 정보를 저장
var replTitleSpan;//댓글내용을 임시 저장할 변수
const detailModal = new bootstrap.Modal(document.getElementById('detailModal'), {
  backdrop:"static"//모달창 외부 클릭시 창 닫힘 여부
});
const updateModal = new bootstrap.Modal(document.getElementById('updateModal'), {
  backdrop:"static"//모달창 외부 클릭시 창 닫힘 여부
});
const viewer = toastui.Editor.factory({
  el: document.querySelector('#detailViewer')
  ,viewer: true
  ,initialValue: ''//초기 내용
  // ,hideModeSwitch: false,
});
const editorButtonList = [
  'heading','bold','italic','strike','divider','hr','quote',
  'divider',/*'ul','ol'*/,'task','indent','outdent','divider',
  'table','image','link','divider','code','codeblock','divider',
  // Using Option: Customize the last button
  // {
  //     type: 'button',
  //     options: {
  //         el: createLastButton(),
  //         command: 'Bold',
  //         tooltip: 'Custom Bold'
  //     }
  // }
];
const editorUpdate = new toastui.Editor({
  el : document.querySelector("#editorUpdate"),
  initialEditType: 'wysiwyg',
  previewStyle: 'vertical',
  language: 'ko-KR',
  toolbarItems: editorButtonList
});
const editorInsert = new toastui.Editor({
  //height: '500px',
  el : document.querySelector("#editorInsert"),
  initialEditType: 'wysiwyg',
  previewStyle: 'vertical',
  language: 'ko-KR',
  toolbarItems: editorButtonList
});
//editor.setMarkdown('');//실행시 디폴트값 </br> 없애기
//editor.getHtml();

var container = document.getElementById('tui-date-picker-container');
var target = document.getElementById('tui-date-picker-target');




ajaxSelectList();//리스트 조회

// $("#btnInput").on('shown.bs.modal', function () {
//   $(this).focus();
// });
$("#ajaxInsert").on("click", ajaxInsert);
$("#btnUpdate").on("click",function () {
  $("#detailModal .btn-close").trigger("click");
  updateModal.show();
  updateForm.reset();
  $("#updateForm").jform(selectContent);
  editorUpdate.setHtml(selectContent.contents);
});
$("#searchForm input").on("keyup",function (evt) {
  if (evt.key == "Enter"){
    $("#searchForm input[name=pageNum]").val(1);
    ajaxSelectList(true);
  }
});
$("#searchForm select[name=pageSize]").on("change",function (evt) {
  grid.setPerPage(parseInt(evt.target.value));
  $("#searchForm input[name=pageNum]").val(1);
  ajaxSelectList(true);
});
$("#ajaxDelete").on("click",ajaxDeletePost);
$("#ajaxUpdate").on("click",ajaxUpdatePost);
//댓글등록
$("#ajaxBoardReplyPost").on("click", ajaxBoardReplyPost);


/**
 * 리스트생성
 * @param {Object} data : DBListData
 * */
function createGrid(data) {
  var options = {
    totalItems: data.pagination.totalCount,
    itemsPerPage: $("select[name=pageSize]").val(),
    visiblePages: 10, //페이지버튼 갯수 설정
    page: $("#searchForm input[name=pageNum]").val(),
    centerAlign: true,
  }
  pagination = new tui.Pagination('pagination', options);
  //페이지버튼 클릭시
  pagination.on('beforeMove', function(evt) {
    var currentPage = evt.page;
    $("#searchForm input[name=pageNum]").val(currentPage);
    ajaxSelectList(false);
  });
  tui.Grid.applyTheme('clean',{row:{hover:{background:'whitesmoke'}}}); // Call API of static method
  grid = new tui.Grid({
    el: document.getElementById('grid') // Container element
    ,scrollY : false
    ,scrollX : true
    ,data : data.contents
    // ,rowHeaders: ['rowNum', 'checkbox']
    ,onGridUpdated : function (evt) {
      console.log(evt);
    }
    ,columnOptions : {
      resizable : true
    }
    ,columns: [
      {
        header: 'No'
        ,name: 'rnum'
        ,align : "center"
        ,resizable : false
      },
      {
        header: '제목'
        ,name: 'title'
        ,width: 500
        ,ellipsis : true // 커럼 사이즈보다 넘치는 내용을 자동 ... 처리 해줌
        ,escapeHTML : true //html entity 형식을 자동 디코딩해줌
        ,renderer:{
          type : titleRenderer
        }
      },
      {
        header: '이름'
        ,name: 'userName'
        ,width: 150
        ,ellipsis : true
        ,escapeHTML : true
        ,align : "center"
      },
      {
        header: '날짜'
        ,name: 'updateDate'
        ,width: 160
        ,align : "center"
        ,resizable : false
      }
    ]
  });
  grid.on("click",function (evt) {
    console.log(evt);
    if (evt.targetType == "cell"){
      selectContent = dbData[evt.rowKey];//선택한 게시물의 정보를 전역변수에 저장
      ajaxSelectBoardPost();
      detailModal.show();
      var width = $("#detailModal").width();
      $("#detailModal td.title").css('maxWidth',width - width/2)
    }
  });
  function titleRenderer(props) {
    // console.log('titleRenderer',props);
    var div = document.createElement("div");
    div.setAttribute("class","tui-grid-cell-content");
    div.setAttribute("style","cursor:pointer;");

    var replyCnt = dbData[props.rowKey].replyCnt;
    if (replyCnt>0){
      div.innerHTML = props.value+"<span class='badge rounded-pill bg-warning text-dark m-1'>"+replyCnt+"</span>";
    }else{
      div.innerHTML = props.value;
    }
    this.el = div;
    titleRenderer.prototype.getElement = function () {
      return this.el;
    }
  }
}


/**
 * 리스트조회
 * @param resetPaginationFlag
 */
function ajaxSelectList(resetPaginationFlag) {
  var params = JSON.parse($("#searchForm").jform());
  params.pageSize = $("select[name=pageSize] :selected").val();
  $.ajax({
    type: "get",
    url: "/ajax/board/freeBoard/list",
    contentType: "application/json;charset=UTF-8",
    dataType: "json",
    data : params,
    success : function (data) {
      console.log('ajaxSelectList',data);
      if (data.result == true){
        dbData = data.data.contents;
        //utcdate를 localdate로 변환
        for (const datum of data.data.contents) {
          datum.updateDate = convertUTCtoLocal(datum.updateDate);
        }
        if(grid){
          grid.resetData(data.data.contents); // Call API of instance's public method
          if (resetPaginationFlag){
            pagination.reset(data.data.pagination.totalCount);
          }
        }else{
          createGrid(data.data);
        }
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    },
  });
}

/**
 * 글쓰기
 */
function ajaxInsert() {
  var insertForm = $("#insertForm").jform();
  insertForm = JSON.parse(insertForm);
  insertForm.contents = editorInsert.getHtml();
  console.log("ajaxInsert",insertForm);

  if(!insertForm.title){alert("제목을 입력하세요.");return;}
  if(!insertForm.contents){alert("내용을 입력하세요.");return;}
  if(!insertForm.userName){alert("작성자를 입력하세요.");return;}
  if(!insertForm.pw){alert("비밀번호를 입력하세요.");return;}

  $.ajax({
    type: "post",
    url: "/ajax/board/freeBoard",
    dataType: "json",
    data : insertForm,
    success : function (data) {
      console.log('ajaxInsert',data);
      if (data.result == true){
        alert("등록되었습니다.");
        $("#insertModal .btn-close").trigger("click");
        ajaxSelectList(true);
      }else {
        alert("에러가 발생했습니다.");
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}
/**
 * 글수정
 */
function ajaxUpdatePost() {
  var updateForm = $("#updateForm").jform();
  updateForm = JSON.parse(updateForm);
  updateForm.contents = editorUpdate.getHtml();
  console.log("ajaxUpdatePost",updateForm);

  if(!updateForm.title){alert("제목을 입력하세요.");return;}
  if(!updateForm.contents){alert("내용을 입력하세요.");return;}
  if(!updateForm.userName){alert("작성자를 입력하세요.");return;}
  if(!updateForm.pw){alert("비밀번호를 입력하세요.");return;}

  $.ajax({
    type: "put",
    url: "/ajax/board/freeBoard",
    dataType: "json",
    data : updateForm,
    success : function (data) {
      console.log('ajaxUpdatePost',data);
      if (data.result == true){
        alert("등록되었습니다.");
        $("#updateModal .btn-close").trigger("click");
        ajaxSelectList(true);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}

/**
 * 게시글상세보기
 * @param postId
 */
function ajaxSelectBoardPost(){
  $.ajax({
    type: "get",
    url: "/ajax/board/freeBoard/"+selectContent.postId,
    dataType: "json",
    // data : {postId : postId},
    success : function (data) {
      console.log('ajaxSelectBoardPost',data);
      if (data.result == true){
        var postContent = data.data.postContent;
        var replyContent = data.data.reply;
        selectContent.contents = postContent.contents;
        for (const elem in postContent) {
          let value = postContent[elem];
          if (elem == "contents"){
            viewer.setMarkdown(value);
          }else{
            $("#detailModal").find("[name="+elem+"]").html(value);
          }
        }
        $(".replyList").remove();
        //댓글을 상세화면 하단에 추가
        $(replyContent).each((idx,item)=>{
          // console.log('reply',idx,item);
          $("#detailModal #replyBox")
            .after(
              "<div id="+item.postId+" class='replyList d-flex justify-content-between input-group-sm mt-1 mb-0'>" +
              "<span class='replRow ' style='display: -webkit-inline-box;width:calc(100% - 525px)'>"+
              "<small class='me-2'>"+item.createDate+"</small>"+
              "<sapn class='title'>"+item.title+"</sapn>"+
              "</span>"+
              `
                <div class='d-flex justify-content-end'>
                  <button class='updateReply btn btn-sm btn-outline-light'>
                    <i class="bi bi-pencil-square"></i>
                  </button>
                  <button class='deleteReply btn btn-sm btn-outline-light'>
                    <i class="bi bi-x"></i>
                  </button>
                  <div class='input-group'>
                    <button class='voteUp btn btn-sm btn-outline-primary px-1'>
                      <i class="bi bi-arrow-up-circle"></i>
                    </button>
                    <span class='input-group-text text-primary'>${item.voteUp}</span>
                  </div>
                  <div class='input-group'>
                    <button class='voteDown btn btn-sm btn-outline-danger px-1'>
                      <i class="bi bi-arrow-down-circle"></i>
                    </button>
                    <span class='input-group-text text-danger'>${item.voteDown}</span>
                  </div>
                </div>`+
              "</div>"
            );
        });
        $(".deleteReply").on("click",function (evt) {
          ajaxDeleteReply($(evt.currentTarget).closest("div.replyList").attr("id"));
        });
        $(".updateReply").on("click",function (evt) {
          //비밀번호 확인
          // if(!ajaxSelectReplyPW(evt)){
          //     return;
          // }
          //댓글쓰기버튼을 댓글 등록 버튼으로 변경
          addUpdateReplyEventHandler(evt,false);
          addDeleteReplyEventHandler(evt,true);
        });
      }
      $(".voteUp, .voteDown").on("click", ajaxUpdateReplyVote);
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });

  /**
   *
   * @param evt
   * @param editMode
   */
  function addDeleteReplyEventHandler(evt,editMode) {
    var id = $(evt.currentTarget).closest("div.replyList").attr("id");
    if (editMode){
      // console.log('addDeleteReplyEventHandler',editMode)
      $("#"+id).find(".deleteReply")
        .removeClass("btn-outline-light")
        .addClass("btn-light")
        .empty()
        .append(`<i class="bi bi-x-circle"></i>`)
        .off("click")
        .on("click",(evt)=>{
          $("#"+id+" .replRow").find("input")
            .remove()
            .append(replTitleSpan);
          addDeleteReplyEventHandler(evt,false);
        });
    }else{
      // console.log('addDeleteReplyEventHandler',editMode)
      $("#"+id).find(".deleteReply")
        .removeClass("btn-light")
        .addClass("btn-outline-light")
        .empty()
        .append(`<i class="bi bi-x"></i>`)
        .off("click")
        .on("click",(evt)=>{
          ajaxDeleteReply($(evt.target).closest("div.replyList").attr("id"));
        });
      addUpdateReplyEventHandler(evt,true);
    }
  }

  /**
   *
   * @param evt
   * @param editMode
   */
  function addUpdateReplyEventHandler(evt,editMode) {
    var id = $(evt.currentTarget).closest("div.replyList").attr("id");
    if (editMode){
      // console.log('addUpdateReplyEventHandler',editMode)
      $("#"+id).find(".updateReply")
        .removeClass("btn-light")
        .addClass("btn-outline-light")
        .empty()
        .append(`<i class="bi bi-pencil-square"></i>`)
        .off("click")
        .on("click", (evt)=>{
          var id = $(evt.target).closest("div.replyList").attr("id");
          addUpdateReplyEventHandler(evt,false);
          addDeleteReplyEventHandler(evt,true);
        });
      //댓글내용을 다시 삽입
      if(replTitleSpan){
        $("#"+id+" .replRow").append(replTitleSpan);
        replTitleSpan = null;
      }
    }else{
      // console.log('addUpdateReplyEventHandler',editMode)
      var replTitle = $(evt.target).closest(".replyList").find(".title").text();
      //입력되있던 댓글내용을 제거
      replTitleSpan = $("#"+id).find(".replRow .title").detach();
      // console.log('replTitleSpan',replTitleSpan,'replTitle',replTitle);
      //input 삽입
      $("#"+id).find(".replRow")
        .append("<input type='text' name='title' class='form-control form-control-sm' value='"+replTitle+"' autocomplete='off' maxlength='1000'>"+
          "<input type='password' name='pw' class='form-control form-control-sm' style='width: auto;' placeholder='password'>"
        );
      $("#"+id).find(".updateReply")
        .removeClass("btn-outline-light")
        .addClass("btn-light")
        .empty()
        .append(`<i class="bi bi-check-circle"></i>`)
        .off("click")
        .on("click",(evt)=>{
          ajaxUpdateReply(id);
        })
    }
  }
}

/**
 * 게시글삭제
 */
function ajaxDeletePost() {
  var pw = prompt("비밀번호");
  if(!pw)    return;
  $.ajax({
    type: "delete",
    url: "/ajax/board/freeBoard",
    dataType: "json",
    data : {postId : selectContent.postId, pw : pw},
    success : function (data) {
      console.log('ajaxDeletePost',data);
      if (data.result == true){
        alert("삭제되었습니다");
        $("#detailModal .btn-close").trigger("click");
        ajaxSelectList(false);
      }else if(data.result == false && data.info == "not matched"){
        alert("비밀번호가 일치하지 않습니다.");
      }else{
        console.log(data.info);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}

/**
 * 댓글등록
 */
function ajaxBoardReplyPost() {
  //댓글내용 빈값 검사
  if($("#replyBox input[name=title]").val() == "") {
    $("#replyBox input[name=title]").focus().addClass("is-invalid");
    return;
  }else{
    $("#replyBox input[name=title]").removeClass("is-invalid");
  }
  //댓글비번 유효성 검사
  if($("#replyBox input[name=pw]").val() == "") {
    $("#replyBox input[name=pw]").focus().addClass("is-invalid");
    return;
  }else{
    $("#replyBox input[name=pw]").removeClass("is-invalid");
  }
  var replyForm = JSON.parse($("#replyBox").jform());
  replyForm.postGroupId = selectContent.postId;
  console.log("ajaxBoardReplyPost",replyForm);
  $.ajax({
    type: "post",
    url: "/ajax/board/freeBoard/reply",
    dataType: "json",
    data : replyForm,
    success : function (data) {
      console.log('ajaxBoardReplyPost',data);
      if (data.result == true){
        replyBox.reset();//댓글입력폼 초기화
        ajaxSelectBoardPost();
      }else if(data.result == false && data.info == "not matched"){
        alert("비밀번호가 일치하지 않습니다.");
      }else{
        console.log(data.info);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}
/**
 * 댓글수정
 * @param {strig} postId
 * */
function ajaxUpdateReply(postId) {
  //댓글내용
  var title = $("#"+postId+" .replRow input[name=title]").val();
  var pw = $("#"+postId+" .replRow input[name=pw]").val();
  $.ajax({
    type: "put",
    url: "/ajax/board/freeBoard/reply",
    dataType: "json",
    data : {postId:postId,title:title, pw:pw},
    success : function (data) {
      console.log('ajaxBoardReplyPost',data);
      if (data.result == true){
        ajaxSelectBoardPost();
      }else if(data.result == false && data.info == "not matched"){
        $("#"+postId+" .replRow input[name=pw]").focus();
        alert("비밀번호가 일치하지 않습니다.");
      }else{
        console.error(data.info);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}

/**
 * 댓글삭제
 */
function ajaxDeleteReply(postId) {
  var pw = prompt("비밀번호");
  if(!pw)    return;
  $.ajax({
    type: "delete",
    url: "/ajax/board/freeBoard/reply",
    dataType: "json",
    data : {postId : postId, pw : pw},
    success : function (data) {
      console.log('ajaxDeleteReply',data);
      if (data.result == true){
        alert("삭제되었습니다");
        ajaxSelectBoardPost();//상세 화면 갱신
        ajaxSelectList(false);//목록화면 갱신(댓글수표시)
      }else if(data.result == false && data.info == "not matched"){
        alert("비밀번호가 일치하지 않습니다.");
      }else{
        console.error(data.info);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}

/**
 * 댓글 추천수 업데이트
 * @param evt
 */
function ajaxUpdateReplyVote(evt) {
  console.log("ajaxUpdateReplyVote");
  console.log(evt);
  console.log(this);
  let postId = $(this).closest(".replyList").attr("id");
  let type;
  if ($(this).attr("class").includes("voteUp")){
    type = "voteUp";
  }else if ($(this).attr("class").includes("voteDown")){
    type = "voteDown";
  }


  $.ajax({
    type: "put",
    url: "/ajax/board/freeBoard/replyVote",
    // dataType: "json",
    data: {postId: postId, type: type},
    success: function (data) {
      console.log('ajaxUpdateReplyVote', data);
      if (data.result == true) {
        ajaxSelectBoardPost();//상세 화면 갱신
      } else {
        console.error(data.info);
      }
    },
    error: function (request, status, error) {
      console.log(request, status, error)
    }
  });
}

/**
 * 댓글 비번 체크
 * @param evt
 */
function ajaxSelectReplyPW(evt) {
  var postId = $(evt.currentTarget).closest(".replyList").attr("id");
  $.ajax({
    type: "post",
    url: "/ajax/board/freeBoard/reply/",
    dataType: "json",
    data : insertForm,
    async: false, //비동기 -> 동기
    timeout: 1500,
    success : function (data) {
      console.log('ajaxInsert',data);
      if (data.result == true){
        alert("등록되었습니다.");
        $("#insertModal .btn-close").trigger("click");
        ajaxSelectList(true);
      }
    },
    error : function (request, status, error) {
      console.log(request, status, error)
    }
  });
}
