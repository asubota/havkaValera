
var Restaraunt = function( data ){

    this.data = {
        name        : data.name ? data.name : "**no name**",
        description : data.description ? data.description : "",
    };
};

Restaraunt.prototype.getData = function(){
    return this.data;
};

module.exports = Restaraunt;
