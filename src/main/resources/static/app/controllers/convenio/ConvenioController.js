var table;
var aplicacionTitulo = "Gestión Convenio";
angular
		.module('convenio.controller')
		.controller('ConvenioController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {
					
					$scope.showHints = false;
					
					table = $('#tblConvenio').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax": {
							"url": "convenios/lista",
							"type": "POST"
						},
						"columns" : [ {
							"data" : "codigo"
						}, {
							"data" : "descripcion"
						}, {
							"data" : "resolucion.numero"
						}, {
							"data" : "uaa.nombre"
						}, {
							"data" : "fecha_creacion"
						}, {
							"data" : "fecha_terminacion"
						}, {
							"data" : "uaa.uaaTipo.codigo",
							"visible":false
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

					$('#tblConvenio tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'convenios/';
					var convenioResource = $resource(url, {
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

					function listarResolucion($http, $scope, resMod) {
						$http.get('resolucionLista/'+resMod).success(function(data) {
							$scope.JSONResolucion = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarTipoUaa($http, $scope) {
						$http.get('uaaTipoAll').success(function(data) {
							$scope.JSONUaaTipo = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarUaa($http, $scope, tipo, uaaMod) {
						//$scope.asignatura.codigoUaa = '';
						$http.get('uaaAll?tipoUaa=' + tipo + '&uaaMod=' + uaaMod).success(
								function(data) {
									$scope.JSONUaa = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function eliminar(){
						convenioResource.eliminar({},
								$scope.convenio, function(data) {
									$mdDialog.hide();
									mostrarRespuesta(data);
								}, function(response) {
									$mdDialog.hide();
									mostrarRespuesta(response.data);
								})
					}
					
					function DialogController($scope, $http, $mdDialog, accion, items, maxDate) {
						
						$scope.maxDate = maxDate;
						$scope.accion = accion;
						$scope.convenio = items;
						listarTipoUaa($http, $scope);
						if (accion != "Adicionar") {
							listarUaa($http, $scope, $scope.convenio.uaa.uaaTipo.codigo, $scope.convenio.uaa.codigo);
							listarResolucion($http, $scope, $scope.convenio.resolucion.codigo);
						}else{
							listarResolucion($http, $scope, 0);
						}
						$scope.hide = function() {
							$mdDialog.hide();
						};
						$scope.cancel = function() {
							if (accion == "Modificar") {
								$scope.convenio.fecha_creacion = $filter('date')($scope.convenio.fecha_creacion, "yyyy-MM-dd");
								$scope.convenio.fecha_terminacion = $filter('date')($scope.convenio.fecha_terminacion, "yyyy-MM-dd");
							}
							$mdDialog.cancel();
						};
						$scope.answer = function(answer) {
							if (accion == "Adicionar") {
								convenioResource.adicionar({},$scope.convenio, function(data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
								})
							}
							if (accion == "Modificar") {
								convenioResource.modificar({},$scope.convenio, function(data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
								})
							}
						};
						$scope.obtenerUaa = function() {
							listarUaa($http, $scope, $scope.convenio.uaa.uaaTipo.codigo, 0);
						}
					}

					function mostrarRespuesta(data) {
						table.ajax.reload();
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo).textContent(
										data.mensaje).ariaLabel(
										aplicacionTitulo).ok('Aceptar'));
					}

					$scope.dialogEliminar = function(ev) {
					    // Appending dialog to document.body to cover sidenav in docs app
						$scope.convenio = table.row('.selected').data();
						if ($scope.convenio == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
					    var confirm = $mdDialog.confirm()
					          .title('Quieres eliminar este registro?')
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
							$scope.convenio = {};
						} else {
							if (accion != "Adicionar") {
								$scope.convenio = table.row('.selected').data();
								if ($scope.convenio == undefined) {
									data={
											mensaje: "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}
								if ($scope.convenio.fecha_creacion != null) {
									var fecha_creacion = $filter('date')($scope.convenio.fecha_creacion, "M/d/yyyy");
									$scope.convenio.fecha_creacion = new Date(fecha_creacion);
								}
								if ($scope.convenio.fecha_terminacion != null) {
									var fecha_terminacion = $filter('date')($scope.convenio.fecha_terminacion, "M/d/yyyy");
									$scope.convenio.fecha_terminacion = new Date(fecha_terminacion);
								}
							}
						}
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'convenio/forma.convenio.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.convenio,
								accion : accion,
								maxDate : new Date()
							}
						}).then(
								function(answer) {
								});
					};
				});
