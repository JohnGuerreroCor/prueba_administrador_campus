angular.module('nivelAcademico.controller').controller(
	'NivelAcademicoController',
	function($scope, $http, $mdDialog, $mdMedia, $resource) {

		var table;
		var aplicacionTitulo = "Gestión Nivel Académico";
		
		$scope.showHints = false;
		
		table = $('#tblNivelAcademico').DataTable({
			"processing": true,
			"serverSide": true,
			"ajax": {
				"url" : "niveles/lista",
				"type": "POST"
			},
			"columns" : [
				{"data" : "codigo"}
				,{"data" : "nombre"}
				,{"data" : "orden","visible":false}
				,{"data" : "formalidad.nombre"}
				,{"data" : "snies","visible":false}
				,{"data" : "formalidad.codigo","visible":false}
				],
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
		
		$('#tblNivelAcademico tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
		});
		
		
		$scope.status = '  ';
		$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
		
		var url = 'niveles/';
		var nivelResource = $resource(url, {codigo:'@codigo'}, {
			adicionar:{url:url, method: 'POST', params:{}},
			modificar:{url:url + '/:codigo', method: 'PUT', params:{}},
			eliminar:{url:url + '/:codigo', method: 'DELETE', params:{}}
		});	
		
		function listarFormalidad($http, $scope) {
			$http.get('formalidadSer').success(function(data) {
				$scope.JSONFormalidad = data;
			}).error(function(data) {
				console.log('Error: ' + data);
			});
		}
		
		function eliminar(){
			nivelResource.eliminar({}, $scope.nivel, function(data){
				$mdDialog.hide();
				mostrarRespuesta(data);
			}, function(response) {
				$mdDialog.hide();
				mostrarRespuesta(response.data);
			});
		}
		
		function DialogController($scope, $http, $mdDialog, accion, items) {
			listarFormalidad($http, $scope);
			$scope.accion=accion;
			$scope.nivel=items;
			$scope.hide = function() {
				$mdDialog.hide();
			};
			$scope.cancel = function() {
				$mdDialog.cancel();
			};
			$scope.answer = function(answer) {
				if(accion == "Adicionar") {
					nivelResource.adicionar({}, $scope.nivel, function(data){
						$mdDialog.hide(answer);
						mostrarRespuesta(data);
					}, function(response) {
						$scope.MsgError = response.data.mensaje;
						$scope.showHints = true;
					});
				}
				if(accion == "Modificar") {
					nivelResource.modificar({}, $scope.nivel, function(data){
						$mdDialog.hide(answer);
						mostrarRespuesta(data);
					}, function(response) {
						$scope.MsgError = response.data.mensaje;
						$scope.showHints = true;
					});
				}
			};
		}
		
		function mostrarRespuesta(data){
			table.ajax.reload();		
			$mdDialog.show(
				$mdDialog.alert()
			        .title(aplicacionTitulo)
			        .textContent(data.mensaje)
			        .ariaLabel(aplicacionTitulo)
			        .ok('Aceptar')
			);
		}
		
		$scope.dialogEliminar = function(ev) {
		    // Appending dialog to document.body to cover sidenav in docs app
			$scope.nivel = table.row('.selected').data();
			if ($scope.nivel == undefined) {
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
						
		$scope.editar = function(accion) {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
					&& $scope.customFullscreen;
			
			if(accion == "Adicionar"){
				$scope.nivel = {};
			}
			else{			
				if (accion != "Adicionar") {
					$scope.nivel = table.row('.selected').data();
					if($scope.nivel == undefined){
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
				templateUrl : 'nivelAcademico/forma.nivel.jsp',
				parent : angular.element(document.body),
				fullscreen : useFullScreen,
				locals: {
					items: $scope.nivel,
					accion: accion
				}
			}).then(function(answer){
				
			});
		};
	}	
);