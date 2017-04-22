<!DOCTYPE html>
<html>
  <head>
    <style>
      #map {
        height: 580px;
        width: 570px;
       }
    </style>
  </head>
  <body>
	<h3 style="text-align:left;float:left;">Call us on</h3> 
	<h3 style="text-align:center;float:center;">Come find us at</h3>
	<h3 style="text-align:right;float:right;">Email us at</h3>  
	<hr style="clear:both;"/>
	
    <div id="map"></div>
    <script>
      function initMap() {
        var address = {lat: 42.664308, lng: 23.287974};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 17,
          center: address
        });
        var marker = new google.maps.Marker({
          position: address,
          map: map
        });
      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBZ6EnlsEf6hunD_gPyE4x60vhmoskD9M0&callback=initMap">
    </script>
  </body>
</html>
