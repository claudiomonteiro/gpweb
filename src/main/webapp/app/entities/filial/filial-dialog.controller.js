(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .controller('FilialDialogController', FilialDialogController);

    FilialDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Filial'];

    function FilialDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Filial) {
        var vm = this;
        vm.filial = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('gpwebApp:filialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){
            //Setting to uppercase
        	vm.filial.nmFilial = angular.uppercase(vm.filial.nmFilial);
            vm.filial.nmRazao = angular.uppercase(vm.filial.nmRazao);
            vm.filial.cdIe = angular.uppercase(vm.filial.cdIe);
            vm.filial.dsComplemento = angular.uppercase(vm.filial.dsComplemento);
            vm.filial.dsPisCofins = angular.uppercase(vm.filial.dsPisCofins);
            vm.filial.dsObs = angular.uppercase(vm.filial.dsObs);
            vm.filial.dsSite = angular.uppercase(vm.filial.dsSite);
            vm.filial.dsEmail = angular.uppercase(vm.filial.dsEmail);
        };

        vm.save = function () {
            vm.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if (vm.filial.id !== null) {
                Filial.update(vm.filial, onSaveSuccess, onSaveError);
            } else {
                Filial.save(vm.filial, onSaveSuccess, onSaveError);
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
