
var User = function( data ){

    this.data = {
        name        : data.name ? data.name : "**no name**",
        description : data.description ? data.description : ""
    };
};

User.prototype.getData = function(){
    return this.data;
};

module.exports = User;
