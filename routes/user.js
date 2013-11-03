
var MongoClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;
var mongoCreds = 'mongodb://127.0.0.1:27017/havka';
var http = require('http');

exports.list = function(req, res){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            res.send( [] );
            db.close();
        }else{
            var usersCollection = db.collection('users');
            usersCollection.find().toArray(function(err, results) {
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

exports.getUserData = function( req, res ){
    if( req.user ){
        res.send( req.user );
    }else{
        res.send( {} );
    }
};

exports.getSession = function( req, res ){
    res.send( req.session );
};

exports.showAuthError = function( req, res ){
    res.send( { error : "auth" } );
};

var save = function( req, res ){
    MongoClient.connect( mongoCreds , function(err, db) {
        if(err){
            console.log( "DB connection error: " + err );
            res.send( [] );
            db.close();
        }else{
            var usersCollection = db.collection('users');
            usersCollection.find( { id : req.user.id } ).toArray( function( error, data ){
                if( !data[0] ){
                    usersCollection.insert( req.user, function( error, data ){
                        res.send( data[0] );
                        db.close();
                    });
                }else{
                    res.send( data[0] );
                    db.close();
                }
            });
        }
    });
};
exports.save = save;

exports.regUserById = function( req, res ){
    if( req.user === undefined ){
        getFBUserData( req.body.id, function( data ){
            req.user = data;
            req.session.passport.user = data;
            save( req, res );
        });
    }else{
        save( req, res );
    }
};

function getFBUserData( UID, clb ){

    var url = "/" + UID;
    var output = '';
    var responseObj = {};

    var options = {
        hostname: 'graph.facebook.com',
        port: 80,
        path: url,
        method: 'GET'
    };

    var req = http.request(options, function(res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            output += chunk;
        });

        res.on('end', function() {
            try{
                responseObj = JSON.parse( output );
            }catch( err ){

            }
            clb( responseObj );
        });
    });

    req.end();
}
