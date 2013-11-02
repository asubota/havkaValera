
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
