(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('GrupoDetailController', GrupoDetailController);

    GrupoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Grupo'];

    function GrupoDetailController($scope, $rootScope, $stateParams, previousState, entity, Grupo) {
        var vm = this;
        vm.grupo = entity;
        vm.previousState = previousState.name;
        
        var unsubscribe = $rootScope.$on('gpwebApp:grupoUpdate', function(event, result) {
            vm.grupo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
