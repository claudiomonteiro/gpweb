(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('produto', {
            parent: 'entity',
            url: '/produto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.produto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto/produtos.html',
                    controller: 'ProdutoController',
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
                    $translatePartialLoader.addPart('produto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('produto-detail', {
            parent: 'entity',
            url: '/produto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.produto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto/produto-detail.html',
                    controller: 'ProdutoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Produto', function($stateParams, Produto) {
                    return Produto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'produto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('produto-detail.edit', {
            parent: 'produto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto/produto-dialog.html',
                    controller: 'ProdutoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produto', function(Produto) {
                            return Produto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produto.new', {
            parent: 'produto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto/produto-dialog.html',
                    controller: 'ProdutoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cdProduto: null,
                                cdBarras: null,
                                nmProduto: null,
                                cdNcm: null,
                                cdGtin: null,
                                cdAnp: null,
                                dsAnp: null,
                                cdContaContabil: null,
                                flBalanca: null,
                                flInativo: null,
                                flSngpc: null,
                                flUsoProlongado: null,
                                vlVenda: 0.00,
                                vlEstoque: 0.00,
                                dsInformacoes: null,
                                blImagem: null,
                                blImagemContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produto', null, { reload: true });
                }, function() {
                    $state.go('produto');
                });
            }]
        })
        .state('produto.edit', {
            parent: 'produto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto/produto-dialog.html',
                    controller: 'ProdutoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produto', function(Produto) {
                            return Produto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produto.delete', {
            parent: 'produto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto/produto-delete-dialog.html',
                    controller: 'ProdutoDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Produto', function(Produto) {
                            return Produto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
