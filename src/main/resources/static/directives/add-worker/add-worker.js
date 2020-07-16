export default ngModule => {
  ngModule.directive('addWorker', addWorkerFn);
  function addWorkerFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./add-worker.html'),
      controller: function ($scope, $http) {
        $http.get("/worker")
          .then(function(response) {
            $scope.workers = response.data;
          }
        );

        $http.get("/organization")
          .then(function(response) {
            $scope.organizations = response.data;
          }
        );

        $scope.addWorker = function(worker) {
            console.log(0);
            if ((typeof worker === 'undefined') || (worker.name == '')) {
                document.getElementsByClassName('message')[0].innerHTML = "<div class='alert alert-danger' role='alert'>Имя сотрудника обязательно для заполнения</div>";
            } else {
                const body = {
                    name: worker.name,
                    organizationId: worker.organization,
                    leaderId: worker.leader
                }
                $http.post("/worker", body);
                location.href = '/';
            }
        }
      }
    }
  }
}