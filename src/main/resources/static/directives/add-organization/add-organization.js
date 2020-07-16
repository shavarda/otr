export default ngModule => {
  ngModule.directive('addOrganization', addOrganizationFn);
  function addOrganizationFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./add-organization.html'),
      controller: function ($scope, $http) {
        $http.get("/organization")
          .then(function(response) {
            $scope.list = response.data;
          }
        );

        $scope.addOrganization = function(organization) {
            if ((typeof organization === 'undefined') || (organization.name == '')) {
                document.getElementsByClassName('message')[0].innerHTML = "<div class='alert alert-danger' role='alert'>Название организации обязательно для заполнения</div>";
            } else {
                const body = {
                    name: organization.name,
                    organizationHeaderId: organization.header
                }
                $http.post("/organization", body);
                location.href = '/organization.html';
            }
        }
      }
    }
  }
}