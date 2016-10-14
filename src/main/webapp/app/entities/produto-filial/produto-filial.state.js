(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('produto-filial', {
            parent: 'entity',
            url: '/produto-filial?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.produtoFilial.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto-filial/produto-filials.html',
                    controller: 'ProdutoFilialController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produtoFilial');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('produto-filial-detail', {
            parent: 'entity',
            url: '/produto-filial/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.produtoFilial.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto-filial/produto-filial-detail.html',
                    controller: 'ProdutoFilialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produtoFilial');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProdutoFilial', function($stateParams, ProdutoFilial) {
                    return ProdutoFilial.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('produto-filial.new', {
            parent: 'produto-filial',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-filial/produto-filial-dialog.html',
                    controller: 'ProdutoFilialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                qtEmbalagem: null,
                                qtMin: null,
                                qtMax: null,
                                qtSaldo: null,
                                dtVencimento: null,
                                vlCompra: null,
                                vlCusto: null,
                                vlCustoMedio: null,
                                vlMarkpVenda: null,
                                vlMarkpAtacado: null,
                                vlAtaVista: null,
                                vlMarkpAprazo: null,
                                vlAprazo: null,
                                vlMarkpAtaPrazo: null,
                                vlAtaAPrazo: null,
                                qtAtacado: null,
                                qtBonificacao: null,
                                flInativo: null,
                                flMateriaPrima: null,
                                flComposto: null,
                                nnPontos: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produto-filial', null, { reload: true });
                }, function() {
                    $state.go('produto-filial');
                });
            }]
        })
        .state('produto-filial.edit', {
            parent: 'produto-filial',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-filial/produto-filial-dialog.html',
                    controller: 'ProdutoFilialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProdutoFilial', function(ProdutoFilial) {
                            return ProdutoFilial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto-filial', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produto-filial.delete', {
            parent: 'produto-filial',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-filial/produto-filial-delete-dialog.html',
                    controller: 'ProdutoFilialDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProdutoFilial', function(ProdutoFilial) {
                            return ProdutoFilial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto-filial', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
