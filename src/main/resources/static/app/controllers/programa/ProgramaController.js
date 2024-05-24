angular
		.module('programa.controller')
		.controller(
				'ProgramaController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {

					var table;
					var aplicacionTitulo = "Gestión Programa";
					
					$scope.showHints = false;

					table = $('#tblPrograma')
							.DataTable(
									{
										"processing" : true,
										"serverSide" : true,
										"ajax": {
											"url": "programas/lista",
											"type": "POST"
										},	
										"columns" : [ {
											"data" : "codigo"
										}, {
											"data" : "titulo_otorgado"
										}, {
											"data" : "uaa.nombre"
										}, {
											"data" : "sede.nombre"
										}, {
											"data" : "nivelAcademico.nombre"
										}, {
											"data" : "jornada.nombre"
										}, {
											"data" : "resolucion.numero"
										}, {
											"data" : "creacion"
										}, {
											"data" : "modalidad.nombre"
										}, {
											"data" : "convenio.descripcion"
										}, {
											"data" : "horario"
										}, {
											"data" : "programaEstado.nombre"
										}, {
											"data" : "propio"
										}, {
											"data" : "nbc.nombre"
										}, {
											"data" : "calendario"
										}, {
											"data" : "uaa.uaaTipo.codigo",
											"visible" : false
										} ],
										"order" : [ [ 0, "desc" ] ],
										"oLanguage" : {
											"sProcessing" : "Procesando...",
											"sLengthMenu" : "Mostrar _MENU_ registros",
											"sZeroRecords" : "No se encontraron resultados",
											"sEmptyTable" : "Ningún dato disponible en esta tabla",
											"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
											"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
											"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
											"sInfoPostFix" : "",
											"sSearch" : "Buscar:",
											"sUrl" : "",
											"sInfoThousands" : ",",
											"sLoadingRecords" : "Cargando...",
											"oPaginate" : {
												"sFirst" : "Primero",
												"sLast" : "Último",
												"sNext" : "Siguiente",
												"sPrevious" : "Anterior"
											}
										},
										"columnDefs": [ {
											"targets": 3,
											"orderable": false
											}]
									});

					$('#tblPrograma tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});

					$scope.status = '';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'programas/';

					var programaResource = $resource(url, {
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

					function listarSedes($http, $scope) {
						$http.get('sedesAll?estado=1').success(function(data) {
							$scope.JSONSedes = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarResolucion($http, $scope, resMod) {
						$http.get('resolucionLista/'+resMod).success(function(data) {
							$scope.JSONResolucion = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarEstados($http, $scope) {
						$http.get('programasEstadoLista').success(
								function(data) {
									$scope.JSONEstados = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarNbc($http, $scope) {
						$http.get('nbcLista').success(function(data) {
							$scope.JSONNbc = data;
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
						$http.get('uaaAll?tipoUaa=' + tipo + '&uaaMod=' + uaaMod)
								.success(function(data) {
									$scope.JSONUaa = data;
								}).error(function(data) {
									console.log('Error: ' + data);
								});
					}
					
					function listarNivelAcademico($http, $scope) {
						$http.get('nivelAcademicoLista').success(
								function(data) {
									$scope.JSONNivelAcademico = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarJornada($http, $scope) {
						$http.get('jornadaLista').success(function(data) {
							$scope.JSONJornada = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarConvenio($http, $scope) {
						$http.get('convenioLista').success(function(data) {
							$scope.JSONConvenio = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarModalidad($http, $scope) {
						$http.get('modalidadLista').success(function(data) {
							$scope.JSONModalidad = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}

					function eliminar() {
						programaResource.eliminar({}, $scope.programa,
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
						$scope.programa = items;
						$scope.maxDate = maxDate;

						if (accion != "Adicionar") {
							$scope.seleccionado = '('+$scope.programa.uaa.nombre+')';
							listarUaa($http, $scope, $scope.programa.uaa.uaaTipo.codigo, $scope.programa.uaa.codigo);
							listarResolucion($http, $scope, $scope.programa.resolucion.codigo);
						}else{
							listarResolucion($http, $scope, 0);
							$scope.programa ={
								titulo_otorgado: null,
								horario: null,
								resolucion: {codigo: null},
								calendario: null,
								convenio: {codigo: null},
								creacion: null,
								jornada:{codigo: null},
								modalidad:{codigo: null},
								nbc:{codigo: null},
								nivelAcademico:{codigo: null},
								programaEstado:{codigo: null},
								resolucion:{codigo: null},
								sede:{codigo: null},
								titulo_otorgado:null,
								uaa:{uaaTipo: {codigo: null}, codigo: null}
							}
						}

						listarSedes($http, $scope);
						listarNbc($http, $scope);
						listarTipoUaa($http, $scope);
						listarNivelAcademico($http, $scope);
						listarJornada($http, $scope);
						listarConvenio($http, $scope);
						listarModalidad($http, $scope);
						listarEstados($http, $scope);

						$scope.hide = function() {
							$mdDialog.hide();
						};
						$scope.cancel = function() {
							if (accion == "Modificar") {
								$scope.programa.creacion = $filter('date')(
										$scope.programa.creacion, "yyyy-MM-dd");
							}
							$mdDialog.cancel();
						};
						$scope.answer = function(answer) {
							if (accion == "Adicionar") {
								programaResource
										.adicionar(
												{},
												$scope.programa,
												function(data) {
													$mdDialog.hide(answer);
													mostrarRespuesta(data);
												},
												function(response) {
													$scope.MsgError = response.data.mensaje;
													$scope.showHints = true;
												})
							}
							if (accion == "Modificar") {
								programaResource
										.modificar(
												{},
												$scope.programa,
												function(data) {
													$mdDialog.hide(answer);
													mostrarRespuesta(data);
												},
												function(response) {
													$scope.MsgError = response.data.mensaje;
													$scope.showHints = true;
												})
							}
						};
						$scope.obtenerUaa = function() {
							$scope.programa.uaa.codigo = null;
							listarUaa($http, $scope, $scope.programa.uaa.uaaTipo.codigo, 0);
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
						$scope.programa = table.row('.selected').data();
						if ($scope.programa == undefined) {
							data = {
								mensaje : "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
						var confirm = $mdDialog
								.confirm()
								.title('Quieres eliminar este registro ('+ $scope.programa.uaa.nombre +')?')
								.textContent(
										'Una vez eliminado no se podra recuperar el registro.')
								.ariaLabel('Asignatura').targetEvent(ev).ok(
										'Eliminar!').cancel('Cancelar');
						$mdDialog.show(confirm).then(function() {
							eliminar();
						}, function() {

						});
					};

					$scope.editar = function(accion) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
								&& $scope.customFullscreen;

						if (accion == "Adicionar") {
							$scope.programa = {};
						} else {
							if (accion != "Adicionar") {
								$scope.programa = table.row('.selected').data();
								if ($scope.programa == undefined) {
									data = {
										mensaje : "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}
								if ($scope.programa.creacion != null) {
									var fechaForma = $filter('date')(
											$scope.programa.creacion,
											"M/d/yyyy");
									$scope.programa.creacion = new Date(
											fechaForma);
								}
							}
						}
						var formulario = "forma.programa.jsp";
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'programa/' + formulario,
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.programa,
								accion : accion,
								maxDate : new Date()
							}
						}).then(function(answer) {

						});
					};
				});
