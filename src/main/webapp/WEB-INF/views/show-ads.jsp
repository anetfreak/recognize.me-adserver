<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ page isELIgnored="false" %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me AdServer!</title>
		
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/datepicker/js/bootstrap-datepicker.js"></script>
		<script type="text/javascript" src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<script type="text/javascript" src="resources/js/ads.js"></script>
		
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="resources/datepicker/css/datepicker.css">
		<link rel="stylesheet" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
		<link rel="stylesheet" href="resources/css/index.css">
	</head>
	<body>
	   <!-- #wrapper -->
		<div id="wrapper">
			<div class="overlay"></div>
			<%@include file="/WEB-INF/views/header.jsp"%>
			
			<!-- Page Content -->
			<div id="page-content-wrapper">
				<button type="button" class="hamburger is-closed" data-toggle="offcanvas">
					<span class="hamb-top"></span>
					<span class="hamb-middle"></span>
					<span class="hamb-bottom"></span>
				</button>
				
				<!-- Container -->
				<div class="container">
			    	<div class="row">
			        	<div class="col-lg-8 col-lg-offset-1">
							<h3>Recognize.me AdServer</h3>
							<hr>
					        <div><p style="font-size:20px;">Advertisements</p></div>
			        	
				        	<div>
				        		<table id="ads-table" class="table table-striped table-bordered table-condensed">
				        			<thead>
				        				<tr>
				        					<th>Name</th>
				        					<th>URL</th>
				        					<th>Brand</th>
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
					        					<td><c:out value="${ad.brand.name}"></c:out></td>
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
			    </div>
				<!-- End Container -->
		
			</div>
			<!-- /#page-content-wrapper -->
			<%@include file="/WEB-INF/views/footer.jsp"%>
		</div>
		<!-- /#wrapper --> 
	    
	</body>
</html>