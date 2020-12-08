<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style type="text/css">
body {
	justify-content: center;
	text-align: center;
	background-color: rgb(69, 163, 229);
}

h1 {
	color: white;
	font-size: 500%;
}

.backdrop {
	background-color: white;
	margin-top: 150px;
	margin-left: 600px;
	width: 300px;
	height: 200px;
	padding-top: 50px;
	border-radius: 20px;
	font-weight: 900;
	color: rgb(69, 163, 229);
}

#button1 {
	background-color: rgb(69, 163, 229);
	color: white;
	border-radius: 25px;
	width: 180px;
	height: 50px;
	font-weight: 900;
	font-size: 25px;
}

.link {
	float: right;
	margin-right: 25px;
}
</style>
</head>
<body>
	<h1>Extract Data Web</h1>

	<div class="backdrop">
		<form method="get" action="./ExtractController">
				<label for="link">Link: </label> <input type="text" name="link"
				class="link"> <br> <br> 
				<label for="user_pass">Tag:</label> <input type="text" name="tag" class="link"> <br> <br>
				<label for="user_pass">Thể loại: </label> <input type="text"
				name="categoryStory" class="link"> <br> <br> 
				<label for="user_pass">Tên Truyện: </label> <input type="text"
				name="nameStory" class="link"> <br> <br> 
				<label for="user_pass">Tag Image: </label> <input type="text"
				name="tagImage" class="link"><br> <br> <br> <br>
				<input type="submit" value="Extract" id="button1">


		</form>
	</div>
</body>
</html>