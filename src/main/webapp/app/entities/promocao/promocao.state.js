(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('promocao', {
            parent: 'entity',
            url: '/promocao?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.promocao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promocao/promocaos.html',
                    controller: 'PromocaoController',
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
                    $translatePartialLoader.addPart('promocao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('promocao-detail', {
            parent: 'entity',
            url: '/promocao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.promocao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promocao/promocao-detail.html',
                    controller: 'PromocaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('promocao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Promocao', function($stateParams, Promocao) {
                    return Promocao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'promocao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('promocao-detail.edit', {
            parent: 'promocao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promocao/promocao-dialog.html',
                    controller: 'PromocaoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promocao', function(Promocao) {
                            return Promocao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promocao.new', {
            parent: 'promocao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promocao/promocao-dialog.html',
                    controller: 'PromocaoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmPromocao: null,
                                dtVencimento: null,
                                nnDiaData: null,
                                nnDiaSemana: null,
                                flInativo: false,
                                vlPromocao: 0.00,                                
                                nnTipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('promocao', null, { reload: true });
                }, function() {
                    $state.go('promocao');
                });
            }]
        })
        .state('promocao.edit', {
            parent: 'promocao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promocao/promocao-dialog.html',
                    controller: 'PromocaoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promocao', function(Promocao) {
                            return Promocao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promocao', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promocao.delete', {
            parent: 'promocao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promocao/promocao-delete-dialog.html',
                    controller: 'PromocaoDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Promocao', function(Promocao) {
                            return Promocao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promocao', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
