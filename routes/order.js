
var MongoClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;
var mongoCreds = 'mongodb://127.0.0.1:27017/havka';
var http = require('http');

exports.getOrders = function( req, res ){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            res.send( [] );
            db.close();
        }else{
            var ordersCollection = db.collection('orders');
            ordersCollection.find().toArray(function(err, results) {
                if(err){
                    res.send( [] );
                }else{
                    res.send( results );
                }
                db.close();
            });
        }
    });
};

/*
{
    restaurant   : "",
    lat          : 0,
    lng          : 0,
    phone        : "",
    description  : "",
    amount       : 0,
    currency     : "UAH",
    order        : [
        {
            menu_id : 0,
            count   : 0
        }
    ]
}
*/

exports.saveOrder = function( req, res ){

    if( req.user === undefined ){
        res.send( { "error" : "user" } );
    }else if( req.body === undefined ){
        res.send( { "error" : "no order" } );
    }else{
        var orderParams = req.body;
        orderParams.date = new Date();
        orderParams.status = 'new';
        orderParams.user_id = req.user.id;

        getAdressByLngLat( orderParams.lng, orderParams.lat, function( data ){
            orderParams.address = data;

            MongoClient.connect( mongoCreds , function(err, db) {
                if(err){
                    res.send( [] );
                    db.close();
                }else{
                    var ordersCollection = db.collection('orders');
                    ordersCollection.insert( orderParams, function( err, docs ){
                        res.send( docs[0] );
                        db.close();
                    });
                }
            });
        });
    }
};

function getAdressByLngLat( lng, lat, clb ){

    var url = "/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false&language=ru";
    var output = '';
    var responseObj = {};
    var address = 'пожалуйста перезвоните';

    var options = {
        hostname: 'maps.googleapis.com',
        port: 80,
        path: url,
        method: 'POST'
    };

    var req = http.request(options, function(res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            output += chunk;
        });

        res.on('end', function() {
            try{
                responseObj = JSON.parse( output );
                if( responseObj.results[0].formatted_address ){
                    address = responseObj.results[0].formatted_address;
                }
            }catch( err ){

            }
            clb( address );
        });
    });

    req.end();
}
