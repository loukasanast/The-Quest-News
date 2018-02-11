<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
    <html ng-app="app" lang="en">
        <head>
            <base href="http://www.thequestnews.org:8080/">
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>The Quest News &ndash; Read &amp; Submit your Own Articles.</title>
            <link rel="shortcut icon" href="favicon.ico" />
            <link rel="icon" href="favicon.ico" />
            <link rel="stylesheet" href="css/main.css" />
            <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet" />
        </head>
        <body ng-controller="MainCtrl">
            <div class="wrapper">
                <header>
                    <div class="top-left">
                        <form class="frm-search">
                            <input class="txt-search" ng-keyup="submitSearch()" type="text" placeholder="Begin your Quest here&hellip;" />
                        </form>
                        <nav>
                            <ul>
                                <li class="active btn-home" 
                                    ng-click="loadPage('home', 'li:first-child')" 
                                    data-category="">NEWS.</li>
                                <li class="btn-politics"
                                    ng-click="loadPage('politics', 'li:nth-child(2)')"
                                    data-category="politics">POLITICS.</li>
                                <li class="btn-business"
                                    ng-click="loadPage('business', 'li:nth-child(3)')" 
                                    data-category="business">BUSINESS.</li>
                                <li class="btn-culture"
                                    ng-click="loadPage('culture', 'li:nth-child(4)')" 
                                    data-category="culture">CULTURE.</li>
                                <li class="btn-sport"
                                    ng-click="loadPage('sport', 'li:nth-child(5)')" 
                                    data-category="sport">SPORT.</li>
                                <li class="btn-tech"
                                    ng-click="loadPage('tech', 'li:nth-child(6)')" 
                                    data-category="tech">TECH.</li>
                                <li class="btn-celebs"
                                    ng-click="loadPage('celebs', 'li:last-child')" 
                                    data-category="celebs">CELEBS.</li>
                                <li class="btn-write"
                                    ng-click="loadPage('celebs', 'li:last-child')" 
                                    data-category="celebs"><a ng-href="/publish">Write.</a></li>
                            </ul>
                        </nav>
                    </div>
                    <img class="logo" src="img/logo.png" alt="Site Logo" />
                    <div class="top-right">
                        <h4>Make the News, by Contributing to our Independent Open Source News Platform.</h4>
                        <a ng-href="/publish"><img class="icn-write" src="img/icn-write.png" alt="Write an Article" /></a>
                    </div>
                </header>
                <main ng-view></main>
                <footer>
                    COPYRIGHT &copy; 2018
                </footer>
                <div class="modal">
                    <div class="modal-title"><i class="fa fa-exclamation-circle"></i>Information.!</div>
                    <div class="modal-body"></div>
                    <button class="btn-modal">Close.</button>
                </div>
                <div class="error">
                    <div class="error-title"><i class="fa fa-ban"></i>Error.!</div>
                    <div class="error-body"></div>
                    <button class="btn-error">Close.</button>
                </div>
                <div class="autosuggest">
                    <ul>
                        <li>No Results found.</li>
                        <li ng-repeat="r in results" ng-click="viewPost(r.id)">{{r.title}}</li>
                    </ul>
                </div>
            </div>
            <script src="js/lib/jquery.min.js"></script>
            <script src="js/lib/angular.min.js"></script>
            <script src="js/lib/angular-route.min.js"></script>
            <script src="js/app/app.js"></script>
            <script src="js/app/controllers/mainCtrl.js"></script>
            <script src="js/app/directives/post.js"></script>
            <script>
                (function(){
                    'use strict';
                    $('.btn-error').click(function(){
                        $(this).parent().hide();
                    });
                    $('.txt-search').blur(function(){
                        window.setTimeout(function(){
                            $('.autosuggest').hide();
                        }, 320);
                    });
                }());
            </script>
        </body>
    </html>