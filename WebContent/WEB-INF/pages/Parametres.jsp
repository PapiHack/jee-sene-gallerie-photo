<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - Paramètres</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/styles.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/sweet-alert/sweetalert.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	<div class="container">
	<c:if test="${ edit_params != null }">
			<div class="col-md-offset-2 alert alert-warning alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ edit_params }"></c:out> </h4>
			</div>
	</c:if>
		<div class="row">
			<div class="col-lg-12">
				<h1 align="center">Paramétrage de votre compte.</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<div class="panel panel-default">
					<div class="panel panel-heading">
						Changement de vos paramètres de connexion 
					</div>
					<div class="panel panel-body">
						<form action="<c:url value="/user/parametre" />" method="post">
				            <c:if test="${ errors.vide != null }">
								<p class="error"><c:out value="${ errors.vide }"/></p>
							</c:if>
			                <div class="form-group">
			                    <label for="login">Login</label>
			                    <c:if test="${ errors.login != null }">
									<p class="error"><c:out value="${ errors.login }"/></p>
								</c:if>
			                    <input type="text" class="form-control" value="<c:out value="${ form.login }"/>" id="login" name="login" placeholder="Votre login ou nom d'utilisateur...">
			                </div>
			                <div class="form-group">
			                    <label for="old_password">Mot de passe actuel</label>
			                    <c:if test="${ errors.old_password != null }">
									<p class="error"><c:out value="${ errors.old_password }"/></p>
								</c:if>
			                    <input type="password" class="form-control" id="old_password" value="<c:out value="${ form.old_mdp }"/>" name="old_password" placeholder="Votre mot de passe actuel...">
			                </div>
			                <div class="form-group">
			                    <label for="new_password">Nouveau mot de passe</label>
			                    <c:if test="${ errors.password != null }">
									<p class="error"><c:out value="${ errors.password }"/></p>
								</c:if>
			                    <input type="password" class="form-control" id="new_password" value="<c:out value="${ form.mdp }"/>" name="new_password" placeholder="Votre nouveau mot de passe...">
			                </div>
			                <div class="form-group">
			                    <label for="confirm_password">Confirmer nouveau mot de passe</label>
			                    <c:if test="${ errors.password != null }">
									<p class="error"><c:out value="${ errors.password }"/></p>
								</c:if>
			                    <input type="password" class="form-control" value="<c:out value="${ form.cmdp }"/>" id="confirm_password" name="confirm_password" placeholder="confirmer votre nouveau mot de passe...">
			                </div>
			                <div class="form-group">
			                    <a href="<c:url value="/user/home"/>" class="btn btn-danger btn-md">Annluer</a>
			                    <button type="submit" class="btn btn-success btn-md">Valider</button>
			                </div>
            			</form>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/sweet-alert/sweetalert.min.js"/>"></script>
</body>
</html>