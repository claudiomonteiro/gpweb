(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('marca', {
            parent: 'entity',
            url: '/marca?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.marca.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marca/marcas.html',
                    controller: 'MarcaController',
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
                    $translatePartialLoader.addPart('marca');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('marca-detail', {
            parent: 'entity',
            url: '/marca/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.marca.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marca/marca-detail.html',
                    controller: 'MarcaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marca');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Marca', function($stateParams, Marca) {
                    return Marca.get({id : $stateParams.id});
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'marca',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('marca-detail.edit', {
            parent: 'marca-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marca.new', {
            parent: 'marca',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmMarca: null,
                                cdCnpj: null,
                                cdIe: null,
                                nnNumero: null,
                                dsComplemento: null,
                                cdTel: null,
                                cdFax: null,
                                flInativo: false,
                                nmFantasia: null,
                                vlComissao: 0.00,
                                dtOperacao: null,                                
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: true });
                }, function() {
                    $state.go('marca');
                });
            }]
        })
        .state('marca.edit', {
            parent: 'marca',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-dialog.html',
                    controller: 'MarcaDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marca.delete', {
            parent: 'marca',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marca/marca-delete-dialog.html',
                    controller: 'MarcaDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Marca', function(Marca) {
                            return Marca.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('marca', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
