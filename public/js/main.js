ymaps.ready(init);

var myMap, placemarks = [];

function init(){     
	myMap = new ymaps.Map ("map", {
  	center: [50.450949,30.522622],
    zoom: 15,
  });
	
	navigator.geolocation.getCurrentPosition(show_map);
}

function show_map(position) {
  var lat = position.coords.latitude;
  var long = position.coords.longitude;
  // let's show a map or do something interesting!

  myMap.panTo([lat, long], {
		duration: 2000,
		callback: function () {
     showObjectsNear(position);
		}
	});

	var myPlacemark = new ymaps.Placemark([lat, long]);
	placemarks.push(myPlacemark);
	myMap.geoObjects.add(myPlacemark);
}

function show_venue_on_map(venue) {
	var placemark = new ymaps.Placemark([venue.lat, venue.long], { content: venue.title, balloonContent: venue.description });
	placemarks.push(placemark);
	myMap.geoObjects.add(placemark);
}

function showObjectsNear(position) {
	var lat = position.coords.latitude;
  var long = position.coords.longitude;

	jQuery.post('/location/'+long+'/'+lat, function(resp) {
		// remove all old placemarks
		jQuery.each(placemarks, function(placemark) {
			myMap.geoObjects.remove(placemark);
		});
		
		jQuery.each(resp.objects, function(venue) {
			show_venue_on_map(venue);
		});
	});
}

function showObjectsNearByCategory(category) {
	jQuery.post('/restaurants/'+category, function(resp) {
		// remove all old placemarks
		jQuery.each(placemarks, function(placemark) {
			myMap.geoObjects.remove(placemark);
		});

		jQuery.each(resp.objects, function(venue) {
			show_venue_on_map(venue);
		});
	});
}