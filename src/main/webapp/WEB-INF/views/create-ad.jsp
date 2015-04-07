<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Recognize.Me AdServer!</title>
	<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="resources/js/index.js"></script>
	<script type="text/javascript" src="resources/js/ads.js"></script>
	<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places"></script>
	<script>
	function initialize() {
		var mapOptions = {
				center : new google.maps.LatLng(37.7749, -122.4194200),
				zoom : 13,
				mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

		var input = document.getElementById('searchTextField');
		var autocomplete = new google.maps.places.Autocomplete(input);

		autocomplete.bindTo('bounds', map);

		var infowindow = new google.maps.InfoWindow();
		var marker = new google.maps.Marker({
			map : map
		});

		google.maps.event
		.addListener(
				autocomplete,
				'place_changed',
				function() {
					infowindow.close();
					marker.setVisible(false);
					input.className = '';
					var place = autocomplete.getPlace();
					if (!place.geometry) {
						// Inform the user that the place was not found and return.
						input.className = 'notfound';
						return;
					}

					// If the place has a geometry, then present it on a map.
					if (place.geometry.viewport) {
						map.fitBounds(place.geometry.viewport);
					} else {
						map.setCenter(place.geometry.location);
						map.setZoom(17); // Why 17? Because it looks good.
					}
					marker.setIcon( /** @type {google.maps.Icon} */
							({
								url : place.icon,
								size : new google.maps.Size(71, 71),
								origin : new google.maps.Point(0, 0),
								anchor : new google.maps.Point(17, 34),
								scaledSize : new google.maps.Size(35, 35)
							}));
					marker.setPosition(place.geometry.location);
					marker.setVisible(true);
					document.getElementById('locationLat').value = place.geometry.location.lat();
					document.getElementById('locationLong').value = place.geometry.location.lng();

					var address = '';
					if (place.address_components) {
						address = [
						           (place.address_components[0]
						           && place.address_components[0].short_name || ''),
						           (place.address_components[1]
						           && place.address_components[1].short_name || ''),
						           (place.address_components[2]
						           && place.address_components[2].short_name || '') ]
						.join(' ');
					}

					infowindow.setContent('<div><strong>' + place.name
							+ '</strong><br>' + address);
					infowindow.open(map, marker);
				});

		// Sets a listener on a radio button to change the filter type on Places
		// Autocomplete.
		function setupClickListener(id, types) {
			var radioButton = document.getElementById(id);
			google.maps.event.addDomListener(radioButton, 'click', function() {
				autocomplete.setTypes(types);
			});
		}

		setupClickListener('changetype-all', []);
		setupClickListener('changetype-establishment', [ 'establishment' ]);
		setupClickListener('changetype-geocode', [ 'geocode' ]);
	}
	google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
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
				        <div><p style="font-size:20px;">Create Advertisement</p></div>
						
						<form class="form-horizontal" style="margin-top: 30px;"
							id="createAdForm" method="POST">
							<div class="form-group">
								<label for="brandname" class="col-sm-2 control-label">Brand</label>
								<div class="col-sm-5">
									<select class="form-control" id="brandid" name="brandid">
										<c:forEach var="brand" items="${brands}">
		        							<option value="${brand.id}">${brand.name}</option>
		    							</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">Name</label>
								<div class="col-sm-10">
									<input type="text" name="name" class="form-control" id="name" required="true" placeholder="Enter the advertisement name">
								</div>
							</div>
							<div class="form-group">
								<label for="url" class="col-sm-2 control-label">URL</label>
								<div class="col-sm-10">
									<input type="url" name="url" class="form-control" id="url" required="true" placeholder="Enter a URL">
								</div>
							</div>
							<div class="form-group">
								<label for="region" class="col-sm-2 control-label">Region</label>
								<div class="col-sm-5">
									<select class="form-control" id="region" name="region">
										<option>EN-US</option>
										<option>EN-UK</option>
										<option>French</option>
										<option>Spanish</option>
										<option>Hindi</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="category" class="col-sm-2 control-label">Category</label>
								<div class="col-sm-5">
									<select class="form-control" id="categoryid" name="categoryid">
										<c:forEach var="category" items="${categories}">
		        							<option value="${category.id}">${category.name}</option>
		    							</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="content" class="col-sm-2 control-label">Content</label>
								<div class="col-sm-5">
									<input type="file" id="content" class="form-control" name="content">
									<p class="help-block">Choose the file to be used as advertisement here..</p>
								</div>
							</div>
							<div class="form-group">
								<label for="type" class="col-sm-2 control-label">Content
									Type</label>
								<div class="col-sm-5">
									<select class="form-control" id="contenttypeid" name="contenttypeid">
										<c:forEach var="contenttype" items="${contenttypes}">
		        							<option value="${contenttype.id}">${contenttype.contentType}</option>
		    							</c:forEach>
									</select>
								</div>
							</div>
							<input type="hidden" id="locationLat" name="locationLat" />
							<input type="hidden" id="locationLong" name="locationLong" />  
							<div class="form-group">
								<label for="region" class="col-sm-2 control-label">Location</label>
								<div class="col-sm-10">
									<div class="radio" id="panel" >
										<input id="searchTextField" type="text" size="50" class="form-control" required="true" >
									  	<label>
									    	<input type="radio" name="type" id="changetype-all" value="all" checked>
									    	All
									  	</label>
									</div>
									<div class="radio">
									  	<label>
									    	<input type="radio" name="type" id="changetype-establishment" value="establishments">
									    	Establishments
									  	</label>
									</div>
									<div class="radio disabled">
									  	<label>
									    	<input type="radio" name="type" id="changetype-geocode" value="geocodes">
									    	Geocodes
									  	</label>
									</div>
								</div>
								<div class="col-sm-offset-2 col-sm-10">
									<div id="map-canvas"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-primary">Create Advertisement</button>
								</div>
							</div>
						</form>
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