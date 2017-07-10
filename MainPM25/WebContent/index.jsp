<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>PM2.5监测与预测</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
	<link rel="stylesheet" href="assets/vendor/chartist/css/chartist-custom.css">
	<link rel="stylesheet" href="weather/weather.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700" rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="brand">
				<a href="index.html"><img src="assets/img/logo-dark.png" alt=" " class="img-responsive logo"></a>
			</div>
			<div class="container-fluid">
				<div class="navbar-btn">
				</div>
				<form class="navbar-form navbar-left">
					<div class="input-group">
					</div>
				</form>
				<div class="navbar-btn navbar-btn-right">
					<a class="btn btn-success update-pro" title="Upgrade to Pro" target="_blank" onclick="Logout();return true;"><i class="fa fa-rocket"></i> <span >退出登录</span></a>
				</div>
				<div id="navbar-menu">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="typography.jsp"><i class="lnr lnr-question-circle"></i> <span>Help</span></a>
						</li>
                       <li class="dropdown">
						<%if(session.getAttribute("username")==null){%><a href="Login.jsp" class="dropdown-toggle" data-toggle="dropdown"><img src="assets/img/user.png" class="img-circle" alt="Avatar"><span>登录<%}else %><a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="assets/img/user.png" class="img-circle" alt="Avatar"><%{out.print(session.getAttribute("username"));%></span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<li><a href="#"><i class="lnr lnr-user"></i> <span>My Profile</span></a></li>
								<li><a href="#"><i class="lnr lnr-envelope"></i> <span>Message</span></a></li>
								<li><a href="#"><i class="lnr lnr-cog"></i> <span>Settings</span></a></li>
							</ul><%} %>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
						<li><a href="index.jsp" class="active"><i class="lnr lnr-home"></i> <span>实时天气</span></a></li>
						<li><a href="charts.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>历史数据</span></a></li>
						<li><a href="tables.jsp" class=""><i class="lnr lnr-dice"></i> <span>预测数据</span></a></li>
						<li>
							<a href="#subPages" data-toggle="collapse" class="collapsed"><i class="lnr lnr-file-empty"></i> <span>登录注册</span> </a>
							<div id="subPages" class="collapse ">
								<ul class="nav">
									<li><a href="Login.jsp" class="">登录</a></li>
									<li><a href="Register.jsp" class="">注册</a></li>
								</ul>
							</div>
						</li>
						
						<li><a href="typography.jsp" class=""><i class="lnr lnr-text-format"></i> <span>网站说明</span></a></li>
					</ul>
				</nav>
			</div>
		</div>
		<!-- END LEFT SIDEBAR -->
<div class="main-content">
<div class="container-fluid">
  <div class="container">
    <div class="row">
      <div id="city"></div>
      <div id="temperature"></div>
    </div>
    <div id="results row">
      <div id="condition"></div>
      <div id="wind-speed"></div>
    </div>
    <div class="row">
      <a id="convert-button" class="btn btn-primary">°F / °C</a>
    </div>
    <div class="row">
      <div class="input-group">
        <input id="search-field" type="text" class="form-control" placeholder="Search for a city...">
      </div>
    </div>
  </div>
  </div>
  </div>
<footer class="footer">
  <div class="container-fluid text-center">
  <div class="footer-text text-muted row">
      <script>document.write(new Date().getFullYear());</script>  Data retrieved from
      <span>
        <a href="https://www.wunderground.com/?apiref=a74a4070cff097a0" target="_blank" alt="Weather Underground Logo">
          <img class="attribution-logo" src="https://icons.wxug.com/logos/PNG/wundergroundLogo_4c_rev_horz.png">
        </a>
      </span>
    </div>

  </div>
</footer>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script >
$(document).ready(function () {
	  getWeather();
	  // add a spinner icon to areas where data will be populated
	  $('#condition').html('<i class="fa fa-spinner fa-pulse fa-3x"></i>');
	  $('#wind-speed').html('<i class="fa fa-spinner fa-pulse fa-3x"></i>');
	});

	// Array for autocomplete cities and their weather stations
	var cities = [];

	// Get the weather from the Weather Underground API
	function getWeather(weatherStation) {
	  
	  var callback = '0fbee3746bac8150/conditions',
	      apiBaseUrl = 'https://api.wunderground.com/api/' + callback,
	      backgroundImgUrl = 'https://tylermoeller.github.io/local-weather-app/assets/img/';

	  // Build the appropriate URL if a specific city's weather station was requested.
	  if (!weatherStation) {
	    weatherApi = apiBaseUrl.replace('b', '9') + '/q/autoip.json';
	  } else {
	    weatherApi = apiBaseUrl.replace('b', '9') + weatherStation + '.json';
	  }

	  // Call the API
	  $.getJSON(weatherApi.replace('b', '9')).done(function (json) {
	    var weatherData = json.current_observation,
	        locData = weatherData.display_location,
	        condition = weatherData.weather,
	        windSpeed = Number((weatherData.wind_mph * 0.86897624190816).toFixed(1)), //mph to knots
	        windDir = weatherData.wind_dir;

	    // Values for the convert button
	    tempF = weatherData.temp_f,
	    tempC = weatherData.temp_c;

	    // If location has a value for "state", use it, otherwise use: city, country.
	    if (locData.state !== '') {
	      $('#city').text(locData.city + ', ' + locData.state + ', ' + locData.country_iso3166);
	    } else {
	      $('#city').text(locData.city + ', ' + locData.country_iso3166);
	    }

	    // categorize weather conditions to determine background image and icons
	    switch (true) {
	      case /thunderstorm|hail/i.test(condition):
	        display = 'thunderstorm';
	      break;
	      case /drizzle|light rain/i.test(condition):
	        display = 'sprinkle';
	      break;
	      case /rain|squalls|precipitation/i.test(condition):
	        display = 'rain';
	      break;
	      case /snow|ice|freezing/i.test(condition):
	        display = 'snow';
	      break;
	      case /overcast|mist|fog|smoke|haze|spray|sand|dust|ash/i.test(condition):
	        display = 'fog';
	      break;
	      case /cloud/i.test(condition):
	        display = 'cloudy';
	      break;
	      default:
	        display = 'clear';
	        if (!condition) condition = 'clear'; // handle undefined cases
	      break;
	    }

	    // Update background, wind speed, and icons based on weather conditions
	    $('body').css('background-image', 'url(' + backgroundImgUrl + display + '.jpg)');
	    if (display === 'clear') {
	      $('#condition').html('<i class="wi wi-night-' + display + '"></i><br><span class="description">' + condition + '</span>');
	    } else {
	      $('#condition').html('<i class="wi wi-' + display + '"></i><br><span class="description">' + condition + '</span>');
	    }

	    $('#wind-speed').html(
	      '<i class="wi wi-wind wi-from-' + windDir.toLowerCase() + '"></i><br><span class="description">' +
	      windDir + ' ' + windSpeed + ' knots</span>');

	    //determine F or C based on country and add temperature to the page.
	    var fahrenheit = ['US', 'BS', 'BZ', 'KY', 'PL'];
	    if (fahrenheit.indexOf(locData.country_iso3166) > -1) {
	      $('#temperature').text(tempF + '° F');
	    } else {
	      $('#temperature').text(tempC + '° C');
	    }
	    
	  // Scroll to the top of the page and remove focus from the
	  // search field to hide the keyboard on mobile
	  scroll(0, 0);
	  $('#search-field').blur();

	  }).fail(function (err, json) {
	    alert('There was an error retrieving your weather data. \n' +
	          'Please try again later or try a different city.');
	  });
	}

	$('#search-field').autocomplete({
	  autoFocus: false,
	  delay: 500,
	  focus: function (event, ui) {
	    $('#search-field').val(ui.item.value);
	  },
	  minLength: 3,
	  open: function () {
	    // prevent the need for double-tap on mobile to select menu item
	    $('.ui-autocomplete').off('menufocus hover mouseover');
	  },
	  select: function (event, ui) {
	    getWeather(cities[cities.indexOf(ui.item.value) + 1]);
	  },
	  source: cities,
	})
	.keyup(function (e) {
	  var key = e.keyCode || e.which,
	      cityAutoComplete = 'https://autocomplete.wunderground.com/aq?cb=?&query=' +
	                        $('#search-field').val();

	  // clear search field when user presses esc
	  if (key === 27) $('#search-field').val('');

	  // Update the autocomplete list when there are more than 2 characters and
	  // the user enters a backspace, space, comma, period, or letter.
	  if ($('#search-field').val().length > 2 &&
	     (key === 8 | key === 32 | key === 44 | key === 46) |
	     (key >= 65 && key <= 90) | (key >= 97 && key <= 122)) {

	    cities.length = 0; // clear the array for a new list of cities

	    // Push all autocomplete values to the cities array with their corresponding weather stations.
	    // Limit to 20 non-duplicate entries. The API also allows searches for "snow", for example,
	    // so we only allow values with a comma to show up in the autocomplete list.
	    $.getJSON(cityAutoComplete).done(function (data) {
	      $.each(data.RESULTS, function (i) {
	        var city = data.RESULTS[i].name;
	        if (city.indexOf(',') > -1 && cities.indexOf(city) < 0)  {
	          cities.push(city, data.RESULTS[i].l);
	        }
	      });
	    })
	    .fail(function (err) {
	      console.log('Error: ' + JSON.stringify(err));
	    });
	  }
	});

	//toggle between celsius / fahrenheit
	$('#convert-button').click(function () {
	  this.blur(); // remove focus from the button after click
	  if ($('#temperature').text().indexOf('F') > -1) {
	    $('#temperature').text(tempC + '° C');
	  } else {
	    $('#temperature').text(tempF + '° F');
	  }
	});

	$('#search-field').click(function () {
	  $(this).val('');
	  $('#search').autocomplete('close');
	});</script>

		<div class="clearfix"></div>
		<footer>
		</footer>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
	<script src="assets/vendor/chartist/js/chartist.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
	<script type="text/javascript" src="weather/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="weather/jquery-easyui-1.5.2/jquery.min.js"></script>
	<script type="text/javascript" src="weather/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
	<script >
	function Logout(){
		session.invalidate();
		response.sendRedirect("index.jsp");
	}
	</script>
</body>
</html>