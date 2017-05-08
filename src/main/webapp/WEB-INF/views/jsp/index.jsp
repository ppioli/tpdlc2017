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

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h1 class="text-center">Mis Documentos</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <form action="<c:url value="/search"/>" method ="GET">
                <div class="input-group">
                    <input type="text" class="form-control" name = "query" placeholder="Buscar"/>
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-success">
                            Buscar
                        </button>
                    </span>
                </div>
            </form>

        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-offset-10 col-md-2">
            <a href="<c:url value="/upload"/>" class="btn btn-default">Agregar Documentos</a>
        </div>
    </div>
</div>



<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/js/jquery.js" var="jQuery" />
<script src="${jQuery}"></script>
<script src="${bootstrapJs}"></script>


</body>
</html>