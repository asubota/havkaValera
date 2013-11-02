ymaps.ready(init);

var myMap, currentPosition, placemarks;

function resizeMap() {
	jQuery('#map').css({width: window.innerWidth, height: (window.innerHeight - $('section.col-md-12').height()) });
}

function init(){ 
	resizeMap();
	
	myMap = new ymaps.Map ("map", {
  	center: [50.450949,30.522622],
    zoom: 15,
		autoFitToViewport: 'always'
  });
	
	myMap.controls.add(
    new ymaps.control.ZoomControl()
	);

	placemarks = new ymaps.GeoObjectCollection();
	navigator.geolocation.getCurrentPosition(show_map, handle_error);
	
	$(window).change(function() { 
		resizeMap();
	})
}

function handle_error(err) {
  if (err.code == 1) {
    alert('Похоже вы отказались от геолокации (мы и так вас найдем!)');
  }
}

function show_map(position) {
	currentPosition = position;
	
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
	placemarks.add(myPlacemark);
	myMap.geoObjects.add(placemarks);
}

function remove_old_placemarks() {
	// remove all old placemarks
	jQuery.each(placemarks, function(placemark) {
		myMap.geoObjects.remove(placemark);
	});
	
	placemarks = new ymaps.GeoObjectCollection();
}

function show_venues_on_map(venues) {
	// Creating a collection of geo objects.
	//var placemarks = new ymaps.GeoObjectCollection();
	var current_pos = new ymaps.Placemark([currentPosition.coords.latitude, currentPosition.coords.longitude], { 
		content: "Йя", 					
		balloonContent: "Йа"
	}, {
		  iconImageHref: '/current_pos_icon.png',
      iconImageSize: [37, 37]
		});

	placemarks.add(current_pos);
	
  jQuery.each(venues, function() {	
	  var placemark = new ymaps.Placemark([this.lat, this.lng], { content: this.title, balloonContent: this.title+'\n'+this.description });
	  placemarks.add(placemark);
	});

	// Adding the collection to the map.
	myMap.geoObjects.add(placemarks);
	// Setting the map center and zoom so that the entire collection is included.
	myMap.setBounds(placemarks.getBounds());	
};
 
function showObjectsNear(position) {
	var lat = position.coords.latitude,
  		lng = position.coords.longitude;

	var stub = [{
		title: 'Сказка Востока',
		lat: 50.324586,
		lng: 30.563057999999955,
		description: 'Столичное шоссе 35'
	}];
	
	//jQuery.get('/restaurants/'+lng+'/'+lat, function(resp) {
		remove_old_placemarks();
		show_venues_on_map(stub);
	//});
};

function showObjectsNearByCategory(category) {
	jQuery.get('/restaurants/'+category, function(resp) {
		remove_old_placemarks();

		//jQuery.each(resp.objects, function(venue) {
			remove_old_placemarks();
		  show_venues_on_map(stub);
		//});
	});
}