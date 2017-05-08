<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>TPDLC</title>

<spring:url value="/resources/core/css/base.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/"/>">TPDLC2017Home</a>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 100px">
	<form id="file-form" action="" method="POST">
        <div class="row">

            <div class="col-md-4">
                <label class="btn btn-default btn-file">
                    Seleccionar Archivos <input type="file" id="file-select"  style="display: none;" name="files" multiple/>
                </label>
                
                <button  class="btn btn-default" type="submit" id="upload-button">Upload</button>
            </div>
        </div>

        <div id="tableDiv" class="row">
        <table  class="table">
        <thead><tr><td>#</td><td>Nombre</td><td>Status</td></tr></thead>
        <tbody id="filesTable">

        </tbody>
        </table>
        </div>

        

</div>

<spring:url value="/resources/core/js/upload.js" var="coreJs" />
<spring:url value="/resources/core/img/spinner.gif" var="spinnerUrl" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/js/jquery.js" var="jQuery" />
<script src="${jQuery}"></script>
<script src="${bootstrapJs}"></script>
<script src="${coreJs}"></script>

</body>
</html>