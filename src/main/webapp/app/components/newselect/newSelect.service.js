(function() {
    'use strict';

    angular
        .module('gpwebApp')
        .factory('NewSelectService', NewSelectService);
    
    NewSelectService.$inject = ['$http'];

    function NewSelectService ($http) {
        

	    	var obj = {};
	    	obj.asyncPageLimit = 20;
	    	obj.totalResults = 0;
			 
	    	obj.searchAsync = function(searchText, page, url, field) {
	    		
	          if (!searchText) {
	            return [];
	          }
	          //ver questão do localhost          
	          return $http.get(url, { 
	            params: {
	              //api_key: '9b0cdcf446cc96dea3e747787ad23575',
	              field: field,
	              query: searchText.toUpperCase(),
	              sort: 'id,asc',              
	              size: obj.asyncPageLimit,
	              page: page - 1,
	              format: 'json',
	              callback: 'JSON_CALLBACK'
	            }
	          }).then(function(result) {
	        	  obj.totalResults = result.data.results['opensearch:totalResults'];
	        	  searchText = null;
	            return result.data.results.trackmatches.track;
	          });
	        };
	
//	        obj.groupBy = function(item) {
//	          if (item.name[0] >= 'A' && item.name[0] <= 'M') {
//	            return 'From A - M';
//	          }
//	          if (item.name[0] >= 'N' && item.name[0] <= 'Z') {
//	            return 'From N - Z';
//	          }
//	        };        
	        
	     
        return{
        	obj: obj        	
        };
    }
})();
