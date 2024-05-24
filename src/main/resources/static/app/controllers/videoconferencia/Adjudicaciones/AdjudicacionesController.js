angular
.module('adjudicacionesreserva.controller')
.controller('AdjudicacionesReservaController',
	function($scope, $http, $mdDialog, $mdMedia, $resource) {

		var aplicacionTitulo = "Adjudicaciones Reserva de espacios";
		var self = this;
		self.showHints = false;
		self.msgLista = false;
		
		table = $('#tblAdjudicaciones').DataTable(
			{
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "espacioOcupacionSer/listar",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "tema"
				}, {
					"data" : "espacioOcupacion.descripcion"
				}, {
					"data" : "uaaPersonal.persona.nombre"
				}, {
					"data" : "curso.asignatura.nombre"
				}, {
					"data" : "uaaPersonal.uaa.nombre"
				}, {
					"data" : "espacioOcupacion.fecha"
				},{
					"data" : "horaInicio.hora"
				}, {
					"data" : "horaFin.hora"
				},{
					"data" : "espacioOcupacion.url"
				}, {
					"data" : "tipoAcceso"
				} , {
					"data" : "codigoVideoconferencia",
					"visible":false
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
				"columnDefs": [
				                {"render": function ( data, type, row ) {
			                        return data+" "+row.uaaPersonal.persona.apellido;
			                    },
			                    "targets": 3,
				                },
				                {"render": function ( data, type, row ) {
			                        return data+" "+row.curso.grupo;
			                    },
			                    "targets": 4,
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
			                    "targets": 10,
				                }
				              ],
			});

	$('#tblAdjudicaciones tbody').on('click','tr',
		function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
	});
	
	var url = 'espacioOcupacionSer/';
	var espacioResource = $resource(url, {
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
			method : 'PUT',
			params : {}
		}
	});
	
	function eliminar(){
		data = {
				mensaje: "Enviando información..."
		};
		mostrarRespuesta(data);
		espacioResource.eliminar({}, self.solicitud, function(data) {
					$mdDialog.hide();
					mostrarRespuesta(data);
				}, function(response) {
					$mdDialog.hide();
					mostrarRespuesta(response.data);
				})
	}
	function DialogController($scope, $http, $mdDialog, accion, items) {
		var self = this;
		self.accion = accion;
		self.solicitud = items;
		console.log(self.solicitud);
		listarGrabaciones($http, self,self.solicitud.codigoVideoconferencia);
		self.hide = function() {
			$mdDialog.hide();
		};
		self.cancel = function() {
			$mdDialog.cancel();
		};

		self.answer = function(answer) {
			if (accion == "Modificar") {
				uaaResource.modificar({}, $scope.uaa, function(data) {
					$mdDialog.hide(answer);
					mostrarRespuesta(data);
				}, function(response) {
					$scope.MsgError = response.data.mensaje;
					$scope.showHints = true;
				})
			}
		};
	}

	function listarGrabaciones($http, self, codigo) {
		$http.get('listaGrabaciones?codigo='+codigo).success(function(data) {
			self.JSONGrabaciones = data;
			if (data.length == 0) {
				self.msgLista = true;
			}

		}).error(function(data) {
			console.log('Error: ' + data);
		});
	}
	
	function mostrarRespuesta(data) {
		table.ajax.reload();
		$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
				.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
				.ok('Aceptar'));
	}
	
	self.dialogEliminar = function(ev) {
		self.solicitud = table.row('.selected').data();
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
		var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
				&& self.customFullscreen;

		if (accion == "Adicionar") {
			self.solicitud = {};
		} else {
			if (accion != "Adicionar"){
				self.solicitud = table.row('.selected').data();
				if (self.solicitud == undefined) {
					data={
							mensaje: "Debe seleccionar un registro para realizar esta acción."
					}
					mostrarRespuesta(data);
					return false;							
				}
			}
		}
		
		$mdDialog.show({
			controller : DialogController,
			controllerAs : 'ctrl',
			templateUrl : 'videoconferencia/adjudicaciones/forma.grabaciones.jsp',
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
