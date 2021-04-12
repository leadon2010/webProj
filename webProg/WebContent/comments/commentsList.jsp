<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>댓글</title>
	<style>
		.comment {
			border: 1px dotted blue;
		}
	</style>
	<script src="ajax.js"></script>
	<script>
		window.onload = function () {
			loadCommentList(); // 목록조회 ajax 요청
		}
		//목록조회 요청
		function loadCommentList() {
			var param = "cmd=selectAll";
			new ajax.xhr.Request("../CommentsServ", param, loadCommentResult, 'GET');
		}
		//목록조회 콜백함수
		function loadCommentResult(req) {
			if (req.readyState == 4) { //응답이 완료
				if (req.status == 200) { //정상실행  
					var xmlDoc = req.responseXML; // 응답결과가 XML 
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						// data 태그의 태그바디값(string)을  json 객체로 변환 
						var commentList = eval("(" + xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue + ")");
						var listDiv = document.getElementById('commentList');
						for (var i = 0; i < commentList.length; i++) {
							var commentDiv = makeCommentView(commentList[i]);//댓글div 태그생성
							listDiv.appendChild(commentDiv); // div목록에 댓글div 추가
						}
					} else if (code == 'error') {
						var message = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue;
						alert("에러 발생:" + message);
					}
				} else {
					alert("댓글 목록 로딩 실패:" + req.status);
				} //에러인 경우 상태코드 출력
			}
		}

		function makeCommentView(comment) {
			var div = document.createElement("div");
			div.setAttribute("id", "c" + comment.id);
			div.className = 'comment';
			div.comment = comment; ////{ id:2, name:'김유미', content:'첫번째 글'},

			var str = "<strong>" + comment.name + "</strong> "
				+ comment.content
				+ "<input type=\"button\" value=\"수정\" onclick=\"viewUpdateForm('" + comment.id + "')\"/>"
				+ "<input type=\"button\" value=\"삭제\" onclick=\"confirmDeletion('" + comment.id + "')\"/>"
			div.innerHTML = str;
			return div;
		}

		//댓글 등록 ajax 요청
		function addComment() {
			var name = document.addForm.name.value;
			var content = document.addForm.content.value;
			if (name == null || name === "") {
				alert("name is invalid");
				document.addForm.name.focus();
				return;
			} else if (content == null || content === "") {
				alert("content is invalid");
				document.addForm.content.focus();
				return;
			}
			var params = "name=" + encodeURIComponent(name) + "&" + "content=" + encodeURIComponent(content) + "&cmd=insert";
			new ajax.xhr.Request('../CommentsServ', params, addResult, 'POST');
		}
		////댓글 등록 콜백함수
		function addResult(req) {
			if (req.readyState == 4) {
				if (req.status == 200) {
					var xmlDoc = req.responseXML;
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						var comment = eval("(" + xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue + ")");
						var listDiv = document.getElementById('commentList');
						var commentDiv = makeCommentView(comment);
						listDiv.appendChild(commentDiv);
						//등록폼 텍스트필드 클리어
						document.addForm.name.value = '';
						document.addForm.content.value = '';
						alert("등록했습니다![" + comment.id + "]");
					} else if (code == 'fail') {
						var message = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue;
						alert("에러 발생:" + message);
					}
				} else {
					alert("서버 에러 발생: " + req.status);
				}
			}
		}

		//수정버튼 이벤트핸들러: 수정할 댓글밑에 수정폼 보이게 함
		function viewUpdateForm(commentId) {
			var commentDiv = document.getElementById('c' + commentId);
			var updateFormDiv = document.getElementById('commentUpdate');
			//현재 수정상태(수정폼이 보이고 있음)이면 수정폼이 보이게 할 필요 없음
			if (updateFormDiv.parentNode != commentDiv) {
				commentDiv.appendChild(updateFormDiv); //수정폼을 수정할 댓글밑에 보이도록
			}
			//수정할 값을 텍스트필드에 보이게
			var comment = commentDiv.comment; //댓글 객체 { id:'', content:'', name:'' }
			document.updateForm.id.value = comment.id;
			document.updateForm.name.value = comment.name;
			document.updateForm.content.value = comment.content;
			updateFormDiv.style.display = 'block'; //수정폼 보이게
		}

		//댓글 수정 ajax 요청
		function updateComment() {
			var id = document.updateForm.id.value;
			var name = document.updateForm.name.value;
			var content = document.updateForm.content.value;
			var params = "id=" + id + "&" + "name=" + encodeURIComponent(name) + "&" + "content=" + encodeURIComponent(content) + "&cmd=update";
			new ajax.xhr.Request('../CommentsServ', params, updateResult, 'POST');
		}
		////댓글 수정 콜백함수
		function updateResult(req) {
			if (req.readyState == 4) {
				if (req.status == 200) {
					var xmlDoc = req.responseXML;
					console.log(xmlDoc);
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					console.log(code);
					if (code == 'success') {
						var comment = eval("(" + xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue + ")");
						var listDiv = document.getElementById('commentList');
						var commentDiv = makeCommentView(comment);

						var oldCommentDiv = document.getElementById('c' + comment.id);
						listDiv.replaceChild(commentDiv, oldCommentDiv);
						alert("수정했습니다!");

					} else if (code == 'fail') {
						var message = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue;
						alert("에러 발생:" + message);
					}
				} else {
					alert("서버 에러 발생: " + req.status);
				}
			}
		}

		//댓글 삭제 ajax 요청
		function confirmDeletion(id) {
			var params = "id=" + id + "&cmd=delete";
			new ajax.xhr.Request('../CommentsServ', params, deleteResult, 'POST');
		}
		////댓글 삭제 콜백함수
		function deleteResult(req) {
			if (req.readyState == 4) {
				if (req.status == 200) {
					var xmlDoc = req.responseXML;
					console.log(xmlDoc);
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						var comment = eval("(" + xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue + ")");
						console.log(comment);
						var listDiv = document.getElementById('commentList');
						var commentDiv = document.getElementById('c' + comment.id);
						commentDiv.parentNode.removeChild(commentDiv);
						alert("삭제했습니다!");

					} else if (code == 'fail') {
						var message = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue;
						alert("에러 발생:" + message);
					}
				} else {
					alert("서버 에러 발생: " + req.status);
				}
			}
		}
	</script>
</head>

<body>
	<div id="commentList"></div>

	<!-- 댓글등록시작 -->
	<div id="commentAdd">
		<form action="" name="addForm">
			이름: <input type="text" name="name" size="10"><br />
			내용: <textarea name="content" cols="20" rows="2"></textarea><br />
			<input type="button" value="등록" onclick="addComment()" />
		</form>
	</div>
	<!-- 댓글등록끝 -->

	<!-- 댓글수정폼시작 -->
	<div id="commentUpdate" style="display: none">
		<form action="" name="updateForm">
			<input type="hidden" name="id" value="" />
			이름: <input type="text" name="name" size="10"><br />
			내용: <textarea name="content" cols="20" rows="2"></textarea><br />
			<input type="button" value="변경" onclick="updateComment()" />
			<input type="button" value="취소" onclick="cancelUpdate()" />
		</form>
	</div>
	<!-- 댓글수정폼끝 -->
</body>

</html>