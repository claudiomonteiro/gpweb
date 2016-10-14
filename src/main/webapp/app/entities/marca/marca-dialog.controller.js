(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('MarcaDialogController', MarcaDialogController);

    MarcaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Marca'];

    function MarcaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Marca) {
        var vm = this;
        vm.marca = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('gpwebApp:marcaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){            
            //Setting to uppercase
          	vm.marca.nmMarca = angular.uppercase(vm.marca.nmMarca);
            vm.marca.nmFantasia = angular.uppercase(vm.marca.nmFantasia);
            vm.marca.cdIe = angular.uppercase(vm.marca.cdIe);
        }; 

        vm.save = function () {
            vm.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if (vm.marca.id !== null) {
                Marca.update(vm.marca, onSaveSuccess, onSaveError);
            } else {
                Marca.save(vm.marca, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dtOperacao = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
