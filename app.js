
/**
 * Module dependencies.
 */

var express = require('express');
var routes = require('./routes');
var user = require('./routes/user');
var town = require('./routes/town');
var restaurants = require('./routes/restaurants');
var http = require('http');
var path = require('path');

var passport = require('passport');
var FacebookStrategy = require('passport-facebook').Strategy;

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());


app.use(passport.initialize());
app.use(passport.session());


app.use(express.cookieParser('your secret here'));
app.use(app.router);
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
    app.use(express.errorHandler());
}

/*********
*////////

passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(obj, done) {
    done(null, obj);
});

var FACEBOOK_APP_ID = '524769977614277';
var FACEBOOK_APP_SECRET = '9a67a7e42f5cd2c9217ff84c5eaf43b3';

passport.use(new FacebookStrategy({
        clientID       : FACEBOOK_APP_ID,
        clientSecret   : FACEBOOK_APP_SECRET,
        callbackURL    : "http://havka.sona-studio.com/auth/facebook/callback"
    },
    function(accessToken, refreshToken, profile, done) {
        process.nextTick(function () {

            // To keep the example simple, the user's Facebook profile is returned to
            // represent the logged-in user.  In a typical application, you would want
            // to associate the Facebook account with a user record in your database,
            // and return that user instead.
            return done(null, profile);
        });
    }
));

app.get('/auth/facebook', passport.authenticate('facebook'));

app.get('/auth/facebook/callback', passport.authenticate('facebook', { failureRedirect: '/town' }),
    function(req, res) {
        // Successful authentication, redirect home.
        console.log( req.session );
        console.log( req.user );
        
        res.send( "hmm" );
    }
);

app.use(passport.initialize());
app.use(passport.session());

/////////////////////////
///////////////////





app.get('/', routes.index);
app.get('/test', routes.test);
app.get('/users', user.list);



/*
 * Towns routes
 * BEGIN
 */
app.get('/town'       , town.list);
app.get('/town/:id'   , town.getTownById);
/*
 * Towns routes
 * END
 */

 /*
 * Restaurants routes
 * BEGIN
 */
app.get('/restaurant'                , restaurants.list);
app.get('/restaurant/categories'     , restaurants.categories);
app.get('/restaurant/:id'            , restaurants.getRestaurantById );
app.get('/restaurant/:lng/:lat/:r'   , restaurants.getRestaurantByLocation );
/*
 * Restaurants routes
 * END
 */

app.get('/reverse_geocode/:lat/:lng', function(req, response) {
	var url = "/maps/api/geocode/json?latlng="+req.params['lat']+","+req.params['lng']+"&sensor=false&language=ru", output = '';
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
    	response.send(output);
    });
  });

	req.end();
});




http.createServer(app).listen(app.get('port'), function(){
    console.log('Express server listening on port ' + app.get('port'));
});
