export default ngModule => {
  ngModule.directive('treeOrganization', treeOrganizationFn);
  function treeOrganizationFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./tree-organization.html'),
      controller: function ($scope, $http, $sce) {
        $http.get("/organization/tree")
          .then(function(response) {
            $scope.tree = response.data;
          }
        );

        $scope.setFile = function() {
            return './item.html';
        };
      }
    }
  }
}