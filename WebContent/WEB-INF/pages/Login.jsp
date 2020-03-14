<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - Connection</title>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/other_css/login.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/other_css/styles.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/sweet-alert/sweetalert.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	
		<c:if test="${ register != null }">
			<div class="col-md-offset-2 alert alert-success alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ register }"></c:out> </h4>
			</div>
		</c:if>
    <div class="container" id="log-in-form">
        <div class="heading">
            <h1>Connectez-vous à votre compte</h1>
        </div>
        <form action="<c:url value="/login" />" method="post">
            <div class="form-group">
                <input type="text" class="form-control" id="username" value="<c:out value="${ login }"/>" name="login" placeholder="Votre login...">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" id="pwd" name="password" placeholder="Votre mot de passe...">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success btn-lg">Se connecter <i class="fa fa-sign-in"></i></button>
            </div>
            <div class="clearfix"></div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="remember_me" name="remember_me"> Se souvenir de moi
                </label>
            </div>
        </form>
    </div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="staticfiles/sweet-alert/sweetalert.min.js"/>"></script>
    <c:if test="${ error != null }">
    	<script>
    		swal("Erreur !", "Login ou mot de passe incorrecte !", "error")
        </script>
	</c:if>
</body>
</html>