/**
 * @jsx React.DOM
 */

var Image = React.createClass({
  render: function() {
    return (
          <img src={"images/icons/svg/" + this.props.icon + ".svg"}
               alt="Compas" width="100px" height="100px" />
    )
  }
});

var MenuItem = React.createClass({
  getInitialState: function() {
    return {active: false};
  },
  handleClick: function(e){
    e.preventDefault();
    this.props.active = !this.props.active;
    this.forceUpdate();
  },
  render: function() {
    return (
      <li onClick={this.handleClick} className={ this.props.active && "active"}>
        <a href="#">
          <Image icon={ this.props.icon} />
          <span className="menu-text"> {this.props.text}  </span>
        </a>
      </li>
    );
  }
});

var Menu = React.createClass({
  render: function() {
    return (
      <ul className="nav navbar-nav navbar-left" >
        <MenuItem active="true" icon="compas" text=" Где я?" />
        <MenuItem icon="clipboard" text=" Хочу" />
        <MenuItem icon="paper-bag" text=" Доставка" />
        <li> <LogoBox /> </li>
      </ul>
    );
  }
});
React.renderComponent(
  <Menu />,
  document.getElementById('menu')
);
