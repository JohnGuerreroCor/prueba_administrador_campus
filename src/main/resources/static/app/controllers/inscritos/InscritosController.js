angular.module('inscritos.controller').controller(
		'InscritosController',
		function($scope, $http, $mdDialog, $mdMedia, $resource) {

			var table;
			var aplicacionTitulo = "Gestión Usuarios Inscritos";
			
			$scope.showHints = false;
			
			table = $('#tblInscritos').DataTable();
			
			$scope.cargarInscritos = function (codigoOferta){
				
				table.destroy();
				table = $('#tblInscritos').DataTable({
					"processing" : true,
					"serverSide" : true,
					"ajax": {
						"url": "ListaUsuariosInscritos?codigoOferta=" + codigoOferta,
						"type": "POST"
					},
					"columns" : [ {
						"data" : "codigo"
					},{
						"data" : "persona.identificacion"
					}, {
						"data" : "persona.nombre"
					}, {
						"data" : "persona.apellido"
					}, {
						"data" : "oferta.programa.nombre"
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
	                    "sLoadingRecords": "Cargando... ",
	                    "oPaginate": {
	                        "sFirst": "Primero",
	                        "sLast": "Último",
	                        "sNext": "Siguiente",
	                        "sPrevious": "Anterior"}}
				});
				
			}
	
			$('#tblInscritos tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});
			
			$scope.status = '  ';
			$scope.myData = null;
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

			var url = 'admisionUsuario';
			var uaaResource = $resource(url, {
				codigo : '@codigo'
			}, {
				verificar : {
					url : url + '/:codigo',
					method : 'GET',
					params : {}
				}
			});
			
			function InformacionPersonalInscrito($http, $scope) {
				$http.get('InformacionPersonalInscrito?ins_codigo=' + $scope.inscripcion.codigo).success(function(data) {
					$scope.JSONInformacionPersonalInscrito = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}

			function loadProgramaRequisitos($http, $scope) {
				$http.get('consultarRequisitosAdjuntosOferta?codigo=' + $scope.inscripcion.codigo+'&oferta=0').success(function(data) {
					$scope.JSONRequisitosOfertaInscrita = data;	
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			};
			
			function loadEstudisAnteriores($http, $scope) {
				console.log($scope.inscripcion);
				$http.get('consultarEstudiosAnterioresInscripcion?persona=' + $scope.inscripcion.persona.codigo+'&tercero='+$scope.inscripcion.persona.id).success(function(data) {
					$scope.JSONEstudiosAnterioresInscripcion = data;	
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			};
			
			function loadArchivosAdjuntosInscripcion($http, $scope) {
				$http.get('consultarRequisitosAdjuntosInscripcion?codigo=' + $scope.inscripcion.codigo).success(function(data) {
					$scope.JSONArchivosAdjuntosInscripcion = data;					
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			};
			
			function listarCombobox($http, $scope) {
				$http.get('ProgramasInscripciones').success(function(data) {
					$scope.JSONUsuariosInscritos = data;
				}).error(function(data) {
					console.log('Error: ' + data);
				});
			}
			listarCombobox($http, $scope);
			
			function verificar(){
				uaaResource.verificar({},
				$scope.inscripcion,
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
				$scope.inscripcion = items;
				InformacionPersonalInscrito($http, $scope);				
				loadProgramaRequisitos($http, $scope);
				loadEstudisAnteriores($http, $scope);
				loadArchivosAdjuntosInscripcion($http, $scope);
				
				if (accion == "Adicionar") {
					$scope.inscripcion = {
						"persona" : {
							"tipoIdentificacion" : {
								"codigo" : null,
							}
						},
						"nombre" : null,
						"nombreCorto" : null,
						"nombreImpresion" : null,
						"sede" : {
							"codigo" : null
						},
						"municipio" : {
							"codigo" : null
						},
						"email" : null,
						"dependencia" : null,
						"emailPqr" : null,
						"pagina" : null,
						"direccion" : null,
						"fax" : null,
						"centroCostos" : null,
						"acronimo" : null,
						"telefono" : null
					}
				}
				
				$scope.hide = function() {
					$mdDialog.hide();
				};
				$scope.cancel = function() {
					$mdDialog.cancel();
				};

				$scope.answer = function(answer) {
					if (accion == "Admitir") {
						verificar();
					}
				};
			}

			function mostrarRespuesta(data) {
				table.ajax.reload();
				$mdDialog.show($mdDialog.alert().title(aplicacionTitulo)
						.textContent(data.mensaje).ariaLabel(aplicacionTitulo)
						.ok('Aceptar'));
			}
			

			$scope.editar = function(accion) {
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
						&& $scope.customFullscreen;

				if (accion == "Admitir"){
					$scope.inscripcion = table.row('.selected').data();
					if ($scope.inscripcion == undefined) {
						data={
								mensaje: "Debe seleccionar un registro para realizar esta acción."
						}
						table.ajax.reload();
						mostrarRespuesta(data);
						return false;							
					}
				}else if (accion == "Adicionar") {
					$scope.inscripcion = {};
					
				} else {
					if (accion != "Adicionar"){
						$scope.inscripcion = table.row('.selected').data();
						if ($scope.inscripcion == undefined) {
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
					templateUrl : 'inscritos/forma.verificarInformacion.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : $scope.inscripcion,
						accion : accion
					}
				}).then(function(answer) {

				});
			};
		});

$(document).ready(function() {
	$('#tblInscritos thead th').click(function() {
		alert(":(");
		console.log("============");
	})
})