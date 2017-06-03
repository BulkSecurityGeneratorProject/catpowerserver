/**
 * Created by 橘 on 2017/5/31.
 */
(function(){
    'use strict';
    var app=angular.module('catpowerserverApp');
    app.controller("shopOpController",["$scope","shopopService","coachArrangementService","classesTodayService",function($scope,shopopService,coachArrangementService,classesTodayService){
        //今日教练排课
        $scope.coachArrangements = coachArrangementService.loadCoachArrangement();
        //今日售课
        $scope.sellingCoursers = classesTodayService.loadSellingCoursers();
        $scope.page={
            index:0,
            size:10
        };

        function loadbuycourse(){
            shopopService.loadbuycourse($scope.page.index,$scope.page.size,function(data){

            });
        }

        $scope.loadbuycourse=loadbuycourse;
        loadbuycourse();

        //上课状态
        angular.forEach($scope.coachArrangements,function (item) {
            if( item.courseState == '已结束'){
                item.status = false;
            }else{
                item.status = true;
            }
        });
        //下课
        $scope.getOutClass = function (id) {
            var index = -1;
            angular.forEach($scope.coachArrangements,function (item,key) {

                if(item.id == id ){
                    index = key;
                    item.courseState = '已结束';
                    if( item.courseState == '已结束'){
                        item.status = false;
                    }else{
                        item.status = true;
                    }
                };
            });

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
        console.log($scope.coachArrangements);
    }]);

    app.service("coachArrangementService",[function () {

        this.loadCoachArrangement = function () {
            var coachArrangements = [{
                id:1,
                coachName:'鞠董',
                courseName:'瑜伽',
                courseState:'上课中',
                time:'2017-6-2 18:00',
                traineeCount:[{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}].length,
                traineeName:[{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}],
                status:''
            },{
                id:2,
                coachName:'鞠董',
                courseName:'瑜伽',
                courseState:'已结束',
                time:'2017-6-2 18:00',
                traineeCount:[{traineeId:'444',name:'陈乐乐'},{traineeId:'555',name:'陆丹'},{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}].length,
                traineeName:[{traineeId:'444',name:'陈乐乐'},{traineeId:'555',name:'陆丹'},{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}],
                status:''
            },{
                id:3,
                coachName:'鞠董',
                courseName:'瑜伽',
                courseState:'上课中',
                time:'2017-6-2 20:00',
                traineeCount:[{traineeId:'111',name:'张三'}].length,
                traineeName:[{traineeId:'111',name:'张三'}],
                status:''
            },{
                id:4,
                coachName:'鞠董',
                courseName:'瑜伽',
                courseState:'已结束',
                time:'2017-6-2 21:00',
                traineeCount:[{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}].length,
                traineeName:[{traineeId:'111',name:'张三'},{traineeId:'222',name:'李四'},{traineeId:'333',name:'王五'}],
                status:''
            }];
            return coachArrangements;
        }
    }]);
    app.service("classesTodayService",[function () {
        this.loadSellingCoursers = function () {
            var sellingCoursers = [{
                traineeName:'张三',
                courseName:'瑜伽',
                coachName:'鞠董',
                courseCount:'1',
                money:'5000',
                time:'2017-6-2 19:00'
            },{
                traineeName:'张三',
                courseName:'瑜伽',
                coachName:'鞠董',
                courseCount:'1',
                money:'5000',
                time:'2017-6-2 19:00'
            },{
                traineeName:'张三',
                courseName:'瑜伽,体操技能',
                coachName:'鞠董',
                courseCount:'2',
                money:'12000',
                time:'2017-6-2 19:00'
            }];
            return sellingCoursers;
        }
    }]);

    app.service("shopopService",["shopopResource",function(shopopResource){
        this.loadbuycourse=function(index,size,callback){
            shopopResource.query({
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


    app.factory("shopopResource",["$resource",function($resource){
        var resourceUrl =  'api/buy-courses/today';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET'}
        });
    }]);


})();


(function(){
    'use strict';
    var app=angular.module('catpowerserverApp');
    app.config(["$stateProvider",function($stateProvider){
        $stateProvider.state('shopop', {
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
            url: 'shopop.new',
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
                        entity: function () {
                            return {
                                coachName: null,
                                coachPhone: null,
                                coachIntroduce: null,
                                coachPicture: null,
                                coachWechatopenid: null,
                                coachWechatname: null,
                                coachWechatpicture: null,
                                id: null
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
    }]);
})();
