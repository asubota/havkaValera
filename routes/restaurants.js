
/*
 * GET restaurants list
 */

var MongoClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;
var mongoCreds = 'mongodb://127.0.0.1:27017/havka';
var _ = require('underscore');

exports.list = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            res.send( [] );
            db.close();
        }else{
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find().toArray(function(err, results) {
                if(err){
                    res.send( [] );
                }else{
                    var resp = [];
                    for( var i = 0; i < results.length; i++ ){
                        delete results[i].menu;
                        resp.push(results[i]);
                    }
                    res.send( resp );
                }
                db.close();
            });
        }
    });
};

/*
 * GET restaurants list
 */

exports.categories = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            res.send( [] );
            db.close();
        }else{
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find().toArray(function(err, results) {
                var categories = [];
                if(err){
                    res.send( [] );
                }else{
                    for( var i = 0; i < results.length; i++ ){
                        categories.push( results[i].category );
                    }
                    res.send( _.uniq( _.flatten( categories ) ) );
                }
                db.close();
            });
        }
    });
};

/*
 * GET Restaurant
 */

exports.getRestaurantByCategory = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find().toArray(function(err, results) {

                var categories = [];
                var requested_categories = req.params.category.split('&');

                if(err){
                    console.log( "Collection find error: " + err );
                    res.send( {} );
                }else{
                    for( var i = 0; i < results.length; i++ ){
                        var intersection = _.intersection(requested_categories, results[i].category);
                        if( intersection.length ){
                            delete results[i].menu;
                            categories.push( results[i] );
                        }
                    }

                    res.send( categories );

                }
                db.close();
            });
        }
    });
};

exports.getRestaurantById = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find( { _id : new ObjectID( req.params.id ) } ).toArray(function(err, results) {
                if(err){
                    console.log( "Collection find error: " + err );
                    res.send( {} );
                }else{
                    if( results[ 0 ] ){
                        res.send( results[ 0 ] );
                    }else{
                        res.send( {} );
                    }
                }
                db.close();
            });
        }
    });
};

exports.getRestaurantMenu = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find( { _id : new ObjectID( req.params.id ) } ).toArray(function(err, results) {
                if(err){
                    console.log( "Collection find error: " + err );
                    res.send( {} );
                }else{
                    if( results[ 0 ] ){
                        res.send( results[ 0 ].menu );
                    }else{
                        res.send( {} );
                    }
                }
                db.close();
            });
        }
    });
};

/*
 * GET Restaurant by location
 */

exports.getRestaurantByLocation = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{

            var findQuery = {
                lat : {
                    $gt : req.params.lat - dLat( req.params.r ),
                    $lt : parseFloat( req.params.lat ) + parseFloat( dLat( req.params.r ) )
                },
                lng : {
                    $gt : req.params.lng - dLon( req.params.r, req.params.lat ),
                    $lt : parseFloat( req.params.lng ) + parseFloat( dLon( req.params.r, req.params.lat ) )
                }
            };

            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find( findQuery ).toArray(function(err, results) {
                if(err){
                    res.send( [] );
                }else{
                    var resp = [];
                    for( var i = 0; i < results.length; i++ ){
                        delete results[i].menu;
                        resp.push(results[i]);
                    }
                    res.send( resp );
                }
                db.close();
            });
        }
    });
};

exports.getRestaurantByLocationAndCategory = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{

            var findQuery = {
                lat : {
                    $gt : req.params.lat - dLat( req.params.r ),
                    $lt : parseFloat( req.params.lat ) + parseFloat( dLat( req.params.r ) )
                },
                lng : {
                    $gt : req.params.lng - dLon( req.params.r, req.params.lat ),
                    $lt : parseFloat( req.params.lng ) + parseFloat( dLon( req.params.r, req.params.lat ) )
                }
            };

            var requested_categories = req.params.category.split('&');
            var restaurantsCollection = db.collection('restaurants');
            restaurantsCollection.find( findQuery ).toArray(function(err, results) {
                if(err){
                    res.send( [] );
                }else{
                    var resp = [];
                    for( var i = 0; i < results.length; i++ ){
                        var intersection = _.intersection(requested_categories, results[i].category);
                        if( intersection.length ){
                            delete results[i].menu;
                            resp.push(results[i]);
                        }
                    }

                    res.send( resp );
                }
                db.close();
            });
        }
    });
};

function dLat( m ){

    var R = 6378137;

    return ( m / R ) * 180 / Math.PI;
}

function dLon( m, lat ){

    var R = 6378137;

    return ( m / ( R * Math.cos( Math.PI * lat / 180 ) ) ) * 180 / Math.PI;
}

function arrayUnique(a) {
    return a.reduce(function(p, c) {
        if (p.indexOf(c) < 0) p.push(c);
        return p;
    }, []);
}
