angular.module('uaa.controller').controller(
		'UaaController',
		function($scope, $http, $mdDialog, $mdMedia, $resource) {

			var table;
			var aplicacionTitulo = "Gestión Uaa";
			
			$scope.showHints = false;

			table = $('#tblUaa').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "uaa/listar",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "uaaTipo.nombre"
				}, {
					"data" : "nombre"
				}, {
					"data" : "nombreCorto"
				}, {
					"data" : "nombreImpresion"
				}, {
					"data" : "acronimo"
				}, {
					"data" : "nombreDependencia"
				}, {
					"data" : "dependencia",
					"visible" : false
				}, {
					"data" : "sede.nombre"
				}, {
					"data" : "municipio.nombre"
				}, {
					"data" : "telefono"
				}, {
					"data" : "fax"
				}, {
					"data" : "direccion"
				}, {
					"data" : "email"
				}, {
					"data" : "emailPqr"
				}, {
					"data" : "pagina"
				}, {
					"data" : "centroCostos"
				}, {
					"data" : "estado",
					"visible" : false
				}, {
					"data" : "codigoTipoUaaDep",
					"visible" : false
				}, {
					"data" : "municipio.departamento",
					"visible" : false
				} ],
				"order": [[ 0, "desc" ]],
				"oLanguage": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"}}
			});

			$('#tblUaa tbody').on('click', 'tr', function() {
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

			var url = 'uaa/';
			var uaaResource = $resource(url, {
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

			function listarTipoUaa($http, $scope) {
				$http.get('uaaTipoAll').success(function(data) {
					$scope.JSONUaaTipo = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			
			function listarTipoUaaFac($http, $scope) {
				$http.get('uaaTipoAll/1').success(function(data) {
					$scope.JSONUaaTipoFac = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			function listarUaa($http, $scope, tipo, uaaMod) {
				$http.get('uaaDep?tipoUaa=' + tipo + '&uaaMod=' + uaaMod).success(function(data) {
					$scope.JSONUaa = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			function listarSedes($http, $scope) {
				$http.get('sedesAll?estado=1').success(function(data) {
					$scope.JSONSedes = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			function eliminar(){
				uaaResource.eliminar({},
						$scope.uaa,
						function(data) {
							$mdDialog.hide();
							mostrarRespuesta(data);
						}, function(response) {
							$mdDialog.hide();
							mostrarRespuesta(response.data);
						})
			}

			function DialogController($scope, $http, $mdDialog, accion, items) {

				$scope.accion = accion;
				$scope.uaa = items;
				if (accion == "Adicionar") {
					$scope.uaa = {
						"uaaTipo" : {
							"codigo" : null
						},
						"nombre" : null,
						"nombreCorto" : null,
						"nombreImpresion" : null,
						"sede" : {
							"codigo" : null
						},
						"municipio" : {
							"codigo" : null
						},
						"email" : null,
						"dependencia" : null,
						"emailPqr" : null,
						"pagina" : null,
						"direccion" : null,
						"fax" : null,
						"centroCostos" : null,
						"acronimo" : null,
						"telefono" : null
					}
				}

				$scope.JSONDepartamentos = [];
				listarTipoUaa($http, $scope);
				listarTipoUaaFac($http, $scope);
				listarSedes($http, $scope);

				if (accion != "Adicionar") {
					$scope.seleccionado = "( "+ $scope.uaa.nombre+ " )"; 
					listarUaa($http, $scope, $scope.uaa.codigoTipoUaaDep, $scope.uaa.dependencia);
				}

				$scope.hide = function() {
					$mdDialog.hide();
				};
				$scope.cancel = function() {
					$mdDialog.cancel();
				};

				$scope.answer = function(answer) {
					if (accion == "Adicionar") {
						uaaResource.adicionar({}, $scope.uaa, function(data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							$scope.MsgError = response.data.mensaje;
							$scope.showHints = true;
						})
					}
					if (accion == "Modificar") {
						uaaResource.modificar({}, $scope.uaa, function(data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							$scope.MsgError = response.data.mensaje;
							$scope.showHints = true;
						})
					}
				};
				$scope.obtenerUaa = function() {
					$scope.uaa.dependencia = null;
					listarUaa($http, $scope, $scope.uaa.codigoTipoUaaDep, 0);
				}
			}

			function mostrarRespuesta(data) {
				table.ajax.reload();
				$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
						.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
						.ok('Aceptar'));
			}
			$scope.dialogEliminar = function(ev) {

				$scope.uaa = table.row('.selected').data();
				if ($scope.uaa == undefined) {
					data={
							mensaje: "Debe seleccionar un registro para realizar esta acción."
					}
					mostrarRespuesta(data);
					return false;
				}
			    var confirm = $mdDialog.confirm()
			          .title('Quieres eliminar este registro ('+$scope.uaa.nombreCorto+')?')
			          .textContent('Una vez eliminado no se podra recuperar el registro.')
			          .ariaLabel('Asignatura')
			          .targetEvent(ev)
			          .ok('Eliminar!')
			          .cancel('Cancelar');
			    $mdDialog.show(confirm).then(function() {
			    	eliminar();
			    }, function() {
			      
			    });
			  };

			$scope.editar = function(accion) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
						&& $scope.customFullscreen;

				if (accion == "Adicionar") {
					$scope.uaa = {};
				} else {
					if (accion != "Adicionar"){
						$scope.uaa = table.row('.selected').data();
						if ($scope.uaa == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;							
						}
					}
				}
				$mdDialog.show({
					controller : DialogController,
					templateUrl : 'UAA/forma.uaa.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : $scope.uaa,
						accion : accion
					}
				}).then(function(answer) {

				});
			};
		});

$(document).ready(function() {
	$('#tblUaa thead th').click(function() {

	})
})