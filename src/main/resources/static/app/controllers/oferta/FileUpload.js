(function() {
	'use strict'
	angular.module('ofertaAcademica.controller').service('FileUpload',
			[ '$http', FileUpload ]);
	function FileUpload($http) {
		/*this.uploadFileToUrl = function(file, uploadUrl) {
			var fd = new FormData();
			fd.append('file', file);
			$http.post(uploadUrl, fd, {
				headers : {
					'Content-Type' : undefined
				},
				transformRequest : angular.identity
			}).then(function successCallback(response) {
				file = null;
				console.log("=====Success====");
				console.log(response.data);

			}, function errorCallback(response) {
				console.log("========error======");
				console.log(response.data);
			});
			/*
			 * .success(function() { file = null }).error(function() { });
			 */
		//}
	}
})();