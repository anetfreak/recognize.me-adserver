<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recognize.Me Ad-Server!</title>

<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="resources/bootstrap/js/bootstrap.min.js"></script>
<script
	src="http://maps.google.com/maps/api/js?sensor=false&libraries=places"></script>
<script>
	function initialize() {
		var mapOptions = {
			center : new google.maps.LatLng(-33.8688, 151.2195),
			zoom : 13,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var input = /** @type {HTMLInputElement} */
		(document.getElementById('searchTextField'));
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
<link rel="stylesheet"
	href="resources/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="resources/css/home.css">
</head>
<body>
	<nav class="navbar transparent navbar-deafult navbar-fixed-top"
		role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/adserver">Recognize.Me Ads</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Hello, Admin <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Profile</a></li>
						<li><a href="#">Logout</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>

	<div class="container">
		<div class="row">
			<div class="col-md-8">
				<h4>Post a new advertisement</h4>
				<form class="form-horizontal" style="margin-top: 30px;"
					id="createAdForm">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">Name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="name"
								placeholder="Ad Name">
						</div>
					</div>
					<div class="form-group">
						<label for="url" class="col-sm-2 control-label">URL</label>
						<div class="col-sm-10">
							<input type="url" class="form-control" id="url" placeholder="URL">
						</div>
					</div>
					<div class="form-group">
						<label for="region" class="col-sm-2 control-label">Region</label>
						<div class="col-sm-5">
							<select class="form-control" id="region">
								<option>EN-US</option>
								<option>EN-UK</option>
								<option>French</option>
								<option>Spanish</option>
								<option>Hindi</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="region" class="col-sm-2 control-label">Location</label>
						<div class="col-sm-5">
							<div id="panel">
								<input id="searchTextField" type="text" size="50"> <input
									type="radio" name="type" id="changetype-all" checked="checked">
								<label for="changetype-all">All</label> <input type="radio"
									name="type" id="changetype-establishment"> <label
									for="changetype-establishment">Establishments</label> <input
									type="radio" name="type" id="changetype-geocode"> <label
									for="changetype-geocode">Geocodes</label>
							</div>
							<div id="map-canvas"></div>
						</div>
					</div>
					<div class="form-group">
						<label for="category" class="col-sm-2 control-label">Category</label>
						<div class="col-sm-5">
							<select class="form-control" id="category">
								<option>Promotion</option>
								<option>New Launch</option>
								<option>Regular</option>
								<option>Special Campaign</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="content" class="col-sm-2 control-label">Content</label>
						<div class="col-sm-10">
							<input type="file" id="content">
							<p class="help-block">Choose the file to be used as
								advertisement here..</p>
						</div>
					</div>
					<div class="form-group">
						<label for="type" class="col-sm-2 control-label">Content
							Type</label>
						<div class="col-sm-5">
							<select class="form-control" id="type">
								<option>Video</option>
								<option>Audio</option>
								<option>Text</option>
								<option>Email</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">Create</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>