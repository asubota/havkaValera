
var Town = function( data ){

    this.data = {
        name        : data.name ? data.name : "**no name**",
        description : data.description ? data.description : "",
    };
};

Town.prototype.getData = function(){
    return this.data;
};

module.exports = Town;
