(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('ProdutoFilialDetailController', ProdutoFilialDetailController);

    ProdutoFilialDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ProdutoFilial', 'Produto', 'Filial', 'Promocao'];

    function ProdutoFilialDetailController($scope, $rootScope, $stateParams, entity, ProdutoFilial, Produto, Filial, Promocao) {
        var vm = this;

        vm.produtoFilial = entity;

        var unsubscribe = $rootScope.$on('gpwebApp:produtoFilialUpdate', function(event, result) {
            vm.produtoFilial = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
