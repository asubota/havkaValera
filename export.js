
var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://127.0.0.1:27017/havka', function(err, db) {
    if(err) throw err;

    //*************
    // TOWNS
    //*************

    var townsCollection = db.collection('towns');
    var towns = [
        {
            _id         : "5274f00adb8d4f722a000001",
            name        : "Киев",
            description : "Мать городов русских",
        },
        {
            _id         : "5274f00adb8d4f722a000002",
            name        : "Гомель",
            description : "Дыра",
        }
    ];
    townsCollection.insert( towns, function( err, docs ){
        console.log( ">>>>>> Towns Insertion" );
        if( err ){
            console.log( err );
        }
        console.log( "Towns inserted: " + docs.length );
    });

    //*************
    // TOWNS END
    //*************

});
