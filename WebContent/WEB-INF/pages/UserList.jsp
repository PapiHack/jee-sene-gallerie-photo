<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SGP - Liste des ${ path == '/user/list' ? 'utilisateurs' : 'administrateurs' }</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/other_css/datatables.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/sweet-alert/sweetalert.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	
		<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 align="center">Liste des ${ path == '/user/list' ? 'utilisateurs' : 'administrateurs' }</h1>
			</div>
		</div>
		<c:if test="${ userAdd != null }">
			<div class="col-md-offset-2 alert alert-success alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ userAdd }"></c:out> </h4>
			</div>
		</c:if>
		<c:if test="${ userDelete != null }">
			<div class="col-md-offset-2 alert alert-danger alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ userDelete }"></c:out> </h4>
			</div>
		</c:if>
		<c:if test="${ userUpdate != null }">
			<div class="col-md-offset-2 alert alert-warning alert-dismissable col-md-8">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4> <c:out value="${ userUpdate }"></c:out> </h4>
			</div>
		</c:if>
		<div class="row">
			<div class="col-lg-offset-2 col-lg-8">
			<c:if test="${ !empty users }">
					<div class="panel panel-default">
					  <!-- Default panel contents -->
					  <div class="panel-heading">
					  	<h2>Liste de tous les ${ path == '/user/list' ? 'utilisateurs' : 'administrateurs' } et leurs informations</h2>
					  	<p class="text-center"><a class="btn btn-success" href="<c:url value="/user/add" />">Ajouter un nouvel utilisateur <i class="fa fa-user-plus"></i></a></p>	
					  </div>
					
					  <!-- Table -->
					  <table class="table table-striped table-bordered" id="myTable">
					  	<tr>
					  		<th>Nom</th>
					  		<th>Prénom</th>
					  		<th>Email</th>
					  		<th>Login</th>
					  		<th>Statut</th>
					  		<th>Opérations</th>
					  	</tr>
					  	<c:forEach items="${ users }" var="user">
					  		<tr>
					  			<td><c:out value="${ user.nom }" /></td>
					  			<td><c:out value="${ user.prenom }" /></td>
					  			<td><c:out value="${ user.email }" /></td>
					  			<td><c:out value="${ user.compte.login }" /></td>
					  			<td><c:out value="${ user.statut == 'admin' ? 'Administrateur' : 'Utilisateur' }" /></td>
					  			<td>
					  				<a class="btn btn-warning" title="Editer" 
					  				   href="<c:url value="/user/update?user=${ user.id }"/>">
					  				   <i class="fa fa-edit"></i>
					  				</a>
					  				<a class="btn btn-danger sup" title="Supprimer"
					  					onclick="return confirm('Voulez-vous vraiment supprimer cet utilisateur ?');"
					  					href="<c:url value="/user/delete?user=${ user.id }" />">
					  				   <i class="fa fa-trash"></i>
					  				</a>
					  			</td>
					  		</tr>
					  	</c:forEach>
					  </table>
					</div>
					</c:if>
					<c:if test="${ empty users }">
						<h2 align="center">Pas encore d'utilisateurs !</h2>
						<p class="text-center"><a class="btn btn-success" href="<c:url value="/user/add" />">Ajouter un nouvel utilisateur <i class="fa fa-user-plus"></i></a></p>
					</c:if>
			</div>
		</div>
	</div>
	
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/sweet-alert/sweetalert.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/datatables.min.js"/>"></script>
	
	<script>
		$(document).ready(function () {
		    $('#myTable').DataTable();
		});
		
	</script>
</body>
</html>