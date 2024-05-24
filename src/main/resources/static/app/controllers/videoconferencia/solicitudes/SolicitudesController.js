angular.module('solicitudes.controller').controller(
		'SolicitudesController',
		function($http, $mdDialog, $mdMedia, $resource, $filter) {

			var fecha = new Date();
			var anio = fecha.getFullYear();
			var mes = fecha.getMonth()+1;
			var self = this;
			var aplicacionTitulo = "Solicitudes Reserva de espacios";
			
			self.showHints = false;
			self.loading = false;

			table = $('#tblSolicitudes').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "listaSolicitudesSer",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "tema"
				}, {
					"data" : "descripcion"
				}, {
					"data" : "uaaPersonal.uaa.nombre"
				}, {
					"data" : "uaaPersonal.persona.nombre"
				}, {
					"data" : "curso.asignatura.nombre"
				},{
					"data" : "curso.cantidad"
				}, {
					"data" : "fecha"
				}, {
					"data" : "horaInicio.hora"
				}, {
					"data" : "horaFin.hora"
				} , {
					"data" : "estado"
				}, {
					"data" : "tipoAcceso"
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
                        "sPrevious": "Anterior"}},
			"columnDefs": [
			                {"render": function ( data, type, row ) {
		                        return data+" "+row.uaaPersonal.persona.apellido;
		                    },
		                    "targets": 4,
			                },
			                {"render": function ( data, type, row ) {
		                        return data+" "+row.curso.grupo;
		                    },
		                    "targets": 5,
			                },
			                {"render": function ( data, type, row ) {
		                        if(data == 2){
		                        	return "En&nbsp;Espera";
		                        }
		                        if(data == 0){
		                        	return "Rechazada";
		                        }
		                    },
		                    "targets": 10,
			                },
			                {"render": function ( data, type, row ) {
		                        if(data == 0){
		                        	return "Público";
		                        }
		                        if(data == 1){
		                        	return "Con&nbsp;invitados";
		                        }
		                        if(data == 2){
		                        	return "Solo&nbsp;estudiantes&nbsp;registrados";
		                        }
		                    },
		                    "targets": 11,
			                }
			              ],
					});

			$('#tblSolicitudes tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});
			
			self.status = ' ';
			self.myData = null;
			self.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

			var url = 'estadoSolicitudSer/';
			var configResource = $resource(url, {
				codigo : '@codigo'
			}, {
				adjudicar : {
					url : url + '/:codigo',
					method : 'PUT',
					params : {}
				}
			});

			function numSolicitudesMes($http, self, anio, mes, uaaPersona){
				$http.get('cantidadSolicitudDocenteSer?&mes='+mes+'&anio='+anio+'&uaaPersona='+uaaPersona).success(function(data) {
					self.numSolicitudesMes = data;
					self.fechaActual = fecha;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}

			function numHorasMes($http, self, anio, mes, uaaPersona){
				$http.get('cantidadHorasSolicitudDocenteSer?&mes='+mes+'&anio='+anio+'&uaaPersona='+uaaPersona).success(function(data) {
					self.numHorasMes = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}

			function numUsuarios($http, self){
				$http.get('licenciasNum').success(function(data) {
					self.numUsuarios = data.numSesiones;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			
			function numUsuariosSolicitudes($http, self,fecha,hora){
				$http.get('cantidadUsuariosSolicitud/1/'+$filter('date')(fecha,"dd-MM-yyyy")+'/'+hora).success(function(data) {
					self.numUsuariosSolicitud = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			
			function DialogController($http, $mdDialog, accion, items) {
				var self = this;
				self.accion = accion;
				self.solicitud = items;
				numSolicitudesMes($http, self, anio, mes, self.solicitud.uaaPersonal.codigo);
				numHorasMes($http, self, anio, mes, self.solicitud.uaaPersonal.codigo);
				numUsuarios($http, self);
				numUsuariosSolicitudes($http, self, self.solicitud.fecha, self.solicitud.horaInicio.hora24h.substring(0,2));
				
				self.hide = function() {
					$mdDialog.hide();
				};
				self.cancel = function() {
					$mdDialog.cancel();
				};

				self.answer = function(answer) {					
					var btnAdj = document.querySelector("#btnAdjudicar"); 
					btnAdj.setAttribute("disabled", "disabled");
					
					var btnRechazar = document.querySelector("#btnRechazar"); 
					btnRechazar.setAttribute("disabled", "disabled");
					
					self.loading = true;
					
					if (answer == "Adjudicar") {
						self.solicitud.estado = 1;
						self.solicitud.fecha = $filter('date')(self.solicitud.fecha,"yyyy-MM-dd'T'05:mm:ss.sss'Z'");
					}else if(answer == 'Rechazar'){
						self.solicitud.estado = 0;
					}
					configResource.adjudicar({}, self.solicitud, function(data) {
						$mdDialog.hide(answer);
						mostrarRespuesta(data);
						self.loading = false;
						btnAdj.removeAttribute('disabled');
						btnRechazar.removeAttribute('disabled');
					}, function(response) {
						self.MsgError = response.data.mensaje;
						self.showHints = true;
						self.loading = false;
						btnAdj.removeAttribute('disabled');
						btnRechazar.removeAttribute('disabled');
					})
				};
			}
			
			function mostrarRespuesta(data) {
				table.ajax.reload();
				$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
						.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
						.ok('Aceptar'));
			}
			
			self.dialogEliminar = function(ev) {
				self.uaa = table.row('.selected').data();
				if (self.solicitud == undefined) {
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

			self.editar = function(accion) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && self.customFullscreen;
				
				if (accion == "Adjudicar"){
					self.solicitud = table.row('.selected').data();
					
					if (self.solicitud == undefined) {
						data={
								mensaje: "Debe seleccionar un registro para realizar esta acción."
						}
						mostrarRespuesta(data);
						return false;							
					}
				}
			
				$mdDialog.show({
					controller : DialogController,
					controllerAs : 'ctrlFrm',
					templateUrl : 'videoconferencia/solicitudes/forma.adjudicar.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : self.solicitud,
						accion : accion
					}
				}).then(function(answer) {

				});
			};
			
		})
