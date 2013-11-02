/**
 * @jsx React.DOM
 */

var LogoBox = React.createClass({
  render: function() {
    return (
        <h4>
            Havka$$$Valera - доставляет
        </h4>
    );
  }
});
React.renderComponent(
  <LogoBox />,
  document.getElementById('logo')
);
