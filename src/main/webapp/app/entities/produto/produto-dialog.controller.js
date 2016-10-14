(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('ProdutoDialogController', ProdutoDialogController);

    ProdutoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Produto', 'Marca', 'Unidade', 'ClassProduto', 'NewSelectService', 'Filial'];

    function ProdutoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Produto, Marca, Unidade, ClassProduto, NewSelectService, Filial) {
        var vm = this;

        vm.produto = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.obj = NewSelectService.obj;
        
        //When is a new record or there isn't filiais selected then load all filiais
        if(!vm.produto.filialsNotUsed){
        	vm.produto.filialsNotUsed = Filial.query();
        }
        
        vm.demoOptions = {
        		field: 'nmFilial',
        		title: 'Filiais correspondentes',
        		filterPlaceHolder: 'Filtrar',
        		labelAll: 'Todos',
        		labelSelected: 'Selecionados',
        		helpMessage: ' Selecione quais filiais utilizarão este produto.',
        		/* angular will use this to filter your lists */
        		orderProperty: 'id',
        		/* this contains the initial list of all items (i.e. the left side) */
        		items: vm.produto.filialsNotUsed,
        		/* this list should be initialized as empty or with any pre-selected items */
        		selectedItems: vm.produto.filials || []
        };
        
        vm.marcas = Marca.query({filter: 'produto-is-null'});
        $q.all([vm.produto.$promise, vm.marcas.$promise]).then(function() {
            if (!vm.produto.marca || !vm.produto.marca.id) {
                return $q.reject();
            }
            return Marca.get({id : vm.produto.marca.id}).$promise;
        }).then(function(marca) {
            vm.marcas.push(marca);
        });
        vm.unidades = Unidade.query({filter: 'produto-is-null'});
        $q.all([vm.produto.$promise, vm.unidades.$promise]).then(function() {
            if (!vm.produto.unidade || !vm.produto.unidade.id) {
                return $q.reject();
            }
            return Unidade.get({id : vm.produto.unidade.id}).$promise;
        }).then(function(unidade) {
            vm.unidades.push(unidade);
        });
        
        vm.classprodutos = ClassProduto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        var onBeforeSaveOrUpdate = function(){       	        	
        	
        	//Setting to uppercase
        	vm.produto.nmProduto = angular.uppercase(vm.produto.nmProduto);
        	vm.produto.cdBarras  = angular.uppercase(vm.produto.cdBarras);
        	vm.produto.cdNcm     = angular.uppercase(vm.produto.cdNcm);
        	vm.produto.cdGtin    = angular.uppercase(vm.produto.cdGtin);
        	vm.produto.cdAnp     = angular.uppercase(vm.produto.cdAnp);
        	vm.produto.dsAnp     = angular.uppercase(vm.produto.dsAnp);
        	vm.produto.cdContaContabil = angular.uppercase(vm.produto.cdContaContabil);
        	vm.produto.dsInformacoes = angular.uppercase(vm.produto.dsInformacoes);
        };

        function save () {
            vm.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if (vm.produto.id !== null) {
                Produto.update(vm.produto, onSaveSuccess, onSaveError);
            } else {
                Produto.save(vm.produto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gpwebApp:produtoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setBlImagem = function ($file, produto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        produto.blImagem = base64Data;
                        produto.blImagemContentType = $file.type;
                    });
                });
            }
        };

    }
})();
