(function() {
    'use strict';
    angular
        .module('gpwebApp')
        .factory('Produto', Produto);

    Produto.$inject = ['$resource', 'DateUtils'];

    function Produto ($resource, DateUtils) {
        var resourceUrl =  'api/produtos/:id';
        
        //This convertion is necessary cause the component scselect returns an object date
        function converteDateFromScSelect(valueDate){
        	return DateUtils.convertLocalDateToServer(  				
    				new Date(valueDate.year, valueDate.month, valueDate.day)
    		);
        };

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                	
                	//Convert Date from Grupo
                	if(data.grupo){                		
                		data.grupo.dtOperacao = converteDateFromScSelect(data.grupo.dtOperacao);                		
                	};
                	
                	//Convert Date from SubGrupo
                	if(data.subgrupo){                		
                		data.subgrupo.dtOperacao = converteDateFromScSelect(data.subgrupo.dtOperacao);                		
                	};
                	
                	//Convert Date from Marca
                	if(data.marca){                		
                		data.marca.dtOperacao = converteDateFromScSelect(data.marca.dtOperacao);
                	};
                	
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                	
                	//Convert Date from Grupo
                	if(data.grupo){                		
                		data.grupo.dtOperacao = converteDateFromScSelect(data.grupo.dtOperacao);                		
                	};
                	
                	//Convert Date from SubGrupo
                	if(data.subgrupo){                		
                		data.subgrupo.dtOperacao = converteDateFromScSelect(data.subgrupo.dtOperacao);               		
                	};
                	
                	//Convert Date from Marca
                	if(data.marca){                		
                		data.marca.dtOperacao = converteDateFromScSelect(data.marca.dtOperacao);  		
                	};
                	
                    return angular.toJson(data);
                }
            }
        });
    }
})();
