ymaps.ready(init);
var myMap;

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

  myMap.panTo([lat, long], {duration: 2000});

	myPlacemark = new ymaps.Placemark([lat, long]);
	
	myMap.geoObjects.add(myPlacemark);
}