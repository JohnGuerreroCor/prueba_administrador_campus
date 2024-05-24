angular.module('resoluciones.controller').controller(
		'ResolucionesController',
		function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {

			var table;
			var aplicacionTitulo = "Gestión Resoluciones.";
			
			$scope.showHints = false;

			table = $('#tblResoluciones').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "resoluciones/lista",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "uaa.codigo",
					"visible" : false
				}, {
					"data" : "numero"
				}, {
					"data" : "descripcion"
				}, {
					"data" : "fecha"
				}, {
					"data" : "uaa.uaaTipo.codigo",
					"visible" : false
				}, {
					"data" : "uaa.nombre"
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

			$('#tblResoluciones tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});

			$scope.status = '  ';
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

			var url = 'resoluciones/';
			var resolucionResource = $resource(url, {
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
			function listarUaa($http, $scope, tipo, uaaMod) {
				
				if(uaaMod == undefined){
					uaaMod = 0;
				}
				// $scope.asignatura.codigoUaa = '';
				$http.get('uaaAll?tipoUaa=' + tipo +'&uaaMod='+uaaMod).success(function(data) {
					$scope.JSONUaa = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			function eliminar(){
				resolucionResource.eliminar({}, $scope.resolucion,
						function(data) {
							$mdDialog.hide();
							mostrarRespuesta(data);
						}, function(response) {
							$mdDialog.hide();
							mostrarRespuesta(response.data);
						})
			}
			function DialogController($scope, $http, $mdDialog, accion, items, maxDate) {

				$scope.accion = accion;
				listarTipoUaa($http, $scope);
				$scope.resolucion = items;

				$scope.maxDate = maxDate;
				
				if ($scope.accion != 'Adicionar') {
					$scope.seleccionado = "( "+ $scope.resolucion.numero+ " )"; 
					listarUaa($http, $scope, $scope.resolucion.uaa.uaaTipo.codigo, $scope.resolucion.uaa.codigo);
				}

				if ($scope.accion == 'Adicionar') {
					$scope.resolucion = {
						uaaTipo : {
							codigo : null,
						},
						uaa : {
							codigo : null
						},
						numero : null,
						descripcion : null,
						fecha : null
					}
				}
				$scope.hide = function() {
					$mdDialog.hide();
				};

				$scope.cancel = function() {
					if (accion == "Modificar") {
						$scope.resolucion.fecha = $filter('date')($scope.resolucion.fecha, "yyyy-MM-dd");
					}
					$mdDialog.cancel();
				};

				$scope.answer = function(answer) {
					if ($scope.accion == 'Adicionar') {
						//$scope.resolucion.fecha = $filter('date')($scope.resolucion.fecha, "yyyy-MM-dd");
						resolucionResource.adicionar({}, $scope.resolucion,
								function(data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
								})
					}
					if (accion == "Modificar") {
						resolucionResource.modificar({}, $scope.resolucion,
								function(data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
								})
					}
				};
				$scope.obtenerUaa = function() {
					$scope.resolucion.uaa.codigo = null;
					listarUaa($http, $scope, $scope.resolucion.uaa.uaaTipo.codigo, 0);
				}
			}

			function mostrarRespuesta(data) {
				table.ajax.reload();
				$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
						.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
						.ok('Aceptar'));
			}
			$scope.dialogEliminar = function(ev) {
				$scope.resolucion = table.row('.selected').data();
				if ($scope.resolucion == undefined) {
					data={
							mensaje: "Debe seleccionar un registro para realizar esta acción."
					}
					mostrarRespuesta(data);
					return false;
				}
			    var confirm = $mdDialog.confirm()
			          .title('Quieres eliminar este registro ('+$scope.resolucion.numero+')?')
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
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;

				if (accion == "Adicionar") {
					$scope.resolucion = {};
				} else {
					if (accion != "Adicionar") {
						$scope.resolucion = table.row('.selected').data();
						if ($scope.resolucion == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
						if ($scope.resolucion.fecha != null) {
							var fechaForma = $filter('date')($scope.resolucion.fecha, "M/d/yyyy");
							$scope.resolucion.fecha = new Date(fechaForma);
						}

					}
				}
				$mdDialog.show({
					controller : DialogController,
					templateUrl : 'listaResoluciones/forma.resoluciones.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : $scope.resolucion,
						accion : accion,
						maxDate : new Date()
					}
				}).then(function(answer) {
				});
			};
		});
