<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SGP - Gallerie</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/sweet-alert/sweetalert.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/gallery.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/baguetteBox.min.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	
	<div class="gallery-block cards-gallery">
	    <div class="container">
	        <div class="heading">
				<c:if test="${ empty albums }">
	          		<h2 class="titre">Il y a pas d'albums disponible pour le moment.</h2>
	          		<a href="<c:url value="/user/album/add" />" class="btn btn-success">Créer un nouvel album</a>
				</c:if>
				<c:if test="${ !empty albums }">
	          		<h2 class="titre">Albums disponibles</h2>
	          		<a href="<c:url value="/user/album/add" />" class="btn btn-success">Créer un nouvel album</a>
				</c:if>
	        </div>
	        <c:if test="${ !empty albums }">
	        	<div class="row">
	        		<c:forEach items="${ albums }" var="album">
			        	<div class="col-md-6 col-lg-4">
				        	<div class="card transform-on-hover">
			                	<a class="lightbox" href="<c:url value="/uploads/${ album.images[0].fichierImage }" />">
			                		<img src="<c:url value="/uploads/${ album.images[0].fichierImage }" />" style="width: 100%; height: 250;" alt="Card Image" class="card-img-top">
			                	</a>
			                    <div class="card-body">
			                        <h6 class="card-title">
			                        	<a href="#"><c:out value="${album.titre }" /></a>
			                        </h6>
			                        <p class="text-muted card-text"><c:out value="${album.description }" /></p>
			                    </div>
							</div>
			        	</div>
	        		</c:forEach>
	        	</div>
	        </c:if>
	    </div>
    </div>
	
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/sweet-alert/sweetalert.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/baguetteBox.min.js"/>"></script>
    <script>
        baguetteBox.run('.cards-gallery', { animation: 'slideIn'});
    </script>
</body>
</html>