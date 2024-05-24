angular.module('configuracionreserva.controller').controller(
		'ConfiguracionReservaController',
		function($http, $mdDialog, $mdMedia, $resource) {

			var self = this;
			var aplicacionTitulo = "Configuración Reserva de espacios";
			
			self.showHints = false;

			table = $('#tblConfiguraciones').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "configuraciones/lista",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "uaa.nombre"
				}, {
					"data" : "calendario.nombre"
				}, {
					"data" : "diasAnticipacion"
				}, {
					"data" : "maxSolicitudesDias"
				}, {
					"data" : "maxSolicitudesSemana"
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

			$('#tblConfiguraciones tbody').on('click', 'tr', function() {
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

			var url = 'configuracionesSer/';
			var configResource = $resource(url, {
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
			
			function eliminar(){
				configResource.eliminar({},
						self.self,function(data) {
							$mdDialog.hide();
							mostrarRespuesta(data);
						}, function(response) {
							$mdDialog.hide();
							mostrarRespuesta(response.data);
						})
			}
			function DialogController($http, $mdDialog, accion, items) {
				var self = this;
				self.accion = accion;
				self.configuraciones = items;
				
				self.hide = function() {
					$mdDialog.hide();
				};
				self.cancel = function() {
					$mdDialog.cancel();
				};

				self.answer = function(answer) {
					if (accion == "Modificar") {
						configResource.modificar({}, self.configuraciones, function(data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							self.MsgError = response.data.mensaje;
							self.showHints = true;
						})
					}
					if (accion == "Adicionar") {
						configResource.adicionar({}, self.configuraciones, function(data) {
							$mdDialog.hide(answer);
							mostrarRespuesta(data);
						}, function(response) {
							self.MsgError = response.data.mensaje;
							self.showHints = true;
						})
					}
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
				if (self.configuraciones == undefined) {
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
				
				self.configuraciones = table.row('.selected').data();
				if (self.configuraciones == undefined) {
					data={
							mensaje: "Debe seleccionar un registro para realizar esta acción."
					}
					mostrarRespuesta(data);
					return false;							
				}
				
				if(self.configuraciones.codigo == 0){
					accion = "Adicionar";
				}else{
					accion = "Modificar";
				}
				
				/*
				if (accion != "Adicionar"){
					self.configuraciones = table.row('.selected').data();
					if (self.configuraciones == undefined) {
						data={
								mensaje: "Debe seleccionar un registro para realizar esta acción."
						}
						mostrarRespuesta(data);
						return false;							
					}
				}
				*/
				$mdDialog.show({
					controller : DialogController,
					controllerAs : 'ctrlFrm',
					templateUrl : 'videoconferencia/configuraciones/forma.configuracion.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : self.configuraciones,
						accion : accion
					}
				}).then(function(answer) {

				});
			};
			
		})
