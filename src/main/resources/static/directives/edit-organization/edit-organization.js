export default ngModule => {
  ngModule.directive('editOrganization', editOrganizationFn);
  function editOrganizationFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./edit-organization.html'),
      controller: function ($scope, $http) {
        let params = (new URL(document.location)).searchParams;
        let id = params.get("id");

        $http.get("/organization/")
          .then(function(response) {
            $scope.list = response.data;
          }
        );

        $http.get("/organization/" + id)
          .then(function(response) {
            $scope.organization = response.data;
            $scope.organizationHeaderId = '';
            if (response.data.organizationHeaderId !== null) {
                $http.get("/organization/" + response.data.organizationHeaderId)
                  .then(function(info) {
                    $scope.organizationHeaderId = info.data;
                  }
                );
            }
          }
        );

        $scope.editOrganization = function(organization, organizationHeaderId) {
            if ((typeof organization === 'undefined') || (organization.name == '')) {
                document.getElementsByClassName('message')[0].innerHTML = "<div class='alert alert-danger' role='alert'>Имя организации обязательно для заполнения</div>";
            } else {
                let organizationHeader = '';
                if (typeof organizationHeaderId.id !== 'undefined') {
                    organizationHeader = organizationHeaderId.id;
                }
                const body = {
                    name: organization.name,
                    organizationHeaderId: organizationHeader
                }
                $http.put("/organization/" + id, body);
                location.href = '/organization.html';
            }
        }
      }
    }
  }
}