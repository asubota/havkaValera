
var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://127.0.0.1:27017/havka', function(err, db) {
    if(err) throw err;

    //*************
    // TOWNS
    //*************

    var townsCollection = db.collection('towns');
    var towns = [
        {
            name        : "Киев",
            description : "Мать городов русских"
        },
        {
            name        : "Гомель",
            description : "Дыра"
        }
    ];
    townsCollection.remove(function(){
        console.log( ">>>>>> Towns Cleared" );
        townsCollection.insert( towns, function( err, docs ){
            console.log( ">>>>>> Towns Insertion" );
            if( err ){
                console.log( err );
            }
            console.log( "Towns inserted: " + docs.length );
        });
    });

    //*************
    // TOWNS END
    //*************



    //*************
    // Restaurants
    //*************
    var restaurantsCollection = db.collection('restaurants');
    var restaurants = require( './assets/restaurants' );

    restaurantsCollection.remove(function(){
        console.log( ">>>>>> restaurants Cleared" );
        restaurantsCollection.insert( restaurants, function( err, docs ){
            console.log( ">>>>>> restaurants Insertion" );
            if( err ){
                console.log( err );
            }
            console.log( "restaurants inserted: " + docs.length );
        });
    });
    //*************
    // Restaurants END
    //*************

});
