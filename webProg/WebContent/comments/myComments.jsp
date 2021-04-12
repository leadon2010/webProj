<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>myComments.jsp</title>
	<style>
		.comment {
			border: 1px dotted blue;
		}
	</style>
	<script>
		window.onload = function () {
			loadCommentList();  // 목록조회 ajax 요청
		}
		var xhr;
		//목록요청
		function loadCommentList() {
			xhr = new XMLHttpRequest();
			xhr.onreadystatechange = loadCommentResult
			var param = "cmd=selectAll";
			xhr.open("get", "../CommentsServ?" + param);
			xhr.send();
		}
		//목록요청 콜백함수
		function loadCommentResult() {
			if (xhr.readyState == 4) {    //응답이 완료
				if (xhr.status == 200) {  //정상실행
					var xmlDoc = xhr.responseXML;
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						//data 태그 내용(json string)
						var data = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
						// 목록(string)  ==> 객체   
						var commentList = eval("(" + data + ")");
						// 댓글을 추가할 부모태그
						var listDiv = document.getElementById('commentList');
						//댓글목록수 만큼 반복
						for (var i = 0; i < commentList.length; i++) {
							var comments = commentList[i];
							var div = makeCommentView(comments);
							listDiv.appendChild(div);
						}
					}
				}
			}
		}

		function makeCommentView(comments) {
			//div 생성
			var div = document.createElement("div");
			div.comment = comments;

			div.className = "comment";
			div.setAttribute("id", "c" + comments.id);
			//<strong>name</string>
			var strong = document.createElement("strong");
			strong.innerHTML = comments.name;
			div.appendChild(strong);

			//<span>content</span>
			var span = document.createElement("span");
			span.innerHTML = comments.content;
			div.appendChild(span);

			//수정버튼  <input type="buuton" value="수정" onclick=""/>
			var updBtn = document.createElement("input");
			updBtn.setAttribute("type", "button");
			updBtn.setAttribute("value", "수정");
			updBtn.addEventListener("click", viewUpdateForm);
			div.appendChild(updBtn);

			//삭제버튼  <input type="buuton" value="수정" onclick=""/>
			var delBtn = document.createElement("input");
			delBtn.setAttribute("type", "button");
			delBtn.setAttribute("value", "삭제");
			delBtn.addEventListener("click", confirmDeletion);
			div.appendChild(delBtn);

			return div;
		}

		//수정폼 보이게
		function viewUpdateForm() {
			//수정할 div 찾기
			var div = this.parentNode;
			var updateFormDiv = document.getElementById('commentUpdate');
			//수정폼을 보이게 하고 수정할 값을 텍스트 필드에 지정
			updateFormDiv.style.display = '';
			document.updateForm.id.value = div.comment.id;
			document.updateForm.name.value = div.comment.name;
			document.updateForm.content.value = div.comment.content;
			div.appendChild(updateFormDiv);
		}
		//수정취소
		function cancelUpdate() {
			//텍스트필드 클리어
			document.updateForm.reset();
			// 수정폼 div 찾기
			var updateFormDiv = document.getElementById('commentUpdate');
			// 안보이게
			updateFormDiv.style.display = 'none';
			// 바디로 이동
			document.body.appendChild(updateFormDiv);
		}


		//수정요청
		function updateComment() {
			xhr = new XMLHttpRequest();
			xhr.onreadystatechange = updateResult
			var id = document.updateForm.id.value;
			var name = document.updateForm.name.value;
			var content = document.updateForm.content.value;
			var param = "cmd=update"
				+ "&id=" + id
				+ "&name=" + encodeURIComponent(name)
				+ "&content=" + encodeURIComponent(content);
			xhr.open("get", "../CommentsServ?" + param);
			xhr.send();
		}
		//수정 콜백
		function updateResult() {
			if (xhr.readyState == 4) {    //응답이 완료
				if (xhr.status == 200) {  //정상실행
					var xmlDoc = xhr.responseXML;
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						//data 태그 내용(json string)
						var data = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
						// 목록(string)  ==> 객체   
						var comments = eval("(" + data + ")");
						// 부모태그
						var listDiv = document.getElementById('commentList');
						// 기존의 댓글 div
						var oldDiv = document.getElementById('c' + comments.id);
						// 수정된 내용으로 div 생성
						var div = makeCommentView(comments);

						//수정폼을 바디로 이동
						cancelUpdate();

						//old div를 수정된 div로 교체
						listDiv.replaceChild(div, oldDiv);
					}
				}
			}
		}

		//삭제요청
		function confirmDeletion(e) {
			e = window.event || e;
			if (confirm("삭제할까요?")) {
				xhr = new XMLHttpRequest();
				xhr.onreadystatechange = deleteResult
				var id = e.target.parentNode.comment.id;  //e.target  == this
				var param = "id=" + id + "&cmd=delete";
				xhr.open("get", "../CommentsServ?" + param);
				xhr.send();
			}
		}


		//삭제요청 콜백
		function deleteResult() {
			if (xhr.readyState == 4) {    //응답이 완료
				if (xhr.status == 200) {  //정상실행
					var xmlDoc = xhr.responseXML;
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						//data 태그 내용(json string)
						var data = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
						// 목록(string)  ==> 객체   
						var comments = eval("(" + data + ")");
						var listDiv = document.getElementById('commentList');
						var div = document.getElementById('c' + comments.id);
						// 태그 삭제
						//listDiv.removeChild(div);
						div.parentNode.removeChild(div);
					}
				}
			}
		}

		//댓글등록요청
		function addComment() {
			xhr = new XMLHttpRequest();
			xhr.onreadystatechange = addResult

			var name = document.addForm.name.value;
			var content = document.addForm.content.value;

			var param = "cmd=insert&name="
				+ encodeURIComponent(name)
				+ "&content=" + encodeURIComponent(content);
			xhr.open("get", "../CommentsServ?" + param);
			xhr.send();
		}
		//댓글등록콜백
		function addResult() {
			if (xhr.readyState == 4) {    //응답이 완료
				if (xhr.status == 200) {  //정상실행
					var xmlDoc = xhr.responseXML;
					var code = xmlDoc.getElementsByTagName('code').item(0).firstChild.nodeValue;
					if (code == 'success') {
						//data 태그 내용(json string)
						var data = xmlDoc.getElementsByTagName('data').item(0).firstChild.nodeValue
						// 목록(string)  ==> 객체   
						var comments = eval("(" + data + ")");
						// 댓글을 추가할 부모태그
						var listDiv = document.getElementById('commentList');

						var div = makeCommentView(comments);
						listDiv.appendChild(div);

						//등록폼의 텍스트필드 클리어
						document.addForm.reset();
					}
				}
			}
		}

	</script>

</head>

<body>
	<h3>댓글</h3>
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
	<div id="commentUpdate" style="display:none">
		<form action="" name="updateForm">
			<input type="hidden" name="id" value="" />
			이름: <input type="text" name="name" size="10"><br />
			내용: <textarea name="content" cols="20" rows="2"></textarea><br />
			<input type="button" value="등록" onclick="updateComment()" />
			<input type="button" value="취소" onclick="cancelUpdate()" />
		</form>
	</div>
	<!-- 댓글수정폼끝 -->

</body>

</html>