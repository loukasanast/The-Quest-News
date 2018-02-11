var app = angular.module('app', ['ngRoute'])
.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){
    $routeProvider.when('/home', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/politics', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/business', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/culture', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/sport', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/tech', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/celebs', {
        templateUrl: '/js/app/views/home.html'
    })
    .when('/article/:id', {
        templateUrl: '/js/app/views/article.html'
    })
    .when('/publish', {
        templateUrl: '/js/app/views/publish.html'
    })
    .otherwise('/home');
    $locationProvider.html5Mode(true);
}]);