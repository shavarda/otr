export default ngModule => {
  ngModule.directive('menu', menuFn);
  function menuFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./menu.html'),
      controller: function ($scope, $http) {
        let host = window.location.pathname;
        if ((host == '/organization.html') || (host == '/organization_tree.html') || (host == '/new_organization.html')) {
            $scope.href = '/new_organization.html';
        } else {
            $scope.href = '/new_worker.html';
        }
      }
    }
  }
}