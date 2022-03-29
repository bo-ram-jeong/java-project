<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<script type="text/javascript" src="./resources/js/validation.js"></script>
<title>상품 등록</title>
</head>
<body>
<fmt:setLocale value='<%=request.getParameter("language") %>' />
<fmt:bundle basename="bundle.message" >
	<jsp:include page="menu.jsp" />
	
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3"><fmt:message key="title"/></h1>
		</div>	
	</div>
	
	<div class="container">
	<div class="text-right">
		<a href="?language=ko">Korean</a> | <a href="?language=en">English</a>
		<a href="logout.jsp" class="btn btn-sm btn-success pull-right">logout</a>
	</div>		
		<form name="newProduct" action="./processAddProduct.jsp" class="form-horizontal" 
		method="post" enctype="multipart/form-data">
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="productId"/></lable>
				<div class="col-sm-3">
					<input type="text" id="productId" name="productId" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="pname"/></lable>
				<div class="col-sm-3">
					<input type="text" id="name" name="name" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="unitPrice"/></lable>
				<div class="col-sm-3">
					<input type="text" id="unitPrice" name="unitPrice" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="description"/></lable>
				<div class="col-sm-5">
					<textarea name="description" cols="50" rows="2" class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="manufacturer"/></lable>
				<div class="col-sm-3">
					<input type="text" name="manufacturer" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="category"/></lable>
				<div class="col-sm-3">
					<input type="text" name="category" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="unitsInStock"/></lable>
				<div class="col-sm-3">
					<input type="text" id="unitsInStock" name="unitsInStock" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="condition"/></lable>
				<div class="col-sm-5">
					<input type="radio" name="condition" value="New "><fmt:message key="condition_New"/>
					<input type="radio" name="condition" value="Old "><fmt:message key="condition_Old"/>
					<input type="radio" name="condition" value="Refurbished "><fmt:message key="condition_Refurbished"/>
				</div>
			</div>
			<div class="form-group row">
				<lable class="col-sm-2"><fmt:message key="productImage"/></lable>
				<div class="col-sm-5">
					<input type="file" name="productImage" value="form-control">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-primary" value="<fmt:message key="button"/>" onclick="CheckAddProduct()">
				</div>
			</div>
		
		</form>
	</div>
</fmt:bundle>
</body>
</html>


