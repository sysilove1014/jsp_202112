<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/includefile.jsp" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script type="text/javascript">

	function loginCheck() {
		var email = frmLogin.email.value;
		var passwd = frmLogin.passwd.value;
		console.log(email);
		console.log(passwd);
		if(email==''){
			alert('이메일을 입력해주세요!');
			frmLogin.email.focus();
		}else if (passwd==''){
			alert('비밀번호를 입력해주세요!');
			frmLogin.passwd.focus();
		}else{
			frmLogin.action = '${path}/member/login';
			frmLogin.submit();
		}
		
		
		
	}
	
</script>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<h2>로그인</h2>
	<form name="frmLogin" method="post">
		<table>
			<tr>
				<th>이메일</th>
				<td> <input type="email" name="email" value="${cookie.email.value}"> </td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td> <input type="password" name="passwd"> </td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<!-- 쿠기에 저장 여부 체크 -->
					<input type="checkbox" name="idsave" checked>이메일저장 
					<button type="button"  onclick="loginCheck()">로그인</button>
				</td>
			</tr>			
		</table>
	</form>
</body>
</html>