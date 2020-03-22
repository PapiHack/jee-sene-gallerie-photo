<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>SGP - A Propos</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/bootstrap/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/staticfiles/font-awesome/css/font-awesome.min.css"/>"/>
</head>
<body>
	<c:import url="../partials/nav.jsp"/>
	
	<div class="container">
		<div class="row">
			<div class="col-lg-12 jumbotron">
				<h1>A propos de cette application.</h1>
				<p>
					Cette petite application baptisé <strong>#SenGalleriePhoto</strong> permet le partage d'album photo entre ses différents utilisateurs.
					Ces utilisateurs sont de deux (2) types : <strong>Administrateur</strong> et <strong>Simple utilisateur</strong>.
					On distingue également les visiteurs de la plateforme qui ont la possibilité de consulter les albums définit comme
					étant public et de visualiser les photos de ce dernier. Afin de créer et gérer ses albums, il devra s'inscrire.
					L'administrateur gére les utilisateurs, mais peut également créer et partager des albums comme les utilisateurs simples disposant d'un compte.
					Lors de la création d'un album, son propriétaire peut définir ce dernier comme étant public ou privé. S'il est public, 
					tout le monde pourra y accéder y compris les visiteurs non inscrit. Cependant, s'il est privé seul les utilisateurs autorisés à y
					accéder peuvent le visualiser. 
				</p>
					<p><strong>Stack Technique :</strong></p>
					<ul>
						<li><p>Java JEE</p></li>
						<li><p>JSP, JSTL</p></li>
						<li><p>Bootstrap 3</p></li>
						<li><p>MySQL</p></li>
					</ul>
					<p><strong>Architecture Technique :</strong></p>
					<div class="panel panel-danger">
					  <!-- Default panel contents -->
					  <div class="panel-heading">
					  	<h3>Tableau descriptif de l'architecture technique de l'application</h3>
					  </div>
					
					  <!-- Table -->
					  <table class="table table-striped table-bordered">
					  	<tr>
					  		<th><p><strong>Package</strong></p></th>
					  		<th><p><strong>Description</strong></p></th>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.beans</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient les beans ou entités également appelé classe métier 
					  				de l'application.
					  			</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.managers</i></strong></p></td>
					  		<td>
						  		<p>
						  			Correspond à la couche <strong>DAO</strong> de l'application et contient les 
						  			classes permettant de persister les entités (beans) correspondant 
						  			en base de donnée.
						  		</p>  
							</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.validators</i></strong></p></td>
					  		<td>
						  		<p>
						  			Correspond à la couche métier et contient dans notre cas des classes 
						  			permettant de vérifier la validité des entitiés.
						  		</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.servlets</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient les servlets de l'application et font office de controller. 
					  				Ce package correspond à la couche  <strong>Controller</strong>.
					  			</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.utils</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient quelques classes utilitaires facilitant le développement tout en me permettant
					  				de dupliquer du code unitilement. 
					  			</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>sn.sgp.filters</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient des filtres qui sont des sortes de <strong>middleware</strong> permettant d'effectuer des
					  				opérations entre la requête et la réponse. C'est là où je définis certaintes restrictions quant aux accés
					  				à certaines ressources. 
					  			</p>
					  		</td>
					  	</tr>
					  </table>
					</div>
					<p class="small pull-right">By <strong>M.B.C.M</strong> - UCAD/ESP/DGI/M2GLSI</p>
			</div>
		</div>
	</div>
	<c:import url="../partials/footer.jsp"/>
	<script type="text/javascript" src="<c:url value="/staticfiles/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/staticfiles/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>