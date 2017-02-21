angular.module ('orderApp', [])
    .controller('orderCtrl', function($scope, $http, $log){

        var REST_SERVICE_URI = '/etoolstore/order/add/';

    //$scope.refreshCart = function(){
    //    $http.get('/eMusicStore/rest/cart/' + $scope.cartId).success(function (data){
    //        $scope.cart = data;
    //    });
    //};
    //
    //$scope.clearCart = function(){
    //    $http.delete('/eMusicStore/rest/cart/' + $scope.cartId).success($scope.refreshCart());
    //};
    //
    //$scope.initCartId = function(cartId){
    //    $scope.cartId = cartId;
    //    $scope.refreshCart(cartId);
    //};

    $scope.addToCart = function(productId){
        $http.put(REST_SERVICE_URI + productId).success(function (){
            alert('Product added to the cart!');
            $log.log('<--- Product ADDed to cart(Sending data to server) ');
        });
    };

    //$scope.removeFromCart = function(productId){
    //    $http.put('/eMusicStore/rest/cart/remove/' + productId).success(function(data){
    //        $scope.refreshCart();
    //    });
    //};

});



