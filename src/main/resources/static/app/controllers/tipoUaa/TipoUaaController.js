var table;
var aplicacionTitulo = "Gestión";
angular.module('tipoUaa.controller').controller(
		'TipoUaaController',
		function($scope, $http, $mdDialog, $mdMedia, $resource) {

			$scope.showHints = false;
			
			table = $('#tblTipoUaa').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "uaaTipo",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "nombre"
				}, {
					"data" : "temporalNombre"
				} ]
			});

			$('#tblTipoUaa tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});

			$scope.status = '  ';
			$scope.myData = null;
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

			/*
			 * $http.get("../uaaTipo").then(function(response) { $scope.myData =
			 * response.data.data; });
			 */

			var url = 'uaaTipo/';
			var uaaTipoResource = $resource(url, {
				codigo : '@codigo'
			}, {
				adicionar : {
					url : url,
					method : 'POST',
					params : {}
				},
				modificar : {
					url : url + '/:codigo',
					method : 'PUT',
					params : {}
				},
				eliminar : {
					url : url + '/:codigo',
					method : 'DELETE',
					params : {}
				}
			});

			function DialogController($scope, $http, $mdDialog, accion, items) {
				$scope.accion = accion;
				$scope.uaaTipo = items;
				$scope.hide = function() {
					$mdDialog.hide();
				};
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
				$scope.answer = function(answer) {
					if (accion == "Adicionar") {
						uaaTipoResource.adicionar({}, $scope.uaaTipo, function(
								data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							$scope.MsgError = response.data.mensaje;
							$scope.showHints = true;
						})
					}
					if (accion == "Modificar") {
						uaaTipoResource.modificar({}, $scope.uaaTipo, function(
								data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							$scope.MsgError = response.data.mensaje;
							$scope.showHints = true;
						})
					}
					if (accion == "Eliminar") {
						uaaTipoResource.eliminar({}, $scope.uaaTipo, function(
								data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							$scope.MsgError = response.data.mensaje;
							$scope.showHints = true;
						})
					}
				};
			}

			function mostrarRespuesta(data) {
				table.ajax.reload();
				$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
						.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
						.ok('Aceptar'));
			}

			$scope.editar = function(accion) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
						&& $scope.customFullscreen;

				if (accion == "Adicionar") {
					$scope.uaaTipo = {};
				} else {
					if (accion != "Adicionar") {
						$scope.uaaTipo = table.row('.selected').data();
						if ($scope.uaaTipo.codigo == undefined) {
							alert("abortar");
						}
					}
				}
				$mdDialog.show({
					controller : DialogController,
					templateUrl : 'listaTipoUAA/formulario.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : $scope.uaaTipo,
						accion : accion
					}
				}).then(function(answer) {
				});
			};
		});
