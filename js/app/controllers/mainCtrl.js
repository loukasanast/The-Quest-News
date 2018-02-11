app.controller('MainCtrl', ['$scope', '$http', '$routeParams', '$location', '$route', '$templateCache', function($scope, $http, $routeParams, $location, $route, $templateCache){
    $scope.news = [];
    $scope.current = {};
    $scope.category = '';
    $scope.results = [];
    $scope.loadPage = function(category, selector){
        $scope.category = category;
        $location.path('/' + $scope.category);
        $('nav li').removeClass('active');
        $(selector).addClass('active');
        $http.get('data.do?category=' + $scope.category)
        .then(function(response){
            $scope.news = response.data.news;
            console.log($scope.news);
            console.log($scope.category);
        });
    };
    $scope.viewPost = function(id){
        $scope.current = $scope.news.find(function(item){
            return item.id === id;
        });
        $location.path('/article/' + id);
        $http({
            method: 'POST',
            url: 'counter.do',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { id: id }
        }).then(function(response){
            console.log(response.data);
        });
    };
    $scope.submitComment = function(postid){
        $http({
            method: 'POST',
            url: 'comment.do',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { id: postid, body: $('.txt-comment').val() }
        }).then(function(response){
            var d = new Date();
            var days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
            $('.btn-modal').unbind('click');
            $('.btn-modal').click(function(){
                $(this).parent().hide();
            });
            $scope.news.find(function(item){
                return item.id === $scope.current.id;
            }).comments.push({
                id: -1,
                body: $('.txt-comment').val(),
                date: days[d.getDay()] + ', ' + 
                (d.getDate() < 10 ? '0' + d.getDate() : d.getDate()) + '-' + 
                (d.getMonth() < 9 ? '0' + (d.getMonth() + 1) : d.getMonth() + 1) + '-' + 
                d.getFullYear() + ' ' + 
                d.getHours() + ':' + (d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes())
            });
            console.log($scope.news);
            var currentPageTemplate = $route.current.templateUrl;
            $templateCache.remove(currentPageTemplate);
            $route.reload();
            $('.modal-body').text('Dear User! Your Comment has been submitted.');
            $('.modal').show();
            console.log(response.data);
        });
    };
    $scope.submitPost = function(){
        if($('.txt-post').val().length < 600){
            $('.error-body').text('Your Article should consist of at least 600 Characters.');
            $('.error').show();
        }else if($('#file-select').val() !== ''){
            $http({
                method: 'POST',
                url: 'publish.do',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: { name: $('.txt-name').val(), category: $('.slc-category').val(), 
                       title: $('.txt-title').val(), post: $('.txt-post').val(), 
                       image: $('#file-select').val().substring($('#file-select').val().lastIndexOf('\\')) }
            }).then(function(response){
                $('.btn-modal').unbind('click');
                $('.btn-modal').click(function(){
                    $(this).parent().hide();
                    $scope.loadPage($('.slc-category').val(), '.btn-' + $('.slc-category').val());
                });
                $('.modal-body').text('Dear User! Your Article has been submitted.');
                $('.modal').show();
                console.log(response.data);
            });
        }else{
            $('.error-body').text('Please, include an Image to your Article.');
            $('.error').show();
        }
    };
    $scope.submitSearch = function(){
        if($('.txt-search').val().length >= 3){
            $('.autosuggest').show();
            $http.get('search.do?q=' + $('.txt-search').val())
            .then(function(response){
                $scope.results = response.data.news;
                console.log($scope.results);
                if($scope.results.length > 0){
                    $('.autosuggest li').first().hide();
                }else{
                    $('.autosuggest li').first().show();
                }
            });
        }else{
            $('.autosuggest').hide();
        }
    };
    window.onpopstate = function(){
        $scope.loadPage($location.path().substring(1), '.btn-' + $location.path().substring(1));
    };
    $scope.loadPage('home', 'li:first-child');
}]);