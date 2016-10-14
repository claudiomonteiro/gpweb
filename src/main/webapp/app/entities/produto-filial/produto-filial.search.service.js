(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .factory('ProdutoFilialSearch', ProdutoFilialSearch);

    ProdutoFilialSearch.$inject = ['$resource'];

    function ProdutoFilialSearch($resource) {
        var resourceUrl =  'api/_search/produto-filials/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
