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
  	<div class="contact-us">
  		<h3 class="contact-call">Call us on: +359 876 10 10 22 </h3> 
		<h3 class="contact-find">Come find us at: Bulgaria Blvd. Infinity Tower 14 floor</h3>
		<h3 class="contact-email">Email us at: contact@freeagents.com</h3>  
	<hr style="clear:both;"/>
  	</div>
    <div id="map" style="margin: 0 auto;"></div>
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
