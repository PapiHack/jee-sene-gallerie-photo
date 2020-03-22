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
			                	<a class="lightbox" href="<c:url value="/user/album/images?album=${ album.id }" />">
			                		<img src="<c:url value="/uploads/${ album.images[0].fichierImage }" />" style="width: 100%; height: 250px;" alt="Card Image" class="card-img-top">
			                	</a>
			                    <div class="card-body">
			                    	<p class="pull-right">
			                        	<a href="<c:url value="/user/album/image/add?album=${ album.id }" />" class="btn btn-success" title="Ajouter une image à cet album"><i class="fa fa-plus fa fa-image"></i></a>
			                        	<a href="#" class="btn btn-warning" title="Editer cet album"><i class="fa fa-edit"></i></a>
			                        	<a href="<c:url value="/user/album/delete?album=${ album.id }" />" onclick="return confirm('Voulez-vous vraiment supprimer cet album ?');" class="btn btn-danger sup" title="Supprimer cet album"><i class="fa fa-trash"></i></a>
			                        </p>
			                        <h6 class="card-title">
			                        	<a href="#" data-toggle="modal" data-target="#modal-<c:out value="${ album.id }" />"><c:out value="${album.titre }" /></a>
			                        </h6>
			                        <p class="text-muted card-text"><c:out value="${album.description }" /></p>
			                    </div>
					        	<!-- Modal -->
								<div id="modal-<c:out value="${ album.id }" />" class="modal fade" role="dialog">
								  <div class="modal-dialog">
								
								    <!-- Modal content-->
								    <div class="modal-content">
								      <div class="modal-header">
								        <button type="button" class="close" data-dismiss="modal">&times;</button>
								        <h4 class="modal-title">Informations de l'album</h4>
								      </div>
								      <div class="modal-body">
								      	<table class="table table-striped">
								      		<tr>
								      			<th>Titre</th>
								      			<th>Description</th>
								      			<th>Statut</th>
								      			<th>Propriétaire</th>
								      			<th>Date de création</th>
								      			<th>Images</th>
								      		</tr>
								      		<tr>
								      			<td><c:out value="${ album.titre }" /></td>
								      			<td><c:out value="${ album.description }" /></td>
								      			<td><c:out value="${ album.statut == 'publik' ? 'Public' : 'Privé' }" /></td>
								      			<td><c:out value="${ album.proprio.prenom } ${ album.proprio.nom }" /></td>
								      			<td><c:out value="${ album.proprio.createdAt }"/></td>
								      			<td><c:out value="${ album.images.size() }"/></td>
								      		</tr>
								      	</table>
								      </div>
								      <div class="modal-footer">
								        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								      </div>
								    </div>
								
								  </div>
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