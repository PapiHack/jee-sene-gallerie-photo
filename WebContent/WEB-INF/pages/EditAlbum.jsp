<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - Edition d'un album</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/styles.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/dropzone/dropzone.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/select2-bootstrap.min.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 align="center">Veuillez saisir les informations de l'album</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<div class="panel panel-default">
					<div class="panel panel-heading">
						Edition d'un album
					</div>
					<div class="panel panel-body">
						<form enctype="multipart/form-data" id="form" action="<c:url value="/user/album/update" />" method="post">
			                
				            <c:if test="${ errors.vide != null }">
								<p class="error"><c:out value="${ errors.vide }"/></p>
							</c:if>
			                <div class="form-group">
			                    <label for="titre">Titre</label>
						            <c:if test="${ errors.titre != null }">
										<p class="error"><c:out value="${ errors.titre }"/></p>
									</c:if>
			                    <input type="text" class="form-control" id="titre" name="titre" value="<c:out value="${ form.titre }"/>" placeholder="Titre de l'album...">
			                </div>
			                <div class="form-group">
			                    <label for="description">Description</label>
					            <c:if test="${ errors.description != null }">
									<p class="error"><c:out value="${ errors.description }"/></p>
								</c:if>
			                    <textarea class="form-control" id="description" placeholder="Description de l'album..." name="description"><c:out value="${ form.description }"/></textarea>
			                	<div class="invalid-feedback" id="description" style="display: none;">Veuillez saisir tous les champs</div>
			                </div>
			                <div class="form-group">
			                    <label for="statut">Statut</label>
			                    <select id="statut" name="statut" class="form-control">
			                    	<option value="public" <c:out value="${ form.statut == 'public' ? 'selected' : '' }"/> >Public</option>
			                    	<option value="prive" <c:out value="${ form.statut == 'privé' ? 'selected' : '' }"/> >Privé</option>
			                    </select>
			                </div>
			                <div class="form-group" id="usersAuth" style="display: none;">
			                    <label for="users">Utilisateurs authorisés à accéder à cet album</label>
			                    <select id="users" name="users" multiple class="form-control">
			                    <c:if test="${ usersAuth != null }">
	        						<c:forEach items="${ usersAuth }" var="user">
			                    		<option value="<c:out value="${ user.id }" />" selected><c:out value="${ user.prenom } ${ user.nom } (${ user.compte.login })"  /> </option>
	        						</c:forEach>
								</c:if>
			                    <c:if test="${ otherUsers != null }">
	        						<c:forEach items="${ otherUsers }" var="user">
			                    		<option value="<c:out value="${ user.id }" />" ><c:out value="${ user.prenom } ${ user.nom } (${ user.compte.login })"  /> </option>
	        						</c:forEach>
								</c:if>
			                    <c:if test="${ users != null }">
	        						<c:forEach items="${ users }" var="user">
			                    		<option value="<c:out value="${ user.id }" />" ><c:out value="${ user.prenom } ${ user.nom } (${ user.compte.login })"  /> </option>
	        						</c:forEach>
								</c:if>
			                    </select>
			                </div>
			                <div class="form-group">
			                    <a href="<c:url value="/user/home"/>" class="btn btn-danger btn-md">Annluer</a>
			                    <button type="submit" id="btnSubmit" class="btn btn-success btn-md">Editer</button>
			                </div>
			                   <input type="hidden" name="userId" value="${ sessionScope.user.id }" />
			                   <input type="hidden" name="albumId" value="${ requestScope.album.id }" />
            			</form>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/select2.min.js"/>"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		if($('#statut').val() == 'prive') {
			$('#usersAuth').css('display', 'block');
			$('#users').select2();
		}
	});
		$('#statut').on('change', event => {
			if(event.target.value === 'prive') {
				$('#usersAuth').css('display', 'block')
				$('#users').select2()
			}
			else {
				$('#usersAuth').css('display', 'none')
			}
		})
	</script>
</body>
</html>