angular.module('asignatura.controller')
		.controller(
				'AsignaturaController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {					

					var table;
					var aplicacionTitulo = "Gestión Asignatura";
					
					$scope.showHints = false;
					table = $('#tblAsignaturas').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax": {
							"url": "asignaturas/lista",
							"type": "POST"
						},
						"columns" : [ {
							"data" : "codigo"
						}, {
							"data" : "nombre"
						}, {
							"data" : "nombreCorto",
						}, {
							"data" : "nombreImpresion"
						}, {
							"data" : "uaa.nombre"
						}, {
							"data" : "nbc.nombre"
						}, {
							"data" : "caracter.nombre"
						}, {
							"data" : "nucleo.nombre"
						}, {
							"data" : "trabajoPresencial"
						}, {
							"data" : "trabajoIndependiente"
						}, {
							"data" : "estado",
							"visible":false
						}, {
							"data" : "publicar",
							"visible":false
						}, {
							"data" : "uaaTipo.nombre",
							"visible":false
						},{
							"data" : "uaa.codigo",
							"visible":false
						},{
							"data" : "creditos"
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

					$('#tblAsignaturas tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});
					
					
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'asignaturas/';
					var asignaturaResource = $resource(url, {
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
					function listarCaracteres($http, $scope) {
						$http.get('caracteresLista').success(function(data) {
							$scope.JSONCaracteres = data;
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
					function listarNucleo($http, $scope) {
						$http.get('nucleoLista').success(function(data) {
							$scope.JSONNucleo = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarComponentes($http, $scope) {
						$http.get('componenteLista').success(function(data) {
							$scope.JSONComponente = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarUaa($http, $scope, tipo, uaaMod) {
						$http.get('uaaAll?tipoUaa=' + tipo + '&uaaMod=' + uaaMod).success(
								function(data) {
									$scope.JSONUaa = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function eliminar(){
						asignaturaResource.eliminar({},
								$scope.asignatura,
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
						$scope.asignatura = items;
						
						if ($scope.accion == 'Adicionar') {
							$scope.asignatura = {
									"nombreCorto":null,
									"nombre":null,
									"nombreImpresion":null,
									"uaaTipo":{"codigo":null},
									"uaa":{"codigo":null},
									"caracter":{"codigo":null},
									"nbc":{"codigo":null},
									"componente":{"codigoComponente":null},
									"nucleo":{"codigo":null},
									"creditos":null,
									"creditosTeoricos":null,
									"creditosPractico":null,
									"trabajoPresencial":null,
									"trabajoIndependiente":null,
									"publicar":null,
									"estado":null
										};
						} 
						listarTipoUaa($http, $scope);
						listarCaracteres($http, $scope);
						listarNbc($http, $scope);
						listarNucleo($http, $scope);
						listarComponentes($http, $scope);
						
						if ($scope.accion != 'Adicionar') {
							$scope.seleccionado = '('+$scope.asignatura.codigo+' '+$scope.asignatura.nombre+')';
							listarUaa($http, $scope, $scope.asignatura.uaaTipo.codigo, $scope.asignatura.uaa.codigo);
						}
						
						
						$scope.hide = function() {
							$mdDialog.hide();
						};
						$scope.cancel = function() {
							$mdDialog.cancel();
						};
						$scope.answer = function(answer) {
							if ($scope.accion == 'Adicionar') {
								asignaturaResource.adicionar({}, $scope.asignatura,
										function(data) {
											$mdDialog.hide(answer);
											mostrarRespuesta(data);
										}, function(response) {
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true;
										})
							}
							if ($scope.accion == "Modificar") {
								asignaturaResource.modificar({},
										$scope.asignatura,
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
							$scope.asignatura.uaa.codigo = null;
							listarUaa($http, $scope,$scope.asignatura.uaaTipo.codigo, 0);
						}
						$scope.acronimo = function() {
							if ($scope.asignatura.componente.codigoComponente) {
								$http.get('componenteLista/'+ $scope.asignatura.componente.codigoComponente)
								.success(function(dato) {
									$scope.asignatura.nombreCorto = dato[0].acronimoComponente;
									
									if ($scope.asignatura.nucleo.codigo) {
										$http.get('nucleoLista/'+ $scope.asignatura.nucleo.codigo)
										.success(function(datoAcr) {
											$scope.asignatura.nombreCorto += datoAcr[0].acronimo;
											
											if ($scope.asignatura.uaa.codigo) {												
												$http.get('uaaAll/'+ $scope.asignatura.uaa.codigo)
												.success(function(dataUaa) {
													$scope.asignatura.nombreCorto += dataUaa.acronimo;
													
													$http.get('uaaAll/'+ dataUaa.dependencia)
													.success(function(dataDep) {
														if(dataDep.acronimo != null && dataDep.acronimo != ''){
															$scope.asignatura.nombreCorto += dataDep.acronimo;
														}else{
															$scope.asignatura.nombreCorto += dataUaa.acronimo;
														}	
															$http.get('asignaturaCodAcronimo?acronimo='+ $scope.asignatura.nombreCorto)
															.success(function(dataAcronimo) {
																
																var CodigoAcronimo = dataAcronimo.nombreCorto;
																
																var NumeroAcronimo =  "";

																if (CodigoAcronimo != null && CodigoAcronimo != "") {
																	console.log(CodigoAcronimo);
																	var NumAcronimo = parseInt(CodigoAcronimo.substring(6));
																	console.log(NumAcronimo);
																	if (NumAcronimo < 9) {
																		NumAcronimo = NumAcronimo + 1;
																		NumeroAcronimo = "0" + NumAcronimo;
																		console.log(NumeroAcronimo);
																	} else {
																		NumAcronimo = NumAcronimo + 1;
																		NumeroAcronimo = NumAcronimo;
																	}
																} else {
																	NumeroAcronimo = "01";
																}
																$scope.asignatura.nombreCorto += NumeroAcronimo;																
															});
													});
												});
											}
												
										});
									}
								})
							}
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
						$scope.asignatura = table.row('.selected').data();
						if ($scope.asignatura == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
					    var confirm = $mdDialog.confirm()
					          .title('Quieres eliminar este registro ('+$scope.asignatura.codigo+' '+$scope.asignatura.nombre+') ?')
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
							$scope.asignatura = {};
						} else {
							if (accion != "Adicionar") {
								$scope.asignatura = table.row('.selected').data();
								if ($scope.asignatura == undefined) {
									data={
											mensaje: "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}
							}
						}
						if (accion == "Adicionar" || accion == 'Modificar') {
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'asignatura/forma.asignatura.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : $scope.asignatura,
								accion : accion
							}
						}).then(
								function(answer) {
									
								});
						}
					};
				});
