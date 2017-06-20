/**
 * Created by 橘 on 2017/5/31.
 */
(function(){
    'use strict';
    var app=angular.module('catpowerserverApp');
    app.controller("shopOpController",["$http","CourseScheduling","$scope","$filter","shopopService","BuyCourse","buyCourseService","AlertService", function($http,CourseScheduling,$scope,$filter,shopopService,BuyCourse,buyCourseService,AlertService){
        var vm = this;
        loadSche();
        function loadSche(date){
            //今日签到与建档人数
            shopopService.loadRegisterCount(date,function (data,flag) {
                if(!flag){
                    alert(data);
                }
                $scope.registerCount =data;
            });
            //今日销售的数量及总金额
            shopopService.loadBuyCourseCountToday(date,function (data,flag) {
                if(!flag){
                    alert(data);
                }
                $scope.buyCourseCountToday = data;
            });
            //今日教练排课
            shopopService.loadCoachArrangement(date,function(data,flag){
                if(!flag){
                    alert(data);
                }
                $scope.coachArrangements =data;
                console.log($scope.coachArrangements);
                //下课
                $scope.getOutClass = function (id) {
                    var index = -1;
                    angular.forEach($scope.coachArrangements,function (item,key) {
                        if(item.sche.id == id ){
                            index = key;
                        }
                    });
                    if(index != -1){
                        $scope.coachArrangements[index].sche.status = '已下课';
                        $http({
                            url:"api/course-schedulings/courseStatus/"+$scope.coachArrangements[index].sche.id,
                            method:"PUT",
                            data:$scope.coachArrangements[index].sche.status
                        }).success(function (data) {
                            console.log(data);
                        });
                    }
                };
                //取消教练排课
                /*$scope.removeCoachArrangement = function (id) {
                    var index = -1;
                    angular.forEach($scope.coachArrangements,function (item,key) {
                        if(item.id == id ){
                            index = key;
                        };
                    });
                    if(index != -1){
                        $scope.coachArrangements.splice(index,1);
                    }
                    return $scope.coachArrangements;
                };*/
                //教练排课的样式
                angular.forEach($scope.coachArrangements,function (item) {
                    console.log("********************0");
                    console.log(item.sche.status);
                     if(item.sche.status == '未开始'){
                         $scope.arrangementStyle={
                             "border": "1px solid #e3e3e3",
                             "margin-top": "2.5%",
                             "padding": "0px",
                             "background": "#008800",
                             "line-height": "3"
                         };
                     }
                     if(item.sche.status == '进行中'){
                         /*$scope.arrangementStyle={
                         "border": "1px solid #e3e3e3",
                         "margin-top": "2.5%",
                         "padding": "0px",
                         "background": "#FFD700",
                         "line-height": "3"
                         };*/
                     }
                     if(item.sche.status == '已下课'){
                         /*$scope.arrangementStyle={
                         "border": "1px solid #e3e3e3",
                         "margin-top": "2.5%",
                         "padding": "0px",
                         "background": "#F08080",
                         "line-height": "3"
                         };*/
                     }

                 });

            });
        }


        $scope.page={
            index:0,
            size:10
        };

        //取消教练排课
        $scope.removeCoachArrangement = function (id) {
            var index = -1;
            angular.forEach($scope.coachArrangements,function (item,key) {
                if(item.id == id ){
                    index = key;
                };
            });
            if(index != -1){
                $scope.coachArrangements.splice(index,1);
            }
            return $scope.coachArrangements;
        };
        //删除学员
        $scope.removeTrainee = function (id,traineeId) {
            var index = -1;
            var count = '';
            angular.forEach($scope.coachArrangements,function (item,key) {
                if(item.id == id ){
                    count = key;
                    angular.forEach(item.traineeName,function (it,k) {
                        if(it.traineeId == traineeId ){
                            index = k;
                        };
                    });
                    if(index != -1){
                        item.traineeName.splice(index,1);
                        item.traineeCount --;
                        if(item.traineeName.length == 0){
                            $scope.coachArrangements.splice(count,1);
                        }
                    }
                    return item.traineeName;
                };

            });
            return $scope.coachArrangements;
        };
        //获取当前日期
        var now = new Date();
        var month = now.getMonth()+1;
        var nowDate = now.getFullYear()+'-'+month+'-'+now.getDate();
        $scope.nowDate = nowDate;
        //时段
        $scope.today = function() {
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            $scope.startArrangementCourseDate = new Date();
            $scope.endArrangementCourseDate = new Date();
        };
        $scope.today();
        $scope.clear = function() {
            $scope.startDate = null;
            $scope.endDate = null;
            $scope.startArrangementCourseDate = null;
            $scope.endArrangementCourseDate = null;
        };
        $scope.startTime = function() {
            $scope.popup1.opened = true;
        };
        $scope.endTime = function() {
            $scope.popup2.opened = true;
        };
        $scope.startArrangementCourse = function() {
            $scope.popupStartArrangementCourse.opened = true;
        };
        $scope.endArrangementCourse = function() {
            $scope.popupEndArrangementCourse.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };
        $scope.popup2 = {
            opened: false
        };
        $scope.popupStartArrangementCourse = {
            opened: false
        };
        $scope.popupEndArrangementCourse = {
            opened: false
        };

        /**
         * 售课情况
         */
        loadAll();
        function loadAll(){
            BuyCourse.query({},onSuccess,onError);
        }
        function onSuccess(data) {
            $scope.BuyCourses = data;
            console.log($scope.BuyCourses);

        }
        function onError(error) {
            AlertService.error(error.data.message);
        }

        /**
         * 今日售课
         */
        buyCourseService.loadBuyCourseToday(0,10,function (data,flag) {
            if(!flag){
                alert(data);
            }
            $scope.buyCoursesToday =data.content;
            console.log($scope.buyCoursesToday);
        });
        /**
         * 排课情况
         */
        loadSchedulingAll();
        function loadSchedulingAll(){
            CourseScheduling.query({},onScheSuccess,onScheError);
        }
        function onScheSuccess(data) {
            $scope.courseSchedulings = data;
            console.log($scope.courseSchedulings);

        }
        function onScheError(error) {
            AlertService.error(error.data.message);
        }
    }]);
    /**
     * 今日教练排课
     */
    app.service("shopopService",["$http",function ($http) {
        //今日教练排课
        this.loadCoachArrangement = function (date,callback) {
            $http({
                url:"api/course-schedulings/today",
                method:"GET"
            }).then(function(data){
                if(callback){
                    callback(data.data,true);
                }
            },function(error){
                if(callback){
                    callback(error,false);
                }
            })
        };
        //今日签到与建档人数
        this.loadRegisterCount = function (date,callback) {
            $http({
                url:"api/learners/count/today",
                method:"GET"
            }).then(function(data){
                if(callback){
                    callback(data.data,true);
                }
            },function(error){
                if(callback){
                    callback(error,false);
                }
            })
        };
        //今日销售的数量及总金额
        this.loadBuyCourseCountToday = function (date,callback) {
            $http({
                url:'api/buy-courses/today/count',
                method:'GET'
            }).then(function (data) {
                if(callback){
                    callback(data.data,true);
                }
            },function (error) {
                if(callback){
                    callback(error,false);
                }
            });
        }

    }]);
    //今日售课
    app.factory("buyCourseResource",["$resource",function($resource){
        var resourceUrl =  'api/buy-courses/today';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET'}
        });

    }]);
    app.service("buyCourseService",["buyCourseResource",function(buyCourseResource){
        this.loadBuyCourseToday=function(index,size,callback){
            buyCourseResource.query({
                index:index,
                size:size
            },function(data){
                console.log("shopopResource.query()",data);
                if(callback){
                    callback(data,true);
                }
            },function(error){
                console.log("shopopResource.query()",error);
                if(callback){
                    callback(error,false);
                }
            })
        }
    }]);

    app.service("CourseArrangementService",[function () {
        this.loadCourseArrangement = function () {
            var courseArrangements = [{
                arrangementDate:'2017-6-5',
                coachName:'教练1',
                courseName:'基础理论课程',
                startTime:'2017-6-5 17:00',
                endTime:'2017-6-5 18:30',
                attendanceNumber:'5'
            },{
                arrangementDate:'2017-6-6',
                coachName:'教练2',
                courseName:'功能训练课程  ',
                startTime:'2017-6-6 18:00',
                endTime:'2017-6-6 19:30',
                attendanceNumber:'5'
            }];
            return courseArrangements;
        }
    }]);


})();

(function(){
    'use strict';
    var app=angular.module('catpowerserverApp');
    app.config(["$stateProvider",function($stateProvider){
        $stateProvider
            .state('shopop', {
            parent: 'app',
            url: '/',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/pages/shop/shop.html',
                    controller: 'shopOpController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('shop');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
            .state('shopop.new', {
                parent: 'shopop',
                url: 'new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/pages/shop/addTodayCourse.html',
                        controller: 'addCourseController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            addArrangement: function () {
                                return {
                                    coachName: null,
                                    courseName: null,
                                    courseState: null,
                                    time: null,
                                    traineeCount: null,
                                    traineeName: null,
                                    id:null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('shopop', null, { reload: 'shopop' });
                    }, function() {
                        $state.go('shopop');
                    });
                }]
            })
            .state('shopop.delete', {
                parent: 'shopop',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/pages/shop/shop-coachArrangement-delete.html',
                        controller: 'CourseSchedulingDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['CourseScheduling', function(CourseScheduling) {
                                return CourseScheduling.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('course-scheduling', null, { reload: 'course-scheduling' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }]);
})();

