(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('ProdutoDetailController', ProdutoDetailController);

    ProdutoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Produto', 'Grupo', 'Marca', 'Unidade', 'ClassProduto', 'Subgrupo'];

    function ProdutoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Produto, Grupo, Marca, Unidade, ClassProduto, Subgrupo) {
        var vm = this;

        vm.produto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('gpwebApp:produtoUpdate', function(event, result) {
            vm.produto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
