app.directive('post', [function(){
    return{
        restrict: 'A',
        templateUrl: '/js/app/directives/post.html',
        scope: {title: '@', body: '@', author: '@', date: '@', image: '@', length: '@', postid: '@', fn: '&', views: '@'},
        link: function(scope, elem, attr){
            elem.addClass(attr.classname);
        }
    };
}]);