<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - Inscription</title>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/other_css/register.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="staticfiles/other_css/styles.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
    <div class="container" id="registration-form">
        <div class="image"></div>
        <div class="frm">
            <h1>Inscription</h1>
            <form action="<c:url value="/register" />" method="post">
	            <c:if test="${ errors.vide != null }">
					<p class="error"><c:out value="${ errors.vide }"/></p>
				</c:if>
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <c:if test="${ errors.nom != null }">
						<p class="error"><c:out value="${ errors.nom }"/></p>
					</c:if>
                    <input type="text" class="form-control" id="nom" name="nom" value="<c:out value="${ form.nom }"/>" placeholder="Votre nom...">
                </div>
                <div class="form-group">
                    <c:if test="${ errors.prenom != null }">
						<p class="error"><c:out value="${ errors.prenom }"/></p>
					</c:if>
                    <label for="prenom">Prénom</label>
                    <input type="text" class="form-control" id="prenom" name="prenom" value="<c:out value="${ form.prenom }"/>" placeholder="Votre prenom...">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <c:if test="${ errors.email != null }">
						<p class="error"><c:out value="${ errors.email }"/></p>
					</c:if>
                    <input type="email" class="form-control" name="email" value="<c:out value="${ form.email }"/>" id="email" placeholder="Votre adresse email...">
                </div>
                <div class="form-group">
                    <label for="login">Login</label>
                    <c:if test="${ errors.login != null }">
						<p class="error"><c:out value="${ errors.login }"/></p>
					</c:if>
                    <input type="text" class="form-control" value="<c:out value="${ form.login }"/>" id="login" name="login" placeholder="Votre login ou nom d'utilisateur...">
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <c:if test="${ errors.password != null }">
						<p class="error"><c:out value="${ errors.password }"/></p>
					</c:if>
                    <input type="password" class="form-control" id="password" value="<c:out value="${ form.mdp }"/>" name="password" placeholder="Votre mot de passe...">
                </div>
                <div class="form-group">
                    <label for="confirm_password">Confirmer mot de passe</label>
                    <c:if test="${ errors.password != null }">
						<p class="error"><c:out value="${ errors.password }"/></p>
					</c:if>
                    <input type="password" class="form-control" value="<c:out value="${ form.cmdp }"/>" id="confirm_password" name="confirm_password" placeholder="confirmer votre mot de passe...">
                </div>
                <div class="form-group">
                    <a href="<c:url value="/home"/>" class="btn btn-danger btn-md">Annluer</a>
                    <button type="submit" class="btn btn-success btn-md">S'inscire</button>
                </div>
            </form>
        </div>
    </div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>