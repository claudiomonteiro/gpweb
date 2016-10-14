(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('PromocaoDetailController', PromocaoDetailController);

    PromocaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Promocao'];

    function PromocaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Promocao) {
        var vm = this;

        vm.promocao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gpwebApp:promocaoUpdate', function(event, result) {
            vm.promocao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
