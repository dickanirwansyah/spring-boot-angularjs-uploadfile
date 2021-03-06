mainApp.controller('UploadFileController', function($scope, $http){
    $scope.uploadResult = "";

    $scope.myForm = {
        description: "",
        files: []
    }

    $scope.doUploadFile = function(){
        var url = "/api/rest/multifiles";
        var data = new FormData();

        data.append("description", $scope.myForm.description);
        for(i=0; i<$scope.myForm.files.length; i++){
            data.append("files", $scope.myForm.files[i]);
        }

        var config = {
            transformRequest: angular.identity,
            transformResponse: angular.identity,
            headers:{
                'Content-Type': undefined
            }
        }

        $http.post(url, data, config).then(
            //jika sukses
            function(response){
                $scope.uploadResult = response.data;
            },

            //jika error
            function(response){
                $scope.uploadResult = response.data;
            });
    };
});