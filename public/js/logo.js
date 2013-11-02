/**
 * @jsx React.DOM
 */

var LogoBox = React.createClass({
  render: function() {
    return (
        <h1><img src="/logo.jpg" />
            Валера - доставляет
        </h1>
    );
  }
});
React.renderComponent(
  <LogoBox />,
  document.getElementById('logo')
);
