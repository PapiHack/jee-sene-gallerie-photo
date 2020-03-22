<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - Accueil</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	<div class="container">
		<div class="row">
		<c:if test="${ welcome != null }">
			<div class="col-md-offset-2 alert alert-info alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ welcome }"></c:out> </h4>
			</div>
		</c:if>
			<div class="col-lg-12 jumbotron">
				<h1>Bienvenue sur <i>#SenGalleriePhoto</i>, votre réseau social de partage de photos.</h1>
				<p>
					Sen Gallerie Photo ou <strong>S.G.P</strong> pour les intimes, est une plateforme ou réseau social, 
					permettant une certaine interaction entre ses utilisateurs via le partage d'albums photo.
				</p>
				<p>
					<a href="<c:url value="/gallery"/>" title="Explorez la gallerie" class="btn btn-success">
						<i class="fa fa-photo"></i> Visitez la gallerie
					</a>
				</p>
			</div>
		</div>
	</div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>