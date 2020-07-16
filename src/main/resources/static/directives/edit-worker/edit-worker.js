export default ngModule => {
  ngModule.directive('editWorker', editWorkerFn);
  function editWorkerFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./edit-worker.html'),
      controller: function ($scope, $http) {
        let params = (new URL(document.location)).searchParams;
        let id = params.get("id");

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

        $http.get("/worker/" + id)
          .then(function(response) {
            $scope.worker = response.data;
            $scope.organization = '';
            $scope.leader = '';
            if (response.data.organizationId !== null) {
                $http.get("/organization/" + response.data.organizationId)
                  .then(function(info) {
                    $scope.organization = info.data;
                  }
                );
            }
            if (response.data.leaderId !== null) {
                $http.get("/worker/" + response.data.leaderId)
                  .then(function(info) {
                    $scope.leader = info.data;
                  }
                );
            }
          }
        );

        $scope.editWorker = function(worker, organization, leader) {
            if ((typeof organization === 'undefined') || (organization.name == '')) {
                document.getElementsByClassName('message')[0].innerHTML = "<div class='alert alert-danger' role='alert'>Имя сотрудника обязательно для заполнения</div>";
            } else {
                let organizationId = '';
                let leaderId = '';
                if (typeof organization.id !== 'undefined') {
                    organizationId = organization.id;
                }
                if (typeof leader.id !== 'undefined') {
                    leaderId = leader.id;
                }
                const body = {
                    name: worker.name,
                    organizationId: organizationId,
                    leaderId: leaderId
                }
                $http.put("/worker/" + id, body);
                location.href = '/';
            }
        }
      }
    }
  }
}