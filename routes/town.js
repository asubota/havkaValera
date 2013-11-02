
/*
 * GET towns list
 */

var MongoClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;
var mongoCreds = 'mongodb://127.0.0.1:27017/havka';

exports.list = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            res.send( [] );
            db.close();
        }else{
            var townsCollection = db.collection('towns');
            townsCollection.find().toArray(function(err, results) {
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
 * GET town
 */

exports.getTownById = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{
            var townsCollection = db.collection('towns');
            townsCollection.find( { _id : new ObjectID( req.params.id ) } ).toArray(function(err, results) {
                if(err){
                    console.log( "Collection find error: " + err );
                    res.send( [] );
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
