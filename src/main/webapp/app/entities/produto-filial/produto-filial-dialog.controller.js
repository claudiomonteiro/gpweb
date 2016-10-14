(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('ProdutoFilialDialogController', ProdutoFilialDialogController);

    ProdutoFilialDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'DataUtils', '$uibModalInstance', '$q', 'entity', 'ProdutoFilial', 'Produto', 'Filial', 'Promocao', 'NewSelectService'];

    function ProdutoFilialDialogController ($timeout, $scope, $stateParams, DataUtils, $uibModalInstance, $q, entity, ProdutoFilial, Produto, Filial, Promocao, NewSelectService) {
        var vm = this;

        vm.produtoFilial = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.getProductDetail = getProductDetail;
        
        vm.obj = NewSelectService.obj;
        
        vm.filials = Filial.query();
        vm.promocaos = Promocao.query({filter: 'produtofilial-is-null'});
        $q.all([vm.produtoFilial.$promise, vm.promocaos.$promise]).then(function() {
            if (!vm.produtoFilial.promocao || !vm.produtoFilial.promocao.id) {
                return $q.reject();
            }
            return Promocao.get({id : vm.produtoFilial.promocao.id}).$promise;
        }).then(function(promocao) {
            vm.promocaos.push(promocao);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produtoFilial.id !== null) {
                ProdutoFilial.update(vm.produtoFilial, onSaveSuccess, onSaveError);
            } else {
                ProdutoFilial.save(vm.produtoFilial, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gpwebApp:produtoFilialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dtVencimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        function getProductDetail() {        	
        	if(vm.produtoFilial.produto && vm.produtoFilial.produto.id){
        		Produto.get({id : vm.produtoFilial.produto.id}).$promise.then(function(data){     
        			vm.produtoFilial.produto = data;
        		},function(){
        			console.log('Erro ao buscar dados do produto: ' + vm.produtoFilial.produto.id);
        		});
        	}
        }
        
        $scope.$watch('vm.produtoFilial.produto.id', function() {
        	getProductDetail();
        });
        
    }
})();
