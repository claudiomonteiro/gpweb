(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('filial', {
            parent: 'entity',
            url: '/filial?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.filial.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filial/filials.html',
                    controller: 'FilialController',
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
                    $translatePartialLoader.addPart('filial');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('filial-detail', {
            parent: 'entity',
            url: '/filial/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gpwebApp.filial.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filial/filial-detail.html',
                    controller: 'FilialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('filial');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Filial', function($stateParams, Filial) {
                    return Filial.get({id : $stateParams.id});
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'filial',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('filial-detail.edit', {
            parent: 'filial-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filial/filial-dialog.html',
                    controller: 'FilialDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filial', function(Filial) {
                            return Filial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filial.new', {
            parent: 'filial',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filial/filial-dialog.html',
                    controller: 'FilialDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nmFilial: null,
                                nmRazao: null,
                                nnNumero: null,
                                cdCnpj: null,
                                cdIe: null,                       
                                cdTel: null,
                                cdTel1: null,
                                cdTel2: null,
                                cdFax: null,
                                dsComplemento: null,
                                dsPisCofins: null,                                                                
                                dsObs: null,
                                dsSite: null,
                                dtOperacao: null,
                                dsEmail: null,
                                flEnviaEmail: false,
                                flMatriz: false,
                                flTprec: false,
                                flInativo: false,                                
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('filial', null, { reload: true });
                }, function() {
                    $state.go('filial');
                });
            }]
        })
        .state('filial.edit', {
            parent: 'filial',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filial/filial-dialog.html',
                    controller: 'FilialDialogController',
                    controllerAs: 'vm',
                    animation: false,
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filial', function(Filial) {
                            return Filial.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('filial', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filial.delete', {
            parent: 'filial',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filial/filial-delete-dialog.html',
                    controller: 'FilialDeleteController',
                    controllerAs: 'vm',
                    animation: false,
                    size: 'md',
                    resolve: {
                        entity: ['Filial', function(Filial) {
                            return Filial.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('filial', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
