<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SGP - Mes albums</title>
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
		<c:if test="${ deleteAlbum != null }">
			<div class="col-md-offset-2 alert alert-danger alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ deleteAlbum }"></c:out> </h4>
			</div>
		</c:if>
		<c:if test="${ updateAlbum != null }">
			<div class="col-md-offset-2 alert alert-warning alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ updateAlbum }"></c:out> </h4>
			</div>
		</c:if>
	        <div class="heading">
				<c:if test="${ empty albums }">
	          		<h2 class="titre">Vous n'avez aucun album pour le momement !</h2>
				</c:if>
				<c:if test="${ !empty albums }">
	          		<h2 class="titre">Mes albums</h2>
				</c:if>
	          		<a href="<c:url value="/user/album/add" />" class="btn btn-success">Créer un nouvel album</a>
	        </div>
	        <c:if test="${ !empty albums }">
	        	<div class="row">
	        		<c:forEach items="${ albums }" var="album">
			        	<div class="col-md-6 col-lg-4">
				        	<div class="card transform-on-hover">
			                	<a class="lightbox" href="<c:url value="/uploads/${ album.images[0].fichierImage }" />">
			                		<img src="<c:url value="/uploads/${ album.images[0].fichierImage }" />" style="width: 100%" alt="Card Image" class="card-img-top">
			                	</a>
			                    <div class="card-body">
			                    	<p class="pull-right">
			                        	<a href="#" class="btn btn-success" title="Ajouter une image à cet album"><i class="fa fa-plus fa fa-image"></i></a>
			                        	<a href="#" class="btn btn-warning" title="Editer cet album"><i class="fa fa-edit"></i></a>
			                        	<a href="<c:url value="/user/album/delete?album=${ album.id }" />" onclick="return confirm('Voulez-vous vraiment supprimer cet album ?');" class="btn btn-danger sup" title="Supprimer cet album"><i class="fa fa-trash"></i></a>
			                        </p>
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
        /*
        var albums = document.getElementsByClassName('sup');
        albums.map(album => {
        	album.addEventListener('click', event => {
        		if(!confirm('Voulez-'))
        	})
        })*/
    </script>
</body>
</html>