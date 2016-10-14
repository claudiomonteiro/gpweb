(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('UnidadeDetailController', UnidadeDetailController);

    UnidadeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState' , 'entity', 'Unidade'];

    function UnidadeDetailController($scope, $rootScope, $stateParams, previousState , entity, Unidade) {
        var vm = this;
        vm.unidade = entity;
        vm.previousState = previousState.name;
        
        var unsubscribe = $rootScope.$on('gpwebApp:unidadeUpdate', function(event, result) {
            vm.unidade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
