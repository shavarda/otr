export default ngModule => {
  ngModule.directive('treeWorker', treeWorkerFn);
  function treeWorkerFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./tree-worker.html'),
      controller: function ($scope, $http, $sce) {
        $http.get("/worker/tree")
          .then(function(response) {
            $scope.tree = response.data;
          }
        );

        $scope.setFile = function() {
            return './item-worker.html';
        };
      }
    }
  }
}