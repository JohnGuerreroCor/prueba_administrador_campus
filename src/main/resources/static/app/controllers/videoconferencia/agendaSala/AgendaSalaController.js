angular
		.module('agendasala.controller')
		.controller(
				'AgendaSalaController',
				function($http, $mdDialog, $mdMedia, $resource, uiCalendarConfig, $filter, $route) {

					var self = this;
					var aplicacionTitulo = "Reserva de espacios videoconferencia";
					self.eventSources = [];
					

					self.eventSource = {
						url : "solicitudSer",
						color: '#DDD',   // an option!
					    textColor: '#534b4b',
					};
					
					self.alertOnEventClick = function(date, jsEvent, view) {
						self.solicitud = date;
						self.editar('Modificar', self.fInicio, date);
					};

					/* Change View */
					self.changeView = function(view, calendar) {
						// alert('change view');
						uiCalendarConfig.calendars[calendar].fullCalendar(
								'changeView', view);
						self.renderCalender;
					};

					/* Change View */
					self.renderCalender = function(calendar) {
						if (uiCalendarConfig.calendars[calendar]) {
							uiCalendarConfig.calendars[calendar]
									.fullCalendar('render');
						}
					};
					/* Render Tooltip */
					self.eventRender = function(event, element, view) {
						// alert('ole');
						element.attr({
							'tooltip' : event.title,
							'tooltip-append-to-body' : true
						});
						// $compile(element)(self);

					};
					/* Render Tooltip */
					self.alertOnClick = function(start, end) {
						self.fInicio = moment(start).format('YYYY-MM-DD');
						self.fFin = moment(end).format('YYYY-MM-DD');
						self.editar('Adicionar', self.fInicio);
					};

					self.uiConfig = {
						calendar : {
							editable : false,
							selectable : true,
							selectHelper : true,
							header : {
								left : 'prev,next today',
								center : 'title',
								right : 'month,basicWeek,basicDay'
							},
							eventColor: '#DDD',
							lang : 'es',
							eventLimit : true,
							eventClick : self.alertOnEventClick,
							select : self.alertOnClick,
							eventDrop : self.alertOnDrop,
							eventResize : self.alertOnResize,
							eventRender : self.eventRender,
						}
					};

					self.changeLang = function() {
						if (self.changeTo === 'Hungarian') {
							self.uiConfig.calendar.dayNames = [ "Vasárnap",
									"Hétfő", "Kedd", "Szerda", "Csütörtök",
									"Péntek", "Szombat" ];
							self.uiConfig.calendar.dayNamesShort = [ "Vas",
									"Hét", "Kedd", "Sze", "Csüt", "Pén", "Szo" ];
							self.changeTo = 'Español';
						} else {
							self.uiConfig.calendar.dayNames = [ "Sunday",
									"Monday", "Tuesday", "Wednesday",
									"Thursday", "Friday", "Saturday" ];
							self.uiConfig.calendar.dayNamesShort = [ "Sun",
									"Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ];
							self.changeTo = 'Hungarian';
						}
					};
					/* event sources array */

					self.eventSources = [ self.eventSource ];
					

					/** ***************************Formulario************************************** */

					var urlSer = 'solicitudSer/';
					var solicitudResource = $resource(urlSer, {
						codigo : '@codigo'
					}, {
						adicionar : {
							url : urlSer,
							method : 'POST',
							params : {}
						},
						modificar : {
							url : urlSer + '/:codigo',
							method : 'PUT',
							params : {}
						},
						eliminar : {
							url : urlSer + '/:codigo',
							method : 'DELETE',
							params : {}
						}
					});

					function listarHoras($http, self) {
						$http.get('horasSer').success(function(data) {
							self.JSONHoras = data;
						}).error(function(data) {
							console.log('Error: ' + data);
						});
					}

					self.editar = function(accion, fecha, solicitud) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && self.customFullscreen;
						self.solicitud = {
							fecha : fecha
						};
						if (accion == 'Modificar') {
							self.solicitud = solicitud;
						}
						$mdDialog.show({
									controller : DialogController,
									controllerAs : 'ctrlFrm',
									templateUrl : 'videoconferencia/agendaSala/forma.evento.jsp',
									parent : angular
											.element(document.body),
									fullscreen : useFullScreen,
									locals : {
										valores : self.solicitud,
										fecha : fecha,
										accion : accion
									}
								}).then(function(answer) {

								});
					};

					function DialogController($http, $mdDialog, accion, valores, fecha, $timeout, $q) {

						var self = this;
						self.accion = accion;
						self.solicitud = valores;
						self.docente = {};
						self.fecha = fecha;
						listarHoras($http, self);

						self.itemsDoc = [];
						searchTextChangeDoc("");
						self.selectedItemChangeDoc = selectedItemChangeDoc;
						self.searchTextChangeDoc = searchTextChangeDoc;

						if (self.accion == 'Modificar') {
							self.fecha = self.solicitud.fecha;
							self.selectedItemDoc = {
								value : self.solicitud.uaaPersonal,
								display : self.solicitud.uaaPersonal.persona.nombre+ " "+ self.solicitud.uaaPersonal.persona.apellido
							}

							buscarCurso("",self.solicitud.uaaPersonal.persona.codigo);
						}
						self.hide = function() {
							$mdDialog.hide();
						};

						self.cancel = function() {
							$mdDialog.cancel();
						};

						function mostrarRespuesta(data) {
							$mdDialog.show($mdDialog.alert().title(
									aplicacionTitulo).textContent(data.mensaje)
									.ariaLabel(aplicacionTitulo).ok('Aceptar'));
						}

						self.answer = function(answer) {
							if (accion == "Adicionar") {
								self.solicitud.fecha = $filter('date')(self.fecha,"yyyy-MM-dd'T'05:mm:ss.sss'Z'");
								solicitudResource.adicionar({},self.solicitud,
												function(data) {
													$mdDialog.hide(answer);
													mostrarRespuesta(data);
													$route.reload();
													setTimeout(function() {
														$('#btnCalendar')														.click();
													}, 200);
												},
												function(response) {
													self.MsgError = response.data.mensaje;
													self.showHints = true;
												})
							}
							if (accion == "Modificar") {
								self.solicitud.fecha = $filter('date')(
								self.solicitud.fecha,"yyyy-MM-dd'T'05:mm:ss.sss'Z'");								solicitudResource
								.modificar({},self.solicitud,
										function(data) {
											$mdDialog.hide(answer);
											mostrarRespuesta(data);
										},
										function(response) {
											self.MsgError = response.data.mensaje;
											self.showHints = true;
										})
							}
						};

						function buscarCurso(text, docente) {
							$http.get('cursosServ?codigo=' + docente+ '&criterio=' + text).success(function(data) {
								self.JSONCurso = data;
							}).error(function(data) {
								console.log('Error: ' + data);
							});
						}
						
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
								self.solicitud.uaaPersonal = self.docente;
								
								self.items = [];
								self.selectedItemCurso = "";
								
								buscarCurso("",self.docente.persona.codigo);
							}
						}

					}
				})

$(document).ready(function() {
	setTimeout(function() {
		$('#btnCalendar').click();
	}, 700);
	setTimeout(function() {
		$('#btnCalendar').click();
	}, 300);
})