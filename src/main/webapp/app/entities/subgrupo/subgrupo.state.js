(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subgrupo', {
            parent: 'entity',
            url: '/subgrupo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.subgrupo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subgrupo/subgrupos.html',
                    controller: 'SubgrupoController',
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
                    $translatePartialLoader.addPart('subgrupo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subgrupo-detail', {
            parent: 'entity',
            url: '/subgrupo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.subgrupo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subgrupo/subgrupo-detail.html',
                    controller: 'SubgrupoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subgrupo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subgrupo', function($stateParams, Subgrupo) {
                    return Subgrupo.get({id : $stateParams.id});
                }]
            }
        })
        .state('subgrupo.new', {
            parent: 'subgrupo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subgrupo/subgrupo-dialog.html',
                    controller: 'SubgrupoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmSubgrupo: null,
                                vlValor: null,
                                vlCusto: null,
                                dtOperacao: null,
                                flEnvio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subgrupo', null, { reload: true });
                }, function() {
                    $state.go('subgrupo');
                });
            }]
        })
        .state('subgrupo.edit', {
            parent: 'subgrupo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subgrupo/subgrupo-dialog.html',
                    controller: 'SubgrupoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subgrupo', function(Subgrupo) {
                            return Subgrupo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('subgrupo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subgrupo.delete', {
            parent: 'subgrupo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subgrupo/subgrupo-delete-dialog.html',
                    controller: 'SubgrupoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subgrupo', function(Subgrupo) {
                            return Subgrupo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('subgrupo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
