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
  render: function(){
    var r = this.props.data;
    console.log("[info] rest", r);
    return(
      <div className='col-md-12 r-item tile'>
        <img className='r-img' src={r.logo.src} />
        <span className='r-title'>{r.title}</span>
        <span className='r-info'>{r.info.note}</span>
        <span className='r-addr fui-location badge'>{r.address.street}</span>
        <span className='r-delivery-cost badge'>{r.delivery.cost}</span>
        <span className='r-delivery-min'>{r.delivery.min}</span>
        <span className='r-delivery-time fui-time badge'>{r.delivery.time}</span>
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
    if (this.state.visible) {
      return (
            <div className="List">
              <RestorantList data={this.state.data} filterCat={cat}/>
            </div>
        );
    }
    return <div />
  }
});

var RRestBox = React.renderComponent(
  <RestorantsBox />,
  document.getElementById('resorantsbox')
);
