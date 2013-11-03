ymaps.ready(init);

var myMap, currentPosition, placemarks;


function init(){ 
	
	myMap = new ymaps.Map ("yamap", {
  	center: [50.450949,30.522622],
    zoom: 15,
		autoFitToViewport: 'always'
  });
	
	myMap.controls.add(
    new ymaps.control.ZoomControl()
	);

	myMap.events.add('click', function (e) {
		currentPosition = {coords: {latitude: e.get('coordPosition')[0], longitude: e.get('coordPosition')[1]}};
		show_where_am_i(currentPosition);
	});

	placemarks = new ymaps.GeoObjectCollection();
	navigator.geolocation.getCurrentPosition(show_map, handle_error);
	

	
	$('.where_am_i').click(function() {
		navigator.geolocation.getCurrentPosition(show_where_am_i, handle_error);
	});
	
	$('.cousins li a').click(function(){
		var category_id = $(this).data('category-id');
		showObjectsNearByCategory(category_id);
	});

    $('.hide_menu').click(function( e ){

        $( e.target ).parent().slideUp();
    });

}

function handle_error(err) {
  if (err.code == 1) {
    alert('Похоже вы отказались от геолокации (мы и так вас найдем!)');
  }
};

function show_where_am_i(pos) {
	currentPosition = pos;
	var lat = pos.coords.latitude;
  var long = pos.coords.longitude;

	var myPlacemark = new ymaps.Placemark([lat, long]);
	placemarks.add(myPlacemark);
	myMap.geoObjects.add(placemarks);

  myMap.panTo([lat, long], {
		duration: 1000,
		callback: function () {
     showObjectsNear(pos);
		}
	});
};

function show_map(position) {
	currentPosition = position;
	show_where_am_i(currentPosition);
	showObjectsNear(position)
};

function remove_old_placemarks() {
	myMap.geoObjects.remove(placemarks);
	placemarks = new ymaps.GeoObjectCollection();
}

function show_venues_on_map(venues) {
  RRestBox.setState({filterCat: undefined,
                     data: venues});
  
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
        var placemark = new ymaps.Placemark([this.lat, this.lng], { content: this.title, balloonContent: this.title+'<br/><small>'+this.address.street+'</small>' });
        placemark.venus = this;
        placemark.events.add('click', function ( e ) {
            restDetailer( e._fb.target.venus );
        });
        placemarks.add(placemark);
	});

	// Adding the collection to the map.
	myMap.geoObjects.add(placemarks);
	if(placemarks.length > 1) {
	  // Setting the map center and zoom so that the entire collection is included.
	  myMap.setBounds(placemarks.getBounds());
	}
};

var currentRest = {};

function showRestaurantBlock(id){
	var restaurant_block = jQuery('#restaurant-block');
	jQuery.get('/restaurant/'+id, function(resp) {
		console.log( resp );
        currentRest = resp;
		restaurant_block.find('#restaurant-logo').attr('src', resp.logo.src);
		restaurant_block.find('#restaurant-name').html(resp.name);
        restaurant_block.find('#restaurant-description').html(resp.info.note);

        var html = '';
        for( var i = 0; i < resp.menu.length; i++ ){
            html += '<li><a href="#" class="add_to_menu" data-action="addToOrder" data-mid="'+resp.menu[i].key+'">' +
            '<h5>'+resp.menu[i].name+'</h5><span>'+resp.menu[i].description+'</span>' +
            '</a></li>';
        }
        $("#restaurant-block").children(".menu").children("ul").html( html );

        $("#restaurant-block").show();
	});	
} 

function showObjectsNear(position) {
	var lat = position.coords.latitude,
  		lng = position.coords.longitude;
	
	jQuery.get('/restaurant/'+lng+'/'+lat+'/2000', function(resp) {
		remove_old_placemarks();
		show_venues_on_map(resp);
	});
};

function showObjectsNearByCategory(category_id) {
	jQuery.get('/restaurant/'+category_id, function(resp) {
		remove_old_placemarks();
		show_venues_on_map(resp);
	});
}

function reverse_geocode(position) {
	if(!position) {
		var position = currentPosition;
	}
  var lat = position.coords.latitude;
  var lng = position.coords.longitude;

  // 'http://maps.google.com/maps/geo?q='+lat+','+lon+'&output=jsonp&sensor=false&callback=parse_jsonp' v2
  // http://maps.googleapis.com/maps/api/geocode/json?latlng=49.8295075,24.0086034&sensor=false v3
  jQuery.getJSON('/reverse_geocode/'+lat+'/'+lng, function(result){
	  return result.results[0].formatted_address;
  });
}

function restDetailer( rest ){
    showRestaurantBlock( rest._id );
}

jQuery(document).click(function(e){
    var action = getActionElement( e.target );
    if( action !== undefined ){
        e.preventDefault();
        window[action]( e );
    }

    return false;
});

var actionElement;

function getActionElement( element ){
    var action = $( element ).attr( "data-action" );

    if( action === undefined && element.parentElement !== null ){
        return getActionElement( element.parentElement );
    }else if( action === undefined && element.parentElement === null ){
        return undefined;
    }else{
        actionElement = element;
        return action;
    }
}

var order = [];

function addToOrder( e ){
    $("#order-block").show();
    order.push( $(actionElement).attr("data-mid") );
}

