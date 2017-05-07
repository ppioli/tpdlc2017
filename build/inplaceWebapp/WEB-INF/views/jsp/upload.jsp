

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Gradle + Spring MVC</title>

<spring:url value="/resources/core/css/base.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Project Name</a>
		</div>
	</div>
</nav>

<div class="jumbotron">
	<div class="container">
		<h1>${title}</h1>
		<p>
			<c:if test="${not empty msg}">
				Hello ${msg}
			</c:if>
			<c:if test="${not empty palabra}">
                    Palabra ${palabra.getValor()} Cuenta ${palabra.getCuentaMaxima()}
            </c:if>

			<c:if test="${empty msg}">
				Welcome Welcome!
			</c:if>
		</p>
		<p>
			<a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
		</p>
	</div>
</div>

<div class="container">
	<form id="file-form" action="" method="POST">

        <input type="file" id="file-select" name="files" multiple/>
        <button type="submit" id="upload-button">Upload</button>

    </form>
	<hr>
	<footer>
		<p>&copy; Mkyong.com 2015</p>
	</footer>
</div>

<spring:url value="/resources/core/js/upload.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/js/jquery.js" var="jQuery" />
<script src="${jQuery}"></script>
<script src="${bootstrapJs}"></script>
<script src="${coreJs}"></script>

</body>
</html>