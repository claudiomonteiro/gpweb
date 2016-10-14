'use strict';

describe('Controller Tests', function() {

    describe('ProdutoFilial Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProdutoFilial, MockProduto, MockFilial, MockPromocao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProdutoFilial = jasmine.createSpy('MockProdutoFilial');
            MockProduto = jasmine.createSpy('MockProduto');
            MockFilial = jasmine.createSpy('MockFilial');
            MockPromocao = jasmine.createSpy('MockPromocao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProdutoFilial': MockProdutoFilial,
                'Produto': MockProduto,
                'Filial': MockFilial,
                'Promocao': MockPromocao
            };
            createController = function() {
                $injector.get('$controller')("ProdutoFilialDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gpwebApp:produtoFilialUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
