/**
 * Created by asus on 2017/6/2.
 */
(function(){
    'use strict';
    var app=angular.module('catpowerserverApp');
    app.config(["$stateProvider",function($stateProvider){
        $stateProvider
            .state('student-filing', {
                parent: 'app',
                url: '/student-filing',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/student-filing/student-filing.html',
                        controller: 'studentFilingController',
                        controllerAs: 'vm'
                    }
                },
                params:{
                    page:{
                        value:'1',
                        squash:true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams:['$stateParams','PaginationUtil',function ($stateParams,PaginationUtil) {
                        return {
                            page:PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        }
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student-filing');
                        $translatePartialLoader.addPart('learner');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('student-filing.new',{
                parent:'student-filing',
                url:'/new',
                data:{
                    authorities:['ROLE_USER']
                },
                onEnter:['$stateParams','$state','$uibModal',function ($stateParams,$state,$uibModal) {
                    $uibModal.open({
                        templateUrl:'app/pages/student-filing/student-filing-dialog.html',
                        controller:'studentFilingDialogController',
                        controllerAs:'vm',
                        backdrop:'static',
                        size:'md',
                        resolve:{
                            entity:function () {
                                return{
                                    learneName: null,
                                    learnerPhone: null,
                                    learnerSex: null,
                                    registTime: null,
                                    wxOpenId: null,
                                    wxNickname: null,
                                    wxHeader: null,
                                    firstTotime: null,
                                    firstBuyclass: null,
                                    recentlySignin: null,
                                    experience: null,
                                    id: null
                                }
                            }
                        }
                    }).result.then(function () {
                        $state.go('student-filing',null,{reload:'student-filing'});
                    },function () {
                        $state.go('student-filing');
                    });
                }]
            })
            .state('student-filing.detail', {
                parent: 'student-filing',
                url: '/student-filing/{id}',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/pages/student-filing/student-filing-detail.html',
                        controller: 'StudentFilingDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('learner');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Learner', function($stateParams, Learner) {
                        return Learner.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'learner',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
        })
            .state('student-filing.edit', {
                parent: 'student-filing',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl:'app/pages/student-filing/student-filling-edit.html',
                        controller:'studentFilingEditController',
                        controllerAs:'vm',
                        backdrop:'static',
                        size: 'md',
                        resolve: {
                            entity: ['Learner', function(Learner) {
                                return Learner.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-filing', null, { reload: 'student-filing' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('student-filing.stuqr', {
                parent: 'student-filing',
                url: '/{id}/stuqr',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl:'app/pages/student-filing/student-filling-stuqr.html',
                        controller:'studentFilingStuqrController',
                        controllerAs:'vm',
                        backdrop:'static',
                        size: 'md',
                        resolve: {
                            entity: ['Learner', function(Learner) {
                                return Learner.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-filing', null, { reload: 'student-filing' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('student-filing.delete', {
                parent: 'student-filing',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/pages/student-filing/student-filing-delete.html',
                        controller: 'studentFilingDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Learner', function(Learner) {
                                return Learner.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-filing', null, { reload: 'student-filing' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }]);
})();
