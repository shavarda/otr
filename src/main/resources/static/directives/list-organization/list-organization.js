export default ngModule => {
  ngModule.directive('listOrganization', listOrganizationFn);
  function listOrganizationFn() {
    return {
      restrict: 'E',
      scope: {},
      template: require('./list-organization.html'),
      controller: function ($scope, $http, $sce) {
        const count = 5;

        list('', 0, count);

        function list(search, page, count) {
            $http.get("/organization/search-name?name=" + search)
              .then(function(response) {
                $scope.commonElement = response.data.length;
                $scope.currentPageNum = page;
                let pagesNum = Math.ceil(response.data.length / 5);
                let paginationList = [];
                for (let i = 0; i < pagesNum; i++) {
                    let name = i + 1;
                    paginationList.push({
                        name: $sce.trustAsHtml(String (name)),
                        link: i
                    });
                }
                if (pagesNum > 1) {
                    $scope.pagination = paginationList;
                } else {
                    $scope.pagination = '';
                }
                $http.get("/organization/search?name=" + search + "&page=" + page + "&count=" + count + "")
                  .then(function(response) {
                    $scope.listPagination = response.data;
                  }
                );
              }
            );
        }

        $scope.searchOrganization = function(search) {
            if ((typeof search === 'undefined')) {
                search = '';
            }
            list(search, 0, count);
        }

        $scope.showPage = function(search, page) {
            let offset = page * count;
            if ((typeof search === 'undefined')) {
                search = '';
            }
            $http.get("/organization/search?name=" + search + "&page=" + offset + "&count=" + count + "")
              .then(function(response) {
                $scope.listPagination = response.data;
                $scope.currentPageNum = page;
              }
            );
        }

        $scope.modalOrganization = function(id) {
            document.getElementsByClassName('delete__modal')[0].setAttribute('ng-click', 'deleteOrganization(' + id + ')');
            $scope.deleteId = id;
        }

        $scope.deleteOrganization = function(id) {
            $http.delete("/organization/" + id);
            location.href = '/organization.html';
        }
      }
    }
  }
}