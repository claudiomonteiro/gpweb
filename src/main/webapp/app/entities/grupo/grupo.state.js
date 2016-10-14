(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('grupo', {
            parent: 'entity',
            url: '/grupo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.grupo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grupo/grupos.html',
                    controller: 'GrupoController',
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
                    $translatePartialLoader.addPart('grupo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('grupo-detail', {
            parent: 'entity',
            url: '/grupo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.grupo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grupo/grupo-detail.html',
                    controller: 'GrupoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('grupo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Grupo', function($stateParams, Grupo) {
                    return Grupo.get({id : $stateParams.id});
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'grupo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('grupo-detail.edit', {
            parent: 'grupo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo/grupo-dialog.html',
                    controller: 'GrupoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Grupo', function(Grupo) {
                            return Grupo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grupo.new', {
            parent: 'grupo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo/grupo-dialog.html',
                    controller: 'GrupoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmGrupo: null,
                                vlComissao: 0.00,
                                vlDesconto: 0.00,
                                flPromo: false,
                                flDesco: false,
                                dtPromo: null,
                                dtOperacao: null,
                                flSemContagem: false,
                                flEnvio: false,
                                nnNovo: 1,
                                nnDia: null,
                                nnDiaSemana: null,
                                nnTipo: 0,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('grupo', null, { reload: true });
                }, function() {
                    $state.go('grupo');
                });
            }]
        })
        .state('grupo.edit', {
            parent: 'grupo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo/grupo-dialog.html',
                    controller: 'GrupoDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Grupo', function(Grupo) {
                            return Grupo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('grupo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grupo.delete', {
            parent: 'grupo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo/grupo-delete-dialog.html',
                    controller: 'GrupoDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Grupo', function(Grupo) {
                            return Grupo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('grupo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
