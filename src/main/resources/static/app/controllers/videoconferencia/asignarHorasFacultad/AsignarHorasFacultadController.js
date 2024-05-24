angular
	.module('asignarHoras.controller')
	.controller('AsignarHorasController',
	
		function($http, $mdDialog, $mdMedia, $resource) {

			var self = this;
			var aplicacionTitulo = "Asignar Horas Facultad";

			self.numLicencias = null; 
			self.numHorasDisponibles = null;
			self.numHorasSemana = null;
			cargarHoras();
			
			$http.get('licenciasNum').success(function(data) {
				self.numLicencias = data.numSesiones;
				self.numHorasSemana = data.numTotalHoras;
			}).error(function(data) {
				console.log('Error: ' + data);
			});
			
			function cargarHoras(){
				$http.get('horasFacultadSer').success(function(data) {
					self.facultades = data;
					self.numHorasDisponibles = 0;
					var length = self.facultades.length;
					for (i = 0; i < length; i++) {
						self.numHorasDisponibles = self.numHorasDisponibles + self.facultades[i].horas; 
					}; 
					self.numHorasDisponibles = self.numHorasSemana - self.numHorasDisponibles;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			
			var url = 'horasFacultadSer/';
			var horasFacultadResource = $resource(url, {
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
			
			function mostrarRespuesta(data) {
				cargarHoras();
				$mdDialog.show($mdDialog.alert()
						.title(aplicacionTitulo).textContent(
								data.mensaje).ariaLabel(
								aplicacionTitulo).ok('Aceptar'));
			}
			
			self.editar = function(accion, facultad) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && self.customFullscreen;

				self.asignarHoras = facultad;

				$mdDialog.show({
					controller : DialogController,
					controllerAs : 'ctrlFrm',
					templateUrl : 'videoconferencia/asignarHorasFacultad/forma.asignarHoras.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : self.asignarHoras,
						accion : accion
					}
				}).then(function(answer) {
					
				});
			};
			
			function DialogController($http, $mdDialog, accion, items) {
				
				var self = this;
				self.accion = accion;
				self.asignarHoras = items;
				if (self.accion == 'Adicionar') {
					/*self.evento = {
						"horaInicio" : null,
						"horaFin" : null,
						"fecha" : null
					};*/
				}
				self.hide = function() {
					$mdDialog.hide();
				};
				
				self.cancel = function() {
					$mdDialog.cancel();
				};
				
				self.answer = function(answer) {
					if (self.accion == 'Adicionar') {
						horasFacultadResource.adicionar({}, self.asignarHoras,
							function(data) {
								$mdDialog.hide(answer);
								mostrarRespuesta(data);
							}, function(response) {
								self.MsgError = response.data.mensaje;
								self.showHints = true;
							})
					}
					if (self.accion == 'Modificar') {
						horasFacultadResource.modificar({}, self.asignarHoras,
							function(data) {
								$mdDialog.hide(answer);
								mostrarRespuesta(data);
							}, function(response) {
								self.MsgError = response.data.mensaje;
								self.showHints = true;
							})
					}
				};
			}

		})
