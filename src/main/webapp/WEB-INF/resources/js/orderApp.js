angular.module ('orderApp', [])
    .controller('orderCtrl', function($scope, $http, $log){

    var REST_SERVICE_URI = '/etoolstore/order/add/';

    $scope.addToCart = function(productId){
        $http.put(REST_SERVICE_URI + productId).success(function (){
            alert('Product added to the cart!');
            $log.log('<--- Product ADDed to cart(Sending data to server) ');
        });
    };

});



