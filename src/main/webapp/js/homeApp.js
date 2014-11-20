/**
 * Created by yijunmao on 11/19/14.
 */

var app = angular.module("mainApp", ["ngResource", "ngRoute", "base64"]);

app.constant("baseUrl", "localhost:8080");

app.config(function ($routeProvider, $locationProvider, $httpProvider) {

    if (window.localStorage.token) {
        $httpProvider.defaults.headers.common.Authorization = "Basic " + window.localStorage.token;
    }

    $routeProvider.when('/login', {
        templateUrl: 'login.html',
        controller: 'loginCtrl'
    });

    $routeProvider.when('/main', {
        templateUrl: 'main.html'
    });

    $routeProvider.when('/home', {
       templateUrl: 'home.html'
    });

    $routeProvider.otherwise({redirectTo: '/main'});

    $locationProvider.html5Mode(true);
});

app.controller('loginCtrl', function($scope, $window, $location, $http, $base64, baseUrl){

    $scope.login = function(){
        $http({
            url: '/data.json',
            method: 'GET',
            headers:{
                Authorization: 'Basic ' + $base64.encode($scope.username+' ' +$scope.password)
            }
        }).success(function(data){
            if(data.status){
                $window.localStorage.token = data.token;
                $location.path('/home');
            }else{
                $scope.message = 'username/password incorrect';
            }
        }).error(function(err){
            console.log(err);
        });

    }
});

app.controller('mainCtrl', function($scope, $location){
    $scope.showLoginPage = function(){
        $location.path('/login');
    };
});