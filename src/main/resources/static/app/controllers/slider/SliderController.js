angular.module('slider.controller').controller(
		'SliderController',
		function($scope, $http, $mdDialog, $mdMedia, $resource, $filter) {

			var table;
			var aplicacionTitulo = "Gestión Slider.";
			
			$scope.showHints = false;
		
			table = $('#tblSlider').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax": {
					"url": "imagenes-slider/listar",
					"type": "POST"
				},
				"columns" : [ {
					"data" : "codigo"
				}, {
					"data" : "url"
				}, {
					"data" : "descripcion"
				}, {
					"data" : "alt"
				}, {
					"data" : "secuencia"
				}, {
					"data" : "estado",
					"visible":false
				}],
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
								    return "<button>Ver</button>"
								},
								"targets": 1,
								}
							]
			});

			$('#tblSlider tbody').on( 'click', 'button', function () {
				var data = table.row($(this).parents('tr')).data();
		        var contenido = null;
		        if(data.url != ''){
		        	contenido = '<img src="sliderSerImagen/imagen?id='+data.codigo+'" style="width:50%;heidth:50%;">';
		        }else{
		        	contenido = '<md-subheader class="md-warn">No se ha cargado ninguna imagen.</md-subheader>';
		        }
		        var templete = '<md-dialog aria-label="Imagen Slider">'				        	  
	        	    +'<h1 class="template-title">Imagen Slider</h1>'
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
			
			
			$('#tblSlider tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});

			$scope.status = '  ';
			$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
			
			function adicionarSliderRouter(slider, answer, imagen, slider, $scope){
				
				$scope.slider = slider;
				$scope.imagen = imagen;
				sliderResource.adicionar({},$scope.slider,
						function(data) {
							if($scope.imagen){
								var fd = new FormData();
								fd.append('file', $scope.imagen);
								$http.post('sliderSerImagen/'+slider.codigo+'/false', fd, {
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
						})
			}


			var url = 'imagenes-slider/';
			var sliderResource = $resource(url, {
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
				sliderResource.eliminar({}, $scope.slider,
						function(data) {
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
			function DialogController($scope, $http, $mdDialog, accion, items) {

				$scope.accion = accion;
				$scope.slider = items;

				if ($scope.accion == 'Adicionar') {
					$scope.slider = {
						descripcion : null,
						secuencia : null,
						alt : null,
						url : null
					}
				}
				
				$scope.hide = function() {
					$mdDialog.hide();
				};

				$scope.cancel = function() {
					$mdDialog.cancel();
				};

				$scope.answer = function(answer) {
					if ($scope.accion == 'Adicionar') {
						if($scope.imagen){
							$http.post('imagenes-slider', $scope.slider).then(function(response){
								var fd = new FormData();
								fd.append('file', $scope.imagen);
								$http.post('sliderSerImagen/' + response.data.body.codigo + '/true', fd, {
									headers : {
										'Content-Type' : undefined
									},
									transformRequest : angular.identity
								}).then(function successCallback(response) {
									file = null;
									adicionarSliderRouter($scope.slider,answer,$scope.imagen, response.data.body, $scope);
									$mdDialog.hide(answer);
									mostrarRespuesta(response.data);
								}, function errorCallback(response) {
									$scope.MsgError = response.data.mensaje;
									$scope.showHints = true;
									return false;
								});
							});
						}else{
							$scope.MsgError = "Falta seleccionar una imagen.";
							$scope.showHints = true;
							return false;
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
						
						sliderResource.modificar({},$scope.slider,
										function(data) {
											if($scope.imagen){
													var fd = new FormData();
													fd.append('file', $scope.imagen);
													$http.post('sliderSerImagen/'+$scope.slider.codigo+'/true', fd, {
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
			
			$scope.dialogEliminar = function(ev) {
				$scope.slider = table.row('.selected').data();
				if ($scope.slider == undefined) {
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
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
				if (accion == "Adicionar") {
					$scope.slider = {};
				} else {
					if (accion != "Adicionar") {
						$scope.slider = table.row('.selected').data();
						if ($scope.slider == undefined) {
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
					templateUrl : 'slider/forma.slider.jsp',
					parent : angular.element(document.body),
					fullscreen : useFullScreen,
					locals : {
						items : $scope.slider,
						accion : accion
					}
				}).then(function(answer) {
				});
				
			};
		});
