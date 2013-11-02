
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
    var restaurants = [
        {
            'title': 'Сказка Востока',
            'logo': {
                'src': '/images/50ffd2b046a24ed12a00024c',
                'alt': 'Сказка Востока'
            },
            'lat': 50.324586,
            'lng': 30.563057999999955,
            'address': {
                'title': 'Столичное шоссе 35'
            },
            'info': {
                'note': 'Бесплатная доставка шашлыка по всему Киеву. Минимальный заказ',
                'min_cost': '200',
                'delivery_time': '90',
                'delivery_cost': '0',
                'currency' : "UAH"
            }
        }
    ];

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
