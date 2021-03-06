(function() {
    'use strict';

    angular
        .module('catpowerserverApp')
        .controller('FriendyShopDetailController', FriendyShopDetailController);

    FriendyShopDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FriendyShop'];

    function FriendyShopDetailController($scope, $rootScope, $stateParams, previousState, entity, FriendyShop) {
        var vm = this;

        vm.friendyShop = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('catpowerserverApp:friendyShopUpdate', function(event, result) {
            vm.friendyShop = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
