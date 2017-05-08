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

<div class="container">
	<form id="file-form" action="" method="POST">
        <div class="row">
            <div class="col-md-3">
                <h3> Resultados</h3>
            </div>
        </div>

        <div id="tableDiv" class="row">
        <table  class="table">
        <thead><tr><td>Nombre</td><td>Ver</td></tr></thead>
        <tbody>
        <c:forEach items="${documentos}" var="documento">
            <tr>
                <td>${documento.getName()}</td>
                <td><a href="/show/file/${documento.getPath()}">Link</a></td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
        </div>

        <button  class="btn btn-default" type="submit" id="upload-button">Upload</button>

    </form>
	<hr>
	<footer>
		<p>&copy; Mkyong.com 2015</p>
	</footer>
</div>

<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/js/jquery.js" var="jQuery" />
<script src="${jQuery}"></script>
<script src="${bootstrapJs}"></script>

</body>
</html>