angular.module('certificados.controller').controller('CertificadosController',
		
		function($scope, $http, $mdDialog, $mdMedia, $resource) {

			var table;
			var aplicacionTitulo = "Gestión Certificados";

			$scope.showHints = false;

			function listarCombobox($http, $scope) {
				$http.get('ProgramasMatriculados').success(function(data) {
					$scope.JSONUsuariosMatriculados = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}

			listarCombobox($http, $scope);

			$scope.cargarMatriculadosCertificados = function(codigoOferta) {
				$http.get('matriculadosOferta?oferta=' + codigoOferta).success(
						function(data) {
							$scope.JSONPersonasCertificados = data;
						}).error(function(data) {
					console.log('Error: ' + data);
				});
			}

			$scope.enviarCertificado = function() {
				alert();
				console.log($scope.sendMail);
				/*$http.get('matriculadosOferta?oferta=' + codigoOferta).success(
						function(data) {
							$scope.JSONPersonasCertificados = data;
						}).error(function(data) {
					console.log('Error: ' + data);
				});*/
			}

		})