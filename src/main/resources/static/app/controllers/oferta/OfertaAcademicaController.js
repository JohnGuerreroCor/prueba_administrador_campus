angular
		.module('ofertaAcademica.controller')
		.controller(
				'OfertaAcademicaController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter, FileUpload) {

					var table;
					var aplicacionTitulo = "Gestión Oferta Académica";
					
					$scope.showHints = false;
					$scope.msgLista = false;
					$scope.ofertaAcademica = {};
					$scope.ofertaAcademica.fechaInicio = new Date();
					listarUaa($http, $scope);
					
					table = $('#tblOfertaAcademica').DataTable();
					//					
					
				
				$scope.cargarOferta = function (codigo){
				
					table.destroy();
					table = $('#tblOfertaAcademica').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax": {
							"url": "ofertaAcademicaSer/lista?facultad="+codigo,
							"type": "POST"
						},
						"columns" : [ {
							"data" : "codigo"
						}, {
							"data" : "programa.titulo_otorgado"
						}, {
							"data" : "imagen"
						}, {
							"data" : "calendario.nombre"
						}, {
							"data" : "fechaInicio"
						}, {
							"data" : "fechaFin"
						}, {
							"data" : "inscripcionFechaInicio"
						}, {
							"data" : "inscripcionFechaFin"
						}, {
							"data" : "cantidadPreInscritos"
						},{
							"data" : "cantidadInscritos"
						}, {
							"data" : "tipoAdmision"
						}, {
							"data" : "pago"
						}, {
							"data" : "programa.sede.codigo",
							"visible" : false
						}, {
							"data" : "crearUsuario",
						}, {
							"data" : "imagen",
							"visible":false
						}, {
							"data" : "requiereCupo"
						}, {
							"data" : "cupoMax"
						}, {
							"data" : "valor"
						}, {
							"data" : "programa.uaa.nombre",
						}, {
							"data" : "ofertaAcademicaEstado.nombre",
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
		                        "sPrevious": "Anterior"}
						},
						"columnDefs": [
										{
											"render": function ( data, type, row ) {
										    	return "<button>Ver</button>"
											},
											"targets": 2,
										},
						                {"render": function ( data, type, row ) {
					                        return $filter('date')(data,"yyyy-MM-dd HH:mm:ss");
					                    },
					                    "targets": 4,
						                },
						                {"render": function ( data, type, row ) {
					                        return $filter('date')(data,"yyyy-MM-dd HH:mm:ss");
					                    },
					                    "targets": 5,
						                },
						                {"render": function ( data, type, row ) {
					                        return $filter('date')(data,"yyyy-MM-dd HH:mm:ss");
					                    },
					                    "targets": 6,
						                },
						                {"render": function ( data, type, row ) {
					                        return $filter('date')(data,"yyyy-MM-dd HH:mm:ss");
					                    },
					                    "targets": 7,
						                },
						                {"render": function ( data, type, row ) {
					                        if(data==1){
					                        	return 'Manual';
					                        }else{
					                        	return 'Automática';
					                        }
					                    },
					                    "targets": 10,
						                },
						                {
											"targets": 8,
											"orderable": false
										},
						                {
											"targets": 9,
											"orderable": false
										},
						                {"render": function ( data, type, row ) {
					                        if(data==1){
					                        	return 'Si';
					                        }else{
					                        	return 'No';
					                        }
					                    },
					                    "targets": 11,
						                },
						                {"render": function ( data, type, row ) {
					                        if(data==1){
					                        	return 'Si';
					                        }else{
					                        	return 'No';
					                        }
					                    },
					                    "targets": 13,
						                },
						                {"render": function ( data, type, row ) {
					                        if(data==1){
					                        	return 'Si';
					                        }else{
					                        	return 'No';
					                        }
					                    },
					                    "targets": 15,
						                },
						                {"render": function ( data, type, row ) {
					                        return $filter('currency')(data, '$');
					                    },
					                    "targets": 17,
						                }
						                
						            ]
						});
					}
				
				$scope.cargarOferta(0);
				
					$('#tblOfertaAcademica tbody').on( 'click', 'button', function () {
						var data = table.row($(this).parents('tr')).data();
				        var contenido = null;
				        if(data.imagen){
				        	contenido = '<img src="ofertaAcademica/imagen?id='+data.codigo+'" style="width:50%;heidth:50%;">';
				        }else{
				        	contenido = '<md-subheader class="md-warn">No se ha cargado ninguna imagen.</md-subheader>';
				        }
				        var templete = '<md-dialog aria-label="Imagen Oferta">'				        	  
			        	    +'<h1 class="template-title">Imagen Oferta</h1>'
			        	    +'<md-dialog-content style="text-align: center;max-width:800px;max-height:810px; ">'
			        	    + contenido
				        	+'</md-dialog-content>'
				        	+'<md-dialog-actions layout="row">'
			        	    +'<md-button class="md-primary md-raised" data-ng-click="hide()"> Cerrar </md-button>'
			        	    +'</md-dialog-actions>'
			        	    +'</md-dialog>';
				        
				    	$mdDialog.show({
				    		controller : DialogControllerImg,
							template : templete, 
							parent : angular.element(document.body),
							fullscreen : ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen,
							locals : {}
						}).then(function(answer) {
						});
				    } );
					
					$('#tblOfertaAcademica tbody').on(
							'click',
							'tr',
							function() {
								if ($(this).hasClass('selected')) {
									$(this).removeClass('selected');
								} else {
									table.$('tr.selected').removeClass(
											'selected');
									$(this).addClass('selected');
								}
							});

					$scope.status = '  ';
					$scope.myData = null;
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					
					function listarSedes($http, $scope) {
						$http.get('sedesAll?estado=1').success(function(data) {
							$scope.JSONSedes = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarEstados($http, $scope) {
						$http.get('ofertaAcademicaEstadoLista').success(function(data) {
							$scope.JSONEstadosOferta = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}

					function listarUaa($http, $scope) {
						$http.get('uaaDep?tipoUaa=1&uaaMod=0').success(function(data) {
							$scope.JSONUaa = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}

					function listarCalendarios($http, $scope) {
						$http.get('listarCalendarios').success(function(data) {
							$scope.JSONCalendarios = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarProgramas($http, $scope, proMod) {
						$http.get('programasLista/'+ $scope.ofertaAcademica.programa.sede.codigo+"/"+proMod)
								.success(function(data) {
									$scope.JSONProgramas = data;
								}).error(function(data) {
									console.log('Error: ' + data);
								});
					}

					function listarRequisitoTipo($http, $scope) {
						$http.get('programaRequisitoTipoLista').success(
								function(data) {
									$scope.JSONRequisitoTipo = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarRequisitoOferta($http, $scope, id) {
						$http.get('ofertaRequisitosLista/' + id).success(
								function(data) {
									$scope.JSONRequisitoOferta = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listaOfertaInformacion($http, codigo, $scope) {
						$http.get('ofertaInformacionSer/'+codigo).success(
								function(data) {
									$scope.JSONOfertaInformacion = data;
									if (data.length == 0) {
										$scope.msgLista = true;
									}
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listaOfertaConfiguracion($http, codigo, $scope) {
						$http.get('ofertaConfiguracionSer/'+codigo).success(
								function(data) {
									$scope.JSONOfertaConfiguracion = data;
									if (data.length == 0) {
										$scope.msgListaConfig = true;
									}
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
						$http.get('uaaDep?tipoUaa=' + tipo + '&uaaMod=0').success(function(data) {
							$scope.JSONUaa = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function adicinarOfertaRouter(ofertaAcademica, answer, imagen, $scope){
						$scope.ofertaAcademica = ofertaAcademica;
						$scope.imagen = imagen;
						if ($scope.ofertaAcademica.fechaInicio != null) {
							var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
							$scope.ofertaAcademica.fechaInicio = new Date(fechaInicioForma);
						}
						if ($scope.ofertaAcademica.fechaFin != null) {
							var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
							$scope.ofertaAcademica.fechaFin = new Date(fechaFinForma);
						}

						if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
							var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
							$scope.ofertaAcademica.inscripcionFechaInicio = new Date(inscripcionFechaInicioForma);
						}

						if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
							var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
							$scope.ofertaAcademica.inscripcionFechaFin = new Date(inscripcionFechaFinForma);
						}
						ofertaAcademicaResource.adicionar({},$scope.ofertaAcademica,
								function(data) {
									if($scope.imagen){
										var fd = new FormData();
										fd.append('file', $scope.imagen);
										$http.post('ofertaAcademicaSerImagen/'+data.mensaje+'/false', fd, {
											headers : {
												'Content-Type' : undefined
											},
											transformRequest : angular.identity
										}).then(function successCallback(response) {
											file = null;
											
											$mdDialog.hide(answer);
											mostrarRespuesta(response.data);
											
										}, function errorCallback(response) {
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true;
											return false;
										});										
									}else{
										$mdDialog.hide(answer);
										data.mensaje = "Operación Ejecutada exitosamente.";
										mostrarRespuesta(data);
									}
								},
								function(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
									$scope.ofertaAcademica.fechaInicio = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
								})
					}

					var url = 'ofertaAcademicaSer/';
					var ofertaAcademicaResource = $resource(url, {
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

					var urlReq = 'ofertaRequisito/';
					var ofertaRequisitosResource = $resource(urlReq, {
						codigo : '@codigo'
					}, {
						adicionar : {
							url : urlReq,
							method : 'POST',
							params : {}
						},
						eliminar : {
							url : urlReq + ':codigo',
							method : 'DELETE',
							params : {}
						}
					});
					
					var urlInfo = 'ofertaInformacionSer/';
					var ofertaInformacionResource = $resource(urlInfo, {
						codigo : '@codigo'
					}, {
						adicionar : {
							url : urlInfo,
							method : 'POST',
							params : {}
						},
						eliminar : {
							url : urlInfo + ':codigo',
							method : 'DELETE',
							params : {}
						}
					});
					
					var urlConfig = 'ofertaConfiguracionSer/';
					var ofertaConfiguracionResource = $resource(urlConfig, {
						codigo : '@codigo'
					}, {
						adicionar : {
							url : urlConfig,
							method : 'POST',
							params : {}
						}
					});

					
					function eliminar() {
						ofertaAcademicaResource.eliminar({},
								$scope.ofertaAcademica, function(data) {
									$mdDialog.hide();
									mostrarRespuesta(data);
								}, function(response) {
									$mdDialog.hide();
									mostrarRespuesta(response.data);
								})
					}
					function DialogControllerImg($scope, $http, $mdDialog) {
						$scope.hide = function() {
							$mdDialog.hide();
						};
					}

					function DialogController($scope, $http, $mdDialog, accion, items, minDate) {
						
						$scope.accion = accion;
						$scope.ofertaAcademica = items;
						$scope.minDate = minDate;
						
						if (accion == "Modificar") {
							$scope.minDate = null;
							listarProgramas($http, $scope, $scope.ofertaAcademica.programa.codigo);
							listarCalendarios($http, $scope);
						}
						if (accion == "Requisitos") {
							listarRequisitoTipo($http, $scope)
							listarRequisitoOferta($http, $scope, $scope.ofertaAcademica.codigo);
						}
						
						if (accion == "ListaInformacion") {
							listaOfertaInformacion($http, $scope.ofertaAcademica.codigo, $scope);
						}
						
						if (accion == "Configurar") {
							listarTipoUaaFac($http, $scope);
							listaOfertaConfiguracion($http, $scope.ofertaAcademica.codigo, $scope);
						}
						
						
						$scope.obtenerUaa = function() {
							$scope.uaa.dependencia = null;
							listarUaa($http, $scope, $scope.uaa.codigoTipoUaaDep, 0);
						}
						
						
						if (accion == "Informacion") {
							 $scope.tinymceModel = 'Initial content';

							  $scope.getContent = function() {
							    //console.log('Editor content:', $scope.tinymceModel);
							  };

							  $scope.setContent = function() {
							    $scope.tinymceModel = 'Time: ' + (new Date());
							  };

							  $scope.tinymceOptions = {
								plugins: 'paste link',
							    toolbar1: 'undo redo | styleselect | alignjustify | bullist numlist outdent indent | code',
							    paste_as_text: true,
							    paste_enable_default_filters: false,
							    /*paste_word_valid_elements: "b,strong,i,p,h1,h2",
							    paste_merge_formats: false,
							    paste_convert_word_fake_lists: false,*/
							    style_formats: [
				                    { title: 'Titulo', block: 'h2', styles: { color: '#2f2f2f', 'font-size':'14px', font:"normal 92% 'Roboto Condensed', Arial, Helvetica, sans-serif", 'text-align': 'justify' } },
				                    { title: 'Parrafo', block: 'p', styles: { color: '#333', 'font-size':'14px', font:"normal 92% 'Roboto Condensed', Arial, Helvetica, sans-serif", 'text-align': 'justify' } },
				                  ]
							  };
						}
						if ($scope.accion == 'Adicionar') {
							listarCalendarios($http, $scope);
							$scope.ofertaAcademica = {
								"programa" : {
									"codigo" : null
								},
								"tipoAdmision" : null,
								"pago" : 0,
								"fechaInicio" : null,
								"fechaFin" : null,
								"inscripcionFechaInicio" : null,
								"inscripcionFechaFin" : null,
								"requiereCupo":0,
								"cupoMax" : null,
								"valor": null
							};
						}else{
							$scope.seleccionado = ' ('+$scope.ofertaAcademica.programa.titulo_otorgado+')';
						}
						
						listarSedes($http, $scope);
						listarEstados($http, $scope);
						$scope.hide = function() {
							$mdDialog.hide();
						};
						$scope.cancel = function() {
							if (accion == "Modificar") {
								$scope.ofertaAcademica.fechaInicio = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
								$scope.ofertaAcademica.fechaFin = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
								$scope.ofertaAcademica.inscripcionFechaInicio = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
								$scope.ofertaAcademica.inscripcionFechaFin = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
							}
							$mdDialog.cancel();
						};
						
						//$scope.ofertaAcademica.valor = $filter('currency')($scope.ofertaAcademica.valor);
						
						$scope.obtenerProgramas = function() {
							$scope.ofertaAcademica.programa.codigo = null;
							listarProgramas($http, $scope, 0);
						}
						
						$scope.eliminarRequisito = function(event){
							$scope.ofertaRequisito = {
									codigo: event.target.id
							}
							ofertaRequisitosResource.eliminar({},$scope.ofertaRequisito,
									function(data) { 
										$mdDialog.hide();
										mostrarRespuesta(data); 
										}, 
									function(response) {
										$scope.MsgError = response.data.mensaje;
										$scope.showHints = true; 
										})
						}

						$scope.eliminarInformacion = function(event){
							$scope.ofertaInformacion = {
									codigo: event.target.id
							}
							ofertaInformacionResource.eliminar({},$scope.ofertaInformacion,
									function(data) { 
										$mdDialog.hide();
										mostrarRespuesta(data); 
										}, 
									function(response) {
										$scope.MsgError = response.data.mensaje;
										$scope.showHints = true; 
										})
						}

						
						$scope.answer = function(answer) {
							if (accion == "Adicionar") {	
								$scope.prueba = '';
								if($scope.imagen){
										var fd = new FormData();
										
										fd.append('file', $scope.imagen);
										$http.post('ofertaAcademicaSerImagen/0091/true', fd, {
											headers : {
												'Content-Type' : undefined
											},
											transformRequest : angular.identity
										}).then(function successCallback(response) {
											file = null;
											adicinarOfertaRouter($scope.ofertaAcademica,answer,$scope.imagen, $scope);
										}, function errorCallback(response) {
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true;
											return false;
										});
								}else{
									adicinarOfertaRouter($scope.ofertaAcademica,answer, $scope.imagen, $scope);
								}
							}
							if (accion == "Modificar") {
								if($scope.imagen){
									var name = $scope.imagen.name;
									var extn = name.split(".").pop();
									if(extn != 'jpg' && extn != 'jpeg' && extn != "png"){
										$scope.MsgError = "Archivo no valido, por favor seleccione otro.";
										$scope.showHints = true;
										return false;
									}
								}
								if ($scope.ofertaAcademica.fechaInicio != null) {
									var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaInicio = new Date(fechaInicioForma);
								}
								if ($scope.ofertaAcademica.fechaFin != null) {
									var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = new Date(fechaFinForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
									var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = new Date(inscripcionFechaInicioForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
									var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = new Date(inscripcionFechaFinForma);
								}
								ofertaAcademicaResource.modificar({},$scope.ofertaAcademica,
												function(data) {
													if($scope.imagen){
															var fd = new FormData();
															fd.append('file', $scope.imagen);
															$http.post('ofertaAcademicaSerImagen/'+$scope.ofertaAcademica.codigo+'/false', fd, {
																headers : {
																	'Content-Type' : undefined
																},
																transformRequest : angular.identity
															}).then(function successCallback(response) {
																file = null;
																
																$mdDialog.hide(answer);
																mostrarRespuesta(response.data);
																
															}, function errorCallback(response) {
																$scope.MsgError = response.data.mensaje;
																$scope.showHints = true;
																return false;
															});
															
													}else{
														$mdDialog.hide(answer);
														mostrarRespuesta(data);
													}
												},
												function(response) {
													$scope.MsgError = response.data.mensaje;
													$scope.showHints = true;
													$scope.ofertaAcademica.fechaInicio = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
													$scope.ofertaAcademica.fechaFin = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
													$scope.ofertaAcademica.inscripcionFechaInicio = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
													$scope.ofertaAcademica.inscripcionFechaFin = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
												})
							}
							if (accion == "Requisitos") {
								$scope.ofertaRequisito.ofertaAcademica = $scope.ofertaAcademica;
								if ($scope.ofertaAcademica.fechaInicio != null) {
									var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaInicio = new Date(fechaInicioForma);
								}
								if ($scope.ofertaAcademica.fechaFin != null) {
									var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = new Date(fechaFinForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
									var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = new Date(inscripcionFechaInicioForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
									var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = new Date(inscripcionFechaFinForma);
								}

								ofertaRequisitosResource.adicionar({},$scope.ofertaRequisito,
										function(data) { 
											$mdDialog.hide(answer);
											mostrarRespuesta(data); 
											}, 
										function(response) {
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true; 
											})
							}
							
							if (accion == "Informacion") {
								if ($scope.ofertaAcademica.fechaInicio != null) {
									var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaInicio = new Date(fechaInicioForma);
								}
								if ($scope.ofertaAcademica.fechaFin != null) {
									var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = new Date(fechaFinForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
									var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = new Date(inscripcionFechaInicioForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
									var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = new Date(inscripcionFechaFinForma);
								}

								$scope.ofertaInformacion.ofertaAcademica = $scope.ofertaAcademica;
								ofertaInformacionResource.adicionar({},$scope.ofertaInformacion,
										function(data) { 
											$mdDialog.hide(answer);
											mostrarRespuesta(data); 
											}, 
										function(response) {
											$scope.MsgError = response.data.mensaje;
											$scope.showHints = true; 
											})
							}
							
							
							if (accion == "Configurar") {
								if ($scope.ofertaAcademica.fechaInicio != null) {
									var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaInicio = new Date(fechaInicioForma);
								}
								if ($scope.ofertaAcademica.fechaFin != null) {
									var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = new Date(fechaFinForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
									var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = new Date(inscripcionFechaInicioForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
									var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = new Date(inscripcionFechaFinForma);
								}

								$scope.ofertaConfiguracion.ofertaAcademica = $scope.ofertaAcademica;
								$scope.ofertaConfiguracion.tipoUsuario = $scope.ofertaConfiguracion.tipoUsuario.toString();
								if($scope.ofertaConfiguracion.uaa){
									$scope.ofertaConfiguracion.uaa = $scope.ofertaConfiguracion.uaa.toString();
								}
								console.log($scope.ofertaConfiguracion);
								ofertaConfiguracionResource.adicionar({},$scope.ofertaConfiguracion,
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
					}

					function mostrarRespuesta(data) {
						table.ajax.reload();
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo).textContent(
										data.mensaje).ariaLabel(
										aplicacionTitulo).ok('Aceptar'));
					}
					
					$scope.dialogEliminar = function(ev) {
						$scope.ofertaAcademica = table.row('.selected').data();
						if ($scope.ofertaAcademica == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
						var confirm = $mdDialog
								.confirm()
								.title('Quieres eliminar este registro ('+$scope.ofertaAcademica.programa.titulo_otorgado+')?')
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
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;

						if (accion == "Adicionar") {
							$scope.ofertaAcademica = {};
						} else {
							if (accion != "Adicionar") {
								$scope.ofertaAcademica = table.row('.selected').data();
								if ($scope.ofertaAcademica == undefined) {
									data={
											mensaje: "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}
								
								if ($scope.ofertaAcademica.fechaInicio != null) {
									var fechaInicioForma = $filter('date')($scope.ofertaAcademica.fechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaInicio = fechaInicioForma;//new Date(fechaInicioForma);
								}
								if ($scope.ofertaAcademica.fechaFin != null) {
									var fechaFinForma = $filter('date')($scope.ofertaAcademica.fechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.fechaFin = fechaFinForma;//new Date(fechaFinForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaInicio != null) {
									var inscripcionFechaInicioForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaInicio,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaInicio = inscripcionFechaInicioForma;//new Date(inscripcionFechaInicioForma);
								}

								if ($scope.ofertaAcademica.inscripcionFechaFin != null) {
									var inscripcionFechaFinForma = $filter('date')($scope.ofertaAcademica.inscripcionFechaFin,"yyyy-MM-dd HH:mm:ss");
									$scope.ofertaAcademica.inscripcionFechaFin = inscripcionFechaFinForma;//new Date(inscripcionFechaFinForma);
								}

							}
						}
						
						var frm = "";
						if (accion == "Requisitos") {
							frm = "requisitos";
						} else {
							if (accion == "Informacion") {
								frm = "ofertaInformacion";
							}else{
								if (accion == "ListaInformacion") {
									frm = "listaInformacion";
								}else{
									if (accion == "Configurar") {
										frm = "configurar";
									}else{
										frm = "ofertaAcademica";
									}
								}
							}
						}
						
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'oferta/forma.' + frm + '.jsp',
							parent : angular.element(document.body),
							bindToController: true,
							fullscreen : useFullScreen,
							locals : {
								items : $scope.ofertaAcademica,
								accion : accion,
								minDate : new Date()
							}
						}).then(function(answer) {
						});
					};
				});
