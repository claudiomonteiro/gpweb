(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('unidade', {
            parent: 'entity',
            url: '/unidade?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.unidade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidade/unidades.html',
                    controller: 'UnidadeController',
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
                    $translatePartialLoader.addPart('unidade');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('unidade-detail', {
            parent: 'entity',
            url: '/unidade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.unidade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidade/unidade-detail.html',
                    controller: 'UnidadeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unidade');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Unidade', function($stateParams, Unidade) {
                    return Unidade.get({id : $stateParams.id});
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'unidade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('unidade-detail.edit', {
            parent: 'unidade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidade/unidade-dialog.html',
                    controller: 'UnidadeDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unidade', function(Unidade) {
                            return Unidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidade.new', {
            parent: 'unidade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidade/unidade-dialog.html',
                    controller: 'UnidadeDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmUnidade: null,
                                sgUnidade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('unidade', null, { reload: true });
                }, function() {
                    $state.go('unidade');
                });
            }]
        })
        .state('unidade.edit', {
            parent: 'unidade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidade/unidade-dialog.html',
                    controller: 'UnidadeDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unidade', function(Unidade) {
                            return Unidade.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidade.delete', {
            parent: 'unidade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidade/unidade-delete-dialog.html',
                    controller: 'UnidadeDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Unidade', function(Unidade) {
                            return Unidade.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
