var app = angular.module("BuildingManagement", []);

app.controller("UiController", function($scope, $http) {

    $scope.buildings = [];
    $scope.buildingForm = {
        building_id: 1,
        address: "",
        numberOfFloors: 0
    };

    _refreshBuildingData();

    $scope.submitBuilding = function() {

        var method = "";
        var url = "";

        if ($scope.buildingForm.building_id = -1) {
            method = "POST";
            url = '/ui/building';
        }
        else {
            method = "PUT";
            url = '/ui/building';
        }

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.buildingForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    $scope.createBuilding = function() {
        _clearFormData();
    }
})