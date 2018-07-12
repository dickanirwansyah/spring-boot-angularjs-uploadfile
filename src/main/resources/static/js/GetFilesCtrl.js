mainApp.controller('GetFilesController', function($scope, $http){

    $scope.allFiles = [];

    $scope.getAllFiles = function(){
        var url = "/api/rest/list";
        $http.get(url).then(
            function(response){
                alert("oke");
                $scope.allFiles = response.data
            },

            function(response){
                alert("error: "+response.data);
            }
        );
    };
});