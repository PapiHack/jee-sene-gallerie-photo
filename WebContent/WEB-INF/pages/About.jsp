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
					Cette petite application permet d'éffectuer des opérations de type
					CRUD (Create Read Update Delete) afin d'enregistrer, lister et / ou modifier
					des clients.
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
					  <div class="panel-heading">Tableau descriptif de l'architecture technique de l'application</div>
					
					  <!-- Table -->
					  <table class="table table-striped table-bordered">
					  	<tr>
					  		<th><p>Package</p></th>
					  		<th><p>Description</p></th>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>meissa.beans</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient les beans ou entités également appelé classe métier 
					  				de l'application.
					  			</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>meissa.managers</i></strong></p></td>
					  		<td>
						  		<p>
						  			Correspond à la couche <strong>DAO</strong> de l'application et contient les 
						  			classes permettant de persister les entités (beans) correspondant 
						  			en base de donnée.
						  		</p>  
							</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>meissa.metier</i></strong></p></td>
					  		<td>
						  		<p>
						  			Correspond à la couche métier et contient dans notre cas une classe 
						  			permettant de vérifier la validité des données des entitiés.
						  		</p>
					  		</td>
					  	</tr>
					  	<tr>
					  		<td><p><strong><i>meissa.servlets</i></strong></p></td>
					  		<td>
					  			<p>
					  				Contient les servlets de l'application et font office de controller. 
					  				Ce package correspond à la couche  <strong>Controller</strong>.
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