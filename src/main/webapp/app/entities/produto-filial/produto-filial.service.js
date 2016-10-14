(function() {
    'use strict';
    angular
        .module('gpwebApp')
        .factory('ProdutoFilial', ProdutoFilial);

    ProdutoFilial.$inject = ['$resource', 'DateUtils'];

    function ProdutoFilial ($resource, DateUtils) {
        var resourceUrl =  'api/produto-filials/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dtVencimento = DateUtils.convertLocalDateFromServer(data.dtVencimento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dtVencimento = DateUtils.convertLocalDateToServer(data.dtVencimento);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dtVencimento = DateUtils.convertLocalDateToServer(data.dtVencimento);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
