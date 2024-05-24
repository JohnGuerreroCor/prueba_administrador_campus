angular
		.module('cursos.controller')
		.controller(
				'CursosController',
				function($http, $mdDialog, $mdMedia, $resource, $filter) {

					var table;
					var self = this;
					var aplicacionTitulo = "Cursos";
					self.showHints = false;
					self.cargarCursos = cargarCursos;
					table = $('#tblCurso').DataTable();
					
					function cargarCursos(codigo){
					table.destroy();
					table = $('#tblCurso').DataTable(
								{
									"processing" : true,
									"serverSide" : true,
									"ajax" : {
										"url" : "cursos/lista?calendario="+codigo,
										"type" : "POST"
									},
									"columns" : [
											{
												"data" : "codigo"
											}, {
												"data" : "asignatura.nombre"
											}, {
												"data" : "grupo"
											}, {
												"data" : "cupo"
											}, {
												"data" : "fechaInicio"
											}, {
												"data" : "uaaPersonal.persona.nombre"
											}, {
												"data" : "planAcademico.nombre"
											}, {
												"data" : "calendario.nombre"
											}, {
												"data" : "planAcademico.programa.uaa.nombre"
											}, {
												"data" : "asignatura.uaa.nombre"
											}, {
												"data" : "semanas"
											}, {
												"data" : "tipoCurso.nombre"
											}, {
												"data" : "tipoEscala.nombre"
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

									});
					}
					self.cargarCursos(0);
					
					$('#tblCurso tbody').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
						}
					});

					self.status = '  ';
					self.myData = null;
					self.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

					var url = 'cursoSer/';
					var cursoResource = $resource(url, {
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
					
					var url = 'espacioOcupacionVirtual/';
					var espacioOcupacionResource = $resource(url, {
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
					
					listarCalendarios($http, self);
					function listarSedes($http, self) {
						$http.get('sedesAll?estado=1').success(function(data) {
							self.JSONSedes = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}

					function listarTipoCurso($http, self) {
						$http.get('tipoCursoList').success(function(data) {
							console.log(data);
							self.JSONTipoCurso = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarCalendarios($http, self) {
						$http.get('listarCalendarios').success(function(data) {
							self.JSONCalendario = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarTipoEscala($http, self) {
						$http.get('tipoEscalaList').success(function(data) {
							self.JSONTipoEscala = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarDia($http, self) {
						$http.get('diaList').success(function(data) {
							data.splice(data.length-1, 1);
							self.JSONDia = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function listarEspacioOcupacion($http, self, curso) {
						$http.get('espacioOcupacionVirtualList?curso='+curso).success(function(data) {
							self.JSONEspacioOcupacion = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function eliminar() {
						cursoResource.eliminar({}, self.curso, function(data) {
							$mdDialog.hide();
							mostrarRespuesta(data);
						}, function(response) {
							$mdDialog.hide();
							mostrarRespuesta(response.data);
						})
					}

					function listarHora($http, self) {
						$http.get('horasSer').success(function(data) {
							self.JSONHora = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}
					
					function DialogController($http, $mdDialog, accion, items) {
						
						var self = this;
						self.disableHorario = true;
						self.accion = accion;
						self.curso = items;
						self.espacioOcupacion = {};
						self.espacioOcupacion.curso = self.curso ;
						
						self.dialogEliminarHorario = function(ev, id) {
							self.espacioOcupacion.codigo = id;
							console.log(self.espacioOcupacion);
						    var confirm = $mdDialog.confirm()
						          .title('Seguro de eliminar este registro?')
						          .textContent('')
						          .ariaLabel('')
						          .targetEvent(ev)
						          .ok('Eliminar!')
							      .cancel('Cancelar');
							    $mdDialog.show(confirm).then(function() {
							    	espacioOcupacionResource.eliminar({}, self.espacioOcupacion, function(
											data) {
										$mdDialog.hide();
										mostrarRespuesta(data);
									}, function(response) {
										alert(response.data.mensaje);
									})
							    }, function() {
							      
							    });
						  };
						  
						self.rango = {
								dia: {},
								inicio:undefined,
								fin:undefined
						};
						
						self.rangos = [];
						
						self.eliminarRango = function(index){
							var inicio = 0;
							var fin = 0;
							var columna = self.rangos[index].inicio.columna;
							if(self.rangos[index].inicio.fila > self.rangos[index].fin.fila) {
								inicio = self.rangos[index].fin.fila;
								fin = self.rangos[index].inicio.fila;
							} else {
								fin = self.rangos[index].fin.fila;
								inicio = self.rangos[index].inicio.fila;
							}
							
							for(inicio; inicio <= fin; inicio++) {
								$('#'+columna+""+inicio).removeClass('seleccionado');
								self.JSONDiaConHorasOcupadas[columna-1].listaHoras[inicio].ocupado = false;
								if(inicio==fin){
									self.rangos.splice(index,1);
								}
							}					
						}
						
						self.seleccionar = seleccionar;
						
						function seleccionar(diaObj, horaObj, columna, fila, horaSgt) {
						
							var dia = diaObj.codigo;
							var hora = horaObj.codigo;
							
							if(self.rango.inicio == undefined) {
								self.rango.dia = dia;
								self.rango.diaText = diaObj.nombre;
								self.rango.inicio= {
										hora: hora,
										horaText: horaObj.hora,
										fila:fila,
										columna:columna
								};
								return true;
							} else {
								if(self.rango.dia == dia) {	
									self.rango.diaText = diaObj.nombre;
									self.rango.fin = {
											hora: hora,
											horaText: horaSgt,
											fila:fila,
											columna:columna
									};

									var inicio = 0;
									var fin = 0;
									var inicioV = 0;
									var finV = 0;
									
									if(self.rango.inicio.fila > self.rango.fin.fila) {
										inicio = self.rango.fin.fila;
										fin = self.rango.inicio.fila;
										inicioV = self.rango.fin.fila;
										finV = self.rango.inicio.fila;
									} else {
										fin = self.rango.fin.fila;
										inicio = self.rango.inicio.fila;
										finV = self.rango.fin.fila;
										inicioV = self.rango.inicio.fila;
									}
									
									for(inicio; inicio <= fin; inicio++) {
										if(self.JSONDiaConHorasOcupadas[columna-1].listaHoras[inicio].ocupado === true){
											alert("Rango no permitido.");
											$('#'+columna+self.rango.fin.fila).removeClass('seleccionado');
											$('#'+columna+self.rango.inicio.fila).removeClass('seleccionado');
											self.rango = {
													dia: {},
													diaText:{},
													inicio:undefined,
													fin:undefined
											};
											return false;
										}
									}
											
									for(inicioV; inicioV <= finV; inicioV++) {
										$('#'+columna+inicioV).addClass('seleccionado');
										self.JSONDiaConHorasOcupadas[columna-1].listaHoras[inicioV].ocupado = true;
									}
									
									self.rangos.push(self.rango);
									
									self.rango = {
											dia: {},
											diaText:{},
											inicio:undefined,
											fin:undefined
									};
									
									return true;
								} else {
									alert("Debe seleccionar la hora final dentro del mismo día");
									return false;
								}						
							}
						}
						
						listarHora($http, self);
						listarSedes($http, self);
						
						if (accion == "Horario") {
							cargarHorasCurso();
						}

						self.cargarHorasDocente = function(){
							if(self.espacioOcupacion.espacio==undefined){
								
								
								 
								var fecha = $filter('date')(self.espacioOcupacion.fecha, "dd-MM-yyyy");

								var fechaNew = new Date(fecha + (self.espacioOcupacion.curso.semanas * 7)*24*60*60*1000);
								 console.log("FECHA NEW:"+fechaNew);
								 
							    
								var date = new Date(fecha);
							    var newdate = new Date(date);

							    console.log((self.espacioOcupacion.curso.semanas * 7));
							    
							    newdate.setDate(newdate.getDate() + (self.espacioOcupacion.curso.semanas * 7));
							    
							    var dd = newdate.getDate();
							    var mm = newdate.getMonth() + 1;
							    var y = newdate.getFullYear();

							    var someFormattedDate = dd + '-' + mm + '-' + y;
							    console.log("La fecha es:"+fecha);
							    console.log("La fecha mas las semanas"+someFormattedDate);
							    
							    
								$http.get('diaHorasOcupadasSer?actividad=0&espacio=0&espacioTipo=0&dia=0&docente='+self.espacioOcupacion.curso.uaaPersonal.codigo+'&fecha='+fecha+'&semanas='+self.espacioOcupacion.curso.semanas).success(function(data) {
									self.JSONDiaConHorasOcupadas = data;
								}).error(function(data) {
									console.log('Error: ' + data);
								});
							}else{
								self.selectEspacio();
							}
						}
						
						function cargarHorasCurso(){
							$http.get('diaHorasOcupadasSer?actividad='+self.espacioOcupacion.curso.codigo+'&espacio=0&espacioTipo=0&dia=0&docente=0+&fecha=&semanas=0').success(function(data) {
								self.JSONHorasCurso = data;
							}).error(function(data) {
								console.log('Error: ' + data);
							});
						}
						
						self.hide = function() {
							$mdDialog.hide();
						};
						self.cancel = function() {
							$mdDialog.cancel();
						};

						if (self.accion == 'Modificar' || self.accion == 'Horario' || self.accion == 'listarHorario') {
							self.seleccionado = "("+self.curso.codigo+" - "+self.curso.asignatura.nombre+" - " + self.curso.grupo+")"
							self.selectedItemPlanAcademico = {
								value : self.curso.planAcademico,
								display : self.curso.planAcademico.nombre + " / " + self.curso.planAcademico.programa.uaa.nombre
							}
							self.selectedItemDoc = {
								value : self.curso.uaaPersonal,
								display : self.curso.uaaPersonal.persona.nombre
							}
							buscarAsignatura("",self.curso.planAcademico.codigo);

						}
						if(self.accion == 'Modificar' || self.accion == 'Adicionar'){
							listarTipoCurso($http, self);
							listarTipoEscala($http, self);
						}
						if(self.accion == 'Horario'){
							listarDia($http, self);
						}

						if(self.accion == 'Horario' || self.accion == 'listarHorario'){
							listarEspacioOcupacion($http, self, self.curso.codigo);							
						}
						
						self.selectEspacio = function(){							
							$http.get('diaHorasOcupadasSer?actividad=0&espacio=0&espacioTipo=0&dia=0&docente='+self.espacioOcupacion.curso.uaaPersonal.codigo+'&fecha='+$filter('date')(self.espacioOcupacion.fecha, "dd-MM-yyyy")+'&semanas='+self.espacioOcupacion.curso.semanas).success(function(data) {
								self.JSONDiaConHorasOcupadas = data;
							}).error(function(data) {
								console.log('Error: ' + data);
							});
							
							setTimeout(function() {
								$http.get('diaHorasOcupadasSer?actividad=0&espacio='+self.espacioOcupacion.espacio.codigo+"&espacioTipo="+self.espacioOcupacion.espacio.espacioTipo.codigo+'&dia=0&docente=0&fecha='+$filter('date')(self.espacioOcupacion.fecha, "dd-MM-yyyy")+'&semanas='+self.espacioOcupacion.curso.semanas).success(function(data) {
									for(var i = 0; i < self.JSONDiaConHorasOcupadas.length; i++){
										for(var j = 0; j < self.JSONDiaConHorasOcupadas[i].listaHoras.length; j++){
											if(self.JSONDiaConHorasOcupadas[i].listaHoras[j].ocupado === false && data[i].listaHoras[j].ocupado === true){
												self.JSONDiaConHorasOcupadas[i].listaHoras[j].ocupado = data[i].listaHoras[j].ocupado;
											}
										}
									}
								}).error(function(data) {
									console.log('Error: ' + data);
								});
							}, 300);
						}
						
						self.jsonDiasSeleccionados = [];
						self.funcionHora = function (horaInicio, dia, columna, fila, horaSgt){	
							if(self.disableHorario){
								alert("Primero seleccione un espacio.");
								return false;
							}
							if(!seleccionar(dia, horaInicio, columna + 1, fila, horaSgt))
								return;							
							$('#'+(columna+1)+fila).addClass('seleccionado');
						}
						
						self.itemsDoc = [];
						searchTextChangeDoc("");
						self.selectedItemChangeDoc = selectedItemChangeDoc;
						self.searchTextChangeDoc = searchTextChangeDoc;

						self.itemsPlanAcademico = [];
						searchTextChangePlanAcademico("");
						self.selectedItemChangePlanAcademico = selectedItemChangePlanAcademico;
						self.searchTextChangePlanAcademico = searchTextChangePlanAcademico;

						self.itemsEspacio = [];
						self.selectedItemChangeEspacio = selectedItemChangeEspacio;
						self.searchTextChangeEspacio = searchTextChangeEspacio;
						searchTextChangeEspacio("");
						
						
						self.answer = function(answer) {
							if (accion == "Adicionar") {
								cursoResource.adicionar({}, self.curso, function(
										data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									self.MsgError = response.data.mensaje;
									self.showHints = true;
								})
							}
							if (accion == "Modificar") {
								cursoResource.modificar({}, self.curso, function(
										data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									self.MsgError = response.data.mensaje;
									self.showHints = true;
								})
							}
							if (accion == "Horario") {
								
								self.espacioOcupacion.diasHorasOcupadasDTO = self.rangos;
								espacioOcupacionResource.adicionar({}, self.espacioOcupacion, function(
										data) {
									$mdDialog.hide(answer);
									mostrarRespuesta(data);
								}, function(response) {
									self.MsgError = response.data.mensaje;
									self.showHints = true;
								})
							}
						};
						function searchTextChangeDoc(text) {
							$http.get('docentesServ?criterio=' + text).success(
									function(data) {
										self.itemsDoc = data.map(function(docente) {
											return {
													value : docente,
													display : docente.persona.nombre+ " "+ docente.persona.apellido
													};
												});
											}).error(function(data) {
										console.log('Error: ' + data);
									});
						}

						function selectedItemChangeDoc(item) {
							if (item != undefined) {
								self.docente = item.value;
								self.espacioOcupacion.curso.uaaPersonal = self.docente;
								if (accion == "Horario") {
									self.cargarHorasDocente();
								}
								self.items = [];
							}
						}

						function buscarAsignatura(text, plan) {
							$http.get('planAcademicoAsignaturasLista?codigo=' + plan).success(function(data) {
								self.JSONAsignatura = data;
							}).error(function(data) {
								console.log('Error: ' + data);
							});
						}
						
						function searchTextChangePlanAcademico(text) {
							$http.get('planAcademicoSer?criterio=' + text).success(
									function(data) {
										self.itemsPlanAcademico = data.map(function(planAcademico) {
											return {
													value : planAcademico,
													display : planAcademico.nombre + " / " + planAcademico.programa.uaa.nombre
													};
												});
											}).error(function(data) {
										console.log('Error: ' + data);
									});
						}

						function selectedItemChangePlanAcademico(item) {
							if (item != undefined) {
								self.planAcademico = item.value;
								self.curso.planAcademico = self.planAcademico;
								
								self.items = [];
								buscarAsignatura("",self.curso.planAcademico.codigo);
							}
						}
						
						function searchTextChangeEspacio(text) {
							$http.get('espacioList?criterio=' + text).success(
							function(data) {
								self.itemsEspacio = data.map(function(espacio) {
								return {
										value : espacio,
										display : espacio.nombreCorto+ " - "+ espacio.nombre
										};
									});
							}).error(function(data) {
								console.log('Error: ' + data);
							});				
						}

						function selectedItemChangeEspacio(item) {
							if (item != undefined) {
								self.espacio = item.value;
								self.espacioOcupacion.espacio = self.espacio;
								self.selectEspacio();
								self.disableHorario = false;
							}
						}

					}
					
					function mostrarRespuesta(data) {
						table.ajax.reload();
						$mdDialog.show($mdDialog.alert()
								.title(aplicacionTitulo)
								.textContent(data.mensaje)
								.ariaLabel(aplicacionTitulo)
								.ok('Aceptar'));
					}
					self.dialogEliminar = function(ev) {

						self.curso = table.row('.selected').data();
						if (self.curso == undefined) {
							data = {
								mensaje : "Debe seleccionar un registro para realizar esta acción."
							}
							mostrarRespuesta(data);
							return false;
						}
						self.seleccionado = "("+self.curso.codigo+" - "+self.curso.asignatura.nombre+" - " + self.curso.grupo+")"
						
						var confirm = $mdDialog
								.confirm()
								.title('Quieres eliminar este registro?'+self.seleccionado)
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

						if (accion == "Adicionar") {
							self.curso = {};
						} else {
							if (accion != "Adicionar") {
								if (accion == "Horario" || accion == "listarHorario") {
									self.espacioOcupacion = {};
									self.espacioOcupacion.curso = table.row('.selected').data();
								}
								self.curso = table.row('.selected').data();
								if (self.curso == undefined) {
									data = {
										mensaje : "Debe seleccionar un registro para realizar esta acción."
									}
									mostrarRespuesta(data);
									return false;
								}
								if (self.curso.fechaInicio != null) {
									var fechaForma = $filter('date')(self.curso.fechaInicio, "M/d/yyyy");
									self.curso.fechaInicio = new Date(fechaForma);
								}
							}
						}
						var frm = "forma.curso";
						if(accion == "Horario"){
							frm = "forma.horario";	
						}
						if(accion == "listarHorario"){
							frm = "listar.horario";	
						}
						$mdDialog.show({
							controller : DialogController,
							controllerAs : 'ctrlFrm',
							templateUrl : 'prog-academica/cursos/'+frm +'.jsp',
							parent : angular.element(document.body),
							fullscreen : useFullScreen,
							locals : {
								items : self.curso,
								accion : accion
							}
						}).then(function(answer) {

						});
					};
				});
