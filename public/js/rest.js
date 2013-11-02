/**
 * @jsx React.DOM
 */


var CategoryMenu = React.createClass({
  componentDidUpdate: function(){
    $('.dropdown-toggle').dropdown();
    console.log("[info] Cat menu");
  },
  getInitialState: function(){
    return {categories: []}
  },
  render: function(){
    var cat_items = this.state.categories.map(function(c){
      return <li><a href="#">{c}</a></li>;
    });
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
    return <div className='col-md-3'>{r.title}</div>;
  }
});

var RestorantList = React.createClass({
  render: function() {
    var cat = this.props.filter.category;
    var items = this.props.data.filter(function(r){
            if (cat){
              return r.category.indexOf(cat);
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
  componentDidUpdate: function(){
    RCategoryMenu.setState({categories: ["Йида", "Хавка"]});
  },
  getInitialState: function() {
   $.ajax({
      url: '/restaurant',
      dataType: 'json',
      mimeType: 'textPlain',
      success: function(data) {
        console.log("[info]", data);
        console.log("[info]", data[0]);
        this.setState({
          visible: true,
          data: data
        });
      }.bind(this)
    });
    return {
      data: [],
      filter: {category: undefined},
      visible: false
    };
  },
  render: function() {
    if (this.state.visible && this.state.filter.category) {
      return (
            <div className="List">
              <RestorantList data={this.state.data} filter={this.state.filter}/>
            </div>
        );
    }
    return <div />
  }
});

React.renderComponent(
  <RestorantsBox />,
  document.getElementById('resorantsbox')
);
