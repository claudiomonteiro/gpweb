(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('ProdutoFilialDeleteController',ProdutoFilialDeleteController);

    ProdutoFilialDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProdutoFilial'];

    function ProdutoFilialDeleteController($uibModalInstance, entity, ProdutoFilial) {
        var vm = this;

        vm.produtoFilial = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProdutoFilial.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
