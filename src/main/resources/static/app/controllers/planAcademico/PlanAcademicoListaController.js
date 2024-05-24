angular
		.module('planAcademicoLista.controller')
		.controller(
				'PlanAcademicoListaController',
				function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {

					var table;
					var aplicacionTitulo = "Gestión Plan Académico";
					
					var self = this;
					self.msgLista = false;
					self.showHints = false;
					self.asignatura = {};
					table = $('#tblPlanAcademico').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax": {
							"url": "plan-academico/lista",
							"type": "POST"
						},						
						"columns" : [ {
							"data" : "codigo"
						}, {
							"data" : "nombre"
						}, {
							"data" : "programa.uaa.nombre"
						}, {
							"data" : "totalHoras"
						}, {
							"data" : "estado",
							"visible":false
						}, {
							"data" : "fecha"
						}, {
							"data" : "resolucion.numero"
						}, {
							"data" : "planActual"
						} , {
							"data" : "totalCreditos"
						} , {
							"data" : "programa.titulo_otorgado"
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
						                {"render": function ( data, type, row ) {
					                        if(data==1){
					                        	return 'Activo';
					                        }else{
					                        	return 'Inactivo';
					                        }
					                    },
					                    "targets": 7,
						                },
						              ]
					});
					
					var url = 'plan-academico/';
					var planAcademicoResource = $resource(url, {
						codigo : '@codigo'
					}, {
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
					
					var urlAsignatura = 'planAcademicoAsignaturaSer/';
					var planAcademicoAsignaturaResource = $resource(urlAsignatura, {
						codigo : '@codigo'
					}, {
						adicionar : {
							url : urlAsignatura,
							method : 'POST',
							params : {}
						},
						eliminar : {
							url : urlAsignatura + '/:codigo',
							method : 'DELETE',
							params : {}
						}
					});
					
					function eliminar() {
						planAcademicoResource.eliminar({},
								self.planAcademico, function(data) {
									$mdDialog.hide();
									mostrarRespuestaMod(data);
								}, function(response) {
									$mdDialog.hide();
									mostrarRespuestaMod(response.data);
								})
					}
					
					function listarSedes($http, self) {
						$http.get('sedesAll?estado=1').success(function(data) {
							self.JSONSedes = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function componentes(self, $http) {
						$http.get('componenteLista').success(function(data) {
							self.listaComponentes = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarProgramas($http, self, sede, proMod) {
						$http.get('programasLista/' + sede + '/'+proMod).success(
								function(data) {
									self.JSONProgramas = data;
								}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					function listarResolucion($http, self, resMod) {
						$http.get('resolucionLista/'+resMod).success(function(data) {
							self.JSONResolucion = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					$('#tblPlanAcademico tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});

					self.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					function mostrarAsignaturas($http, codigo, self) {
						$http.get('planAcademicoAsignaturasLista?codigo='+codigo).success(
								function(data) {
								self.JSONListaAsignaturas = data;
								if (data.length == 0) {
									self.msgLista = true;
								}
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function mostrarRespuestaMod(data) {
						table.ajax.reload();
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo).textContent(
										data.mensaje).ariaLabel(
										aplicacionTitulo).ok('Aceptar'));
					}
					
					function DialogController($scope, $http, $mdDialog, items, accion, $timeout, $q) {
						var self = this;
						self.planAcademico = {};
						self.planAcademico = items;
						self.accion = accion;
						self.seleccionado = ' ('+self.planAcademico.nombre+')';
						
						if (accion == "Agregar") {
							console.log(self.planAcademico);
							componentes(self, $http);
							self.items = [];
							self.selectedItemChange = selectedItemChange;
							self.searchTextChange = searchTextChange;
							self.agregarAsignatura = agregarAsignatura;
							self.searchTextChange("");
							self.finish = function($event) {
								$mdDialog.hide();
							};

							function searchTextChange(text) {
								$http.get('asignaturasLista?criterio=' + text).success(
										function(data) {
											self.items = data.map(function(asig) {
												return {
													value : asig,
													display : asig.codigo + " " + asig.nombre
												};
											});
										}).error(function(data) {
									console.log('Error: ' + data);
								});
							}
							
							function selectedItemChange(item) {
								self.asignatura = item.value;
								$http.get("datosPlanAcademicoAsignatura?plan="+self.planAcademico.codigo+"&"+"asignatura="+self.asignatura.codigo).success(function(data) {
									self.asignatura.notaMinima = data.notaMinima;
									self.asignatura.creditos = data.creditos;
									self.asignatura.intensidad = data.intensidad;
									self.asignatura.semestre = data.semestre;
									self.asignatura.pertenece = data.pertenece; 
									self.asignatura.programable = data.programable;
									self.asignatura.requisito = data.requisito; 
									self.asignatura.trabajoIndependiente = data.trabajoIndependiente; 
									self.asignatura.electiva = data.electiva; 
									self.asignatura.activo = data.activo;
									self.asignatura.componente = data.componente;
								}).error(function(data) {
									console.log('Error: ' + data);
								});

							}
							function agregarAsignatura(items) {
								self.asignatura.planAcademico = self.planAcademico; 
								console.log(self.asignatura);
								planAcademicoAsignaturaResource.adicionar({},self.asignatura,
										function(data) {
											$mdDialog.hide();
											mostrarRespuesta(data);
										},
										function(response) {
											self.MsgError = response.data.mensaje;
											self.showHints = true;
										})
							}
							
							function mostrarRespuesta(data) {
								table.ajax.reload();
								$mdDialog.show($mdDialog.alert()
										.title(aplicacionTitulo).textContent(
												data.mensaje).ariaLabel(
												aplicacionTitulo).ok('Aceptar'));
							}
						}
						
						self.hide = function() {
							$mdDialog.hide();
						};
						self.cancel = function() {
							if (accion == "Modificar") {
								self.planAcademico.fecha = $filter('date')(self.planAcademico.fecha,"yyyy-MM-dd");
							}
							$mdDialog.cancel();
						};
						self.answer = function(answer) {
							$mdDialog.hide(answer);
						};
						self.eliminarAsignatura = function(index) {
							//alert("hi");
							console.log("hi");
							// self.planAcademico.asignaturas.splice(index, 1);
						};
						if (accion == "Modificar") {
							listarSedes($http, self);
							listarResolucion($http, self, self.planAcademico.resolucion.codigo);
							listarProgramas($http, self, self.planAcademico.programa.sede.codigo, self.planAcademico.programa.codigo);
						}
						self.obtenerProgramas = function() {
							self.planAcademico.programa.codigo = null;
							listarProgramas($http, self, self.planAcademico.programa.sede.codigo, 0);
						}
						mostrarAsignaturas($http, self.planAcademico.codigo,self);
						
						self.eliminarPlanAsignatura = function(event){
							self.planAcademicoAsignatura = {
									codigo:event.target.id
							} 
							planAcademicoAsignaturaResource.eliminar({},self.planAcademicoAsignatura,
									function(data) {
										$mdDialog.hide();
										mostrarRespuestaMod(data);
									},
									function(response) {
										self.MsgError = response.data.mensaje;
										self.showHints = true;
									})
						}
						
						self.answer = function(answer) {
							if (accion == "Modificar") {
								planAcademicoResource.modificar({},self.planAcademico,
									function(data) {
										$mdDialog.hide(answer);
										mostrarRespuestaMod(data);
									},
									function(response) {
										self.MsgError = response.data.mensaje;
										self.showHints = true;
									})
							}
						};
					}

					self.verAsignaturas = function() {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))&& self.customFullscreen;

						self.planAcademico = table.row('.selected').data();
						if (self.planAcademico == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuestaMod(data);
							return false;
						}
						$mdDialog
								.show({
									controller : DialogController,
									controllerAs : 'ctrl',
									templateUrl : 'planAcademico/listaAsignaturasPlanAcademico.jsp',
									parent : angular.element(document.body),
									fullscreen : useFullScreen,
									locals : {
										items : self.planAcademico,
										accion:""
									}
								});
					};
					
					self.dialogEliminar = function(ev) {

						self.planAcademico = table.row('.selected').data();
						
						if (self.planAcademico == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuestaMod(data);
							return false;
						}
						var confirm = $mdDialog
								.confirm()
								.title('Quieres eliminar este registro ('+self.planAcademico.nombre+')?')
								.textContent(
										'Una vez eliminado no se podra recuperar el registro.')
								.ariaLabel('Asignatura').targetEvent(ev).ok(
										'Eliminar!').cancel('Cancelar');
						$mdDialog.show(confirm).then(function() {
							eliminar();
						}, function() {

						});
					};
					
					self.editar = function(accion) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && self.customFullscreen;
						self.planAcademico = table.row('.selected').data();

						if (self.planAcademico == undefined) {
							data={
									mensaje: "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuestaMod(data);
							return false;
						}
						
						var frm = "";
						if (accion == "Modificar") {
							if (self.planAcademico.fecha != null) {
								var fechaForma = $filter('date')(self.planAcademico.fecha,"M/d/yyyy");
								self.planAcademico.fecha = new Date(fechaForma);
							}
							
							frm = "editPlanAcademico";
						}else{
							frm = "materias";
						}
						$mdDialog.show({
							controller : DialogController,
							controllerAs : 'ctrl',
							templateUrl : 'planAcademico/forma.'+frm+'.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : self.planAcademico,
								accion : accion
							}
						}).then(function(answer) {
						});
					};
					
				});
