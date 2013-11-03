/**
 * @jsx React.DOM
 */


var CategoryMenu = React.createClass({
  componentDidUpdate: function(){
    $('.dropdown-toggle').dropdown();
  },
  getInitialState: function(){
    return {categories: []}
  },
  filterCat: function(cat){
    return function(){
      console.log("[info] filter cat", cat);
      RRestBox.setState({filterCat: cat});
    };
  },
  render: function(){
    var cat_items = this.state.categories.map(function(c){
      return <li><a href="#" onClick={this.filterCat(c)}>{c}</a></li>;
    }.bind(this));
    return (<div className="menu-fix">
              <a href="#" className="dropdown-toggle" data-toggle="dropdown">
                <img width="40" height="40" src="images/icons/svg/clipboard.svg" alt="Pocket" />
                <span className="menu-text"> Хочу  </span>
                <b className="caret"></b>
              </a>
              <ul className='dropdown-menu cousins'>{cat_items}</ul>
            </div>
           );
  }
});

var RCategoryMenu = React.renderComponent(
  <CategoryMenu />,
  document.getElementById('category-menu')
);

var RestItem = React.createClass({
  setRest: function(id){
    return function(){
      if (id) RRestBox.setState({current: id});
    };
  },
  render: function(){
    var r = this.props.data;
    return(
      <div className='col-md-12 r-item tile' onClick={this.setRest(r._id)}>
        <img className='r-img' src={r.logo.src} />
        <span className='r-title'>{r.title}</span>
        <span className='r-info'>{r.info.note}</span>
        <span className='r-addr fui-location badge'>{" "}{r.address.street}</span>
        <span className='r-delivery-cost badge'>{" "}{r.delivery.cost}</span>
        <span className='r-delivery-min'>минимльний заказ{" "}{r.delivery.min}</span>
        <span className='r-delivery-time fui-time badge'>{r.delivery.time}{" "}мин</span>
      </div>
    );
  }
});

var RestorantList = React.createClass({
  render: function() {
    var cat = this.props.filterCat;
    var items = this.props.data.filter(function(r){
            if (cat){
              return !!r.category.indexOf(cat);
            } else {
              return true;
            }
          }).map(function(r){
            return <RestItem data={r}/>;
          });
    return (<div> {items} </div>);
  }
});


var SingleRest = React.createClass({
  getInitialState: function() {
   var self = this;
   $.ajax({
      url: '/restaurant/'+ this.props.rest_id,
      success: function(data) {
        console.log('[info] single data', data);
        self.setState({rest: data});
      }
    });
    return { rest: undefined };
  },
  render: function() {
    if (this.state.rest) {
      var r=this.state.rest;
      var rest_id = this.props.rest_id;
      var menus = r.menu.map(function(m){
            return <MenuItem data={m} rest_id={rest_id} />;
          });
      return (
        <div>
          <h1>{r.title}</h1>
          <RestItem data={r}/>
          <div className='r-menu'>{menus}</div>
        </div>
      );
    } 
    return <div />
  }
});
  

var MenuItem = React.createClass({
  render: function(){
    var m = this.props.data;
    return (
// menu: Array[88]
//0: Object
//category: "Пицца  "Сделай  сам""
//currency: "UAH"
//description: "Корж, фирменный томатный соус, сыр Гауда. 360 г"
//hash: "firmennyy-korzh-28-sm"
//image: "/media/celentano/food/515e8976610b98357d00001b.jpg"
//key: "515d8b05610b98c40c000017"
//name: "Фирменный корж 28 см"
//parent: "celentano"
//price: "22.50"
      <div className='m-item'>
          <h5>{m.name}</h5>
          <img className="m-img" src={m.image} />
          <span className="m-price badge">{m.price}</span>
      </div>
    );
  }
});

var RestorantsBox = React.createClass({
  getInitialState: function() {
   $.ajax({
      url: '/restaurant',
      dataType: 'json',
      mimeType: 'textPlain',
      success: function(data) {
        var keys = {};
        var categories = []
        data.map(function(r){
          var l = $.map(r.category, function(c){return c.split(",")});
          r.category = l;
          $.each(l, function(i, c){ keys[c]=0; });
        });
        for (k in keys){
          categories.push(k);
        }
        RCategoryMenu.setState({categories: categories});
        this.setState({
          visible: true,
          current: undefined,
          data: data
        });
      }.bind(this)
    });
    return {
      data: [],
      filterCat: undefined,
      visible: false
    };
  },
  render: function() {
    var cat = this.state.filterCat;
    if (this.state.current) {
      return <SingleRest rest_id={this.state.current} />;
    } else {
      if (this.state.visible) {
        return (
              <div className="List">
                <RestorantList data={this.state.data} filterCat={cat}/>
              </div>
          );
      }
    }
    return <div />
  }
});

var RRestBox = React.renderComponent(
  <RestorantsBox />,
  document.getElementById('resorantsbox')
);
