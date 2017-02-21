angular.module('myApp', [])
    .controller('AppController', ['$log', function($log) {
        var self = this;
        self.submit = function() {
            $log.log('<----START Sending data to Server! ');
            console.log('Form is submitted with following user', self.user);
        };
}]);



