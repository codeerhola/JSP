<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
</head>
<body>

<h4>Restful 기반의 메모 관리</h4>

<!-- 동기든 비동기든 보내야하는 주소, 메소드, 데이터는 같아  -->
<form name = "memoForm" action="${pageContext.request.contextPath}/memo" method="post">
	<input type="text" name="writer" placeholder="작성자">
	<input type="date" name="date" placeholder="작성일">
	<textarea name="content"></textarea>
	<input type="submit" value="등록">
</form>

<table class="table-bordered">
   <thead>
      <tr>
         <th>작성자</th>
         <th>작성일</th>
      </tr>
   </thead>
   <tbody id="listBody"></tbody>
</table>

<!-- <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
  Launch demo modal
</button> -->
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div id="modalContent" class="modal-body">
       ... 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	/* $('[name = "memoForm"]') 같은 뜻 임 */
	//폼테그의 네임속성을 주면 도큐먼트의 프로퍼티명이 됨 제이쿼리 객체 과정
	//this=== event.target this=== event.target
	let memoForm = $(document.memoForm).on('submit',function(event){ //
		//this === event.target     $(this)!=this
		event.preventDefault();
		//$(this)는 이벤트의 타겟 
		let url = this.action;
		let method = this.method;
		//let data = $(this).serialize(); //애초부터 만들어지는 코드는 writer="작성자&date=작성일&content=내용(쿼리스트링) 파라미터가 쿼리스트링형태로 넘어감 
		//객체 writer,date,content 는 프로퍼티임 프로퍼티명은 폼태그 input태그의 네임 속성값임. 값은 어디서 와야해? 밸류에서 와야해  

		let data = $(this).serializeObject();
		
		$.ajax({
			url : url,
			method : method,
			contentType:"application/json; charset=UTF-8", //request content-type 보내는 편지의 타입을 결정
			data : JSON.stringify(data),
			dataType : "json",  //받는 입장의 타입 request의 Accept, response content-type
			success : function(resp) { //tbody를 갱신 할 수 있어야함. 이걸 makeListBody로 만들어놓음 
				makeListBody(resp.memoList);
				memoForm[0].reset();
			},
			error : function(jqXHR, status, error) {
			}
		});
		return false;
	});
	
   // EDD(Event Driven Development)
   // TDD(Test Driven Development)
   $("#exampleModal").on("show.bs.modal", function(event){
      // this==event.target : modal 창 // this는 떠 있는 모달창
      let memo = $(event.relatedTarget).data("memo"); // modal을 오픈할때 사용한 클릭 대상, tr
      $(this).find(".modal-body").html(memo.content);
   }).on("hidden.bs.modal", function(event){
      $(event.target).find(".modal-body").empty();
   });
   
   let listBody = $("#listBody");
   let makeListBody = function(memoList){
      listBody.empty(); // 초기화
      let trTags = [];
      
      if(makeListBody.length>0){
         $.each(memoList, function(index, memo){
            // data-bs-toggle="modal" data-bs-target="#exampleModal"
            let tr = $("<tr>").append(
               $("<td>").html(memo.writer)
               ,$("<td>").html(this.date)
            ).attr({
               "data-bs-toggle":"modal",
               "data-bs-target":"#exampleModal"
            }).data("memo",memo);
            trTags.push(tr);
         });
      }else{
         let tr = $("<tr>").html( // 만든다라는 뜻
            $("<td>")
               .attr("colspan", "2")
               .html("작성된 메모 없음.")
         );
         trTags.push(tr);
      }
      listBody.append(trTags);
   }
   
   $.ajax({
      url : "${pageContext.request.contextPath}/memo",
      // 아래같은 경우를 Rest라고 한다.
      // url : "${pageContext.request.contextPath}/memo/1",
      // 1번 메모를 삭제하겠어
      // method : "delete",
      method : "get", // 전체 메모를 가져와
      dataType : "json",
      success : function(resp) {
         makeListBody(resp.memoList);
      },
      error : function(jqXHR, status, error) {
         console.log(jqXHR);
         console.log(status);
         console.log(error);
      }
   });

   
</script>
<jsp:include page="/includee/postScript.jsp"></jsp:include>
</body>
</html> 