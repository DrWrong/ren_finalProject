var ontologyApp = angular.module('ontologyApp',[]);

// var sensors;

// ontologyApp.factory('OriginSensorValue', [$resource, function ($resoure) {
//     return {
//         getValue: function(){
//             $http.get("/ontology/origin").success(function(data){
//                 sensors = data;
//             });
//         }
//     };
// }]);
// ontologyApp.factory("ReasonServer", [$resource, function($resource){
//     return $resource("/ontology/reason")
// }])

ontologyApp.controller("ontologyUI", function($scope, $http){
    // OriginSensorValue.getValue();
    $http.get("/ontology/origin").success(function(data){
          $scope.sensors = data;
    });

    $scope.loadOrigin = function(){
        $http.get("/ontology/origin").success(function(data){
              $scope.sensors = data;
        });
    };

    $scope.performReason = function(){
        // var baseUrl = "/ontology/reason?"

        $http.post("/ontology/reason", $scope.sensors).success(function(data){
            $scope.reasonResult = data;
        })
        // $("#reason").disabled();
        // $.ajax({
        //     url: '/ontology/reason',
        //     dataType: 'json',
        //     data: $scope.sensors,
        // })
        // .done(function(data) {
        //     $scope.reasonResult = data;
        //     // $("#reason").enabled();
        //     console.log(data);
        // })
        // var ReasonServer = $resource("/ontology/reason");
        // ReasonServer.get($scope.sensors, function(data){
        //     console.log(data);
        // });

    };
});

