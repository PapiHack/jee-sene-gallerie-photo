<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/home"/>">Sène Gallerie Photo (SGP)</a>
    </div>
	
	<c:set var="path" value="${ path }" scope="request" />
	<c:set var="user" value="${ user }" scope="session" />
	
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="<c:out value="${ path == '/home' ? 'active' : '' }" />"><a href="<c:url value="/home"/>">Accueil <span class="sr-only">(current)</span></a></li>
        <c:if test="${ user == null }">        
        	<li class="<c:out value="${ path == '/gallery' ? 'active' : '' }" />"><a href="<c:url value="/gallery"/>">Gallerie</a></li>
        	<li class="<c:out value="${ path == '/login' ? 'active' : '' }" />"><a href="<c:url value="/login"/>">Se connecter</a></li>
        	<li class="<c:out value="${ path == '/register' ? 'active' : '' }" />"><a href="<c:url value="/register"/>">S'inscrire</a></li>
        </c:if>
        <c:if test="${ user != null }">        
        	<li class="<c:out value="${ path == '/user/gallery' ? 'active' : '' }" />"><a href="<c:url value="/user/gallery"/>">Gallerie</a></li>
        </c:if>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="<c:out value="${ path == '/about' ? 'active' : '' }" />"><a href="<c:url value="/about"/>">A Propos</a></li>
        <c:if test="${ user != null }">
		   <li class="<c:out value="${ path == '/user/albums' ? 'active' : '' }" />"><a href="<c:url value="/user/albums" />">Mes albums</a></li>
		   <c:if test="${ user.statut == 'admin' }">
	        	<li class="dropdown <c:out value="${ path == '/user/list' || path == '/user/list-admin' ? 'active' : '' }" />">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded=false>Gestion des utilisateurs <span class="caret"></span></a>
			          <ul class="dropdown-menu">
			            <li><a href="<c:url value="/user/list" />">Gestion des utilisateurs simples</a></li>
			            <li><a href="<c:url value="/user/list-admin" />">Gestion des administrateurs</a></li>
			          </ul>
	        	</li>
		   </c:if>
        	<li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded=false><c:out value="${ user.compte.login }" /> <span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="<c:url value="/user/my-profile" />">Mon profil</a></li>
		            <li><a href="<c:url value="/user/parametre" />">Paramètres</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="<c:url value="/user/deconnexion" />">Se déconnecter</a></li>
		          </ul>
        	</li>
        </c:if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>