<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me Ad-Server!</title>
		
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/js/ads.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="resources/css/home.css">
	</head>
	<body>
	    <nav class="navbar transparent navbar-deafult navbar-fixed-top" role="navigation">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/adserver">Recognize.Me Ads</a>
				</div>
	
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">Hello, Admin <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Profile</a></li>
								<li><a href="#">Logout</a></li>
							</ul>
						</li>
					</ul>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container-fluid -->
		</nav>
	    
	    <div class="container">
	    	<div class="row">
	        	<h4>Advertisement</h4>
	        	<div>
	        		<table id="ads-table" class="table table-striped">
	        			<thead>
	        				<tr>
	        					<th>Name</th>
	        					<th>URL</th>
	        					<th>Region</th>
	        					<th>Category</th>
	        					<th>Content Type</th>
	        				</tr>
	        			</thead>
	        			<tbody>
	        				<c:forEach var="ad" items="${ads}">
		        				<tr>
		        					<td><c:out value="${ad.name}"></c:out></td>
		        					<td><c:out value="${ad.url}"></c:out></td>
		        					<td><c:out value="${ad.region}"></c:out></td>
		        					<td><c:out value="${ad.category.name}"></c:out></td>
		        					<td><c:out value="${ad.contentType.contentType}"></c:out></td>
		        				</tr>
	        				</c:forEach>
	        			</tbody>
	        		</table>
	        	</div>    
		    </div>
	    </div>
	</body>
</html>