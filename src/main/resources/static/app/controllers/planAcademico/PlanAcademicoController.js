(function() {
	'use strict';
	angular.module('planAcademico.controller').controller(
			'PlanAcademicoController', DemoCtrl);

	function DemoCtrl($mdDialog, $http, $filter, $route) {
		var self = this;
		self.asignatura = {};

		self.maxDate = new Date();
		listarSedes($http, self);

		listarResolucion($http, self);
		self.asignaturasSeleccionadas = [];

		self.planAcademico = {
			nombre : null,
			programa : {
				codigo : null
			},
			resolucion : {
				codigo : null
			},
			tipoRegistro : null,
			planActual : null,
			creditos : null,
			totalCreditos : null,
			asignaturas : []
		};

		self.modalmaterias = function($event) {
			$mdDialog.show({
				controller : DialogCtrl,
				controllerAs : 'ctrl',
				templateUrl : 'planAcademico/forma.materias.jsp',
				parent : angular.element(document.body),
				targetEvent : $event,
				clickOutsideToClose : true,
				locals : {
					valores : {
						asignatura : self.asignatura,
						planAcademico : self.planAcademico
					}
				}
			}).then(function(answer) {
				if (answer.codigo) {
					self.planAcademico.asignaturas.push(answer);
				}
			});
		}
		self.obtenerProgramas = function() {
			self.planAcademico.programa.codigo = null;
			listarProgramas($http, self, 0);
		}

		self.guardar = function() {
			if (self.planAcademico.asignaturas[0]) {
				// self.planAcademico.fecha =
				// $filter('date')(self.planAcademico.fecha, "yyyy-MM-dd");
				$http.post('plan-academico', self.planAcademico)
						.success(
								function(data) {
									$mdDialog.show($mdDialog.alert().title(
											"Plan Academico").textContent(
											data.mensaje).ariaLabel(
											"Plan Academico").ok('Aceptar'));
									setTimeout($route.reload(), 2000);
								}).error(
								function(data) {
									$mdDialog.show($mdDialog.alert().title(
											"Plan Academico").textContent(
											data.mensaje).ariaLabel(
											"Plan Academico").ok('Aceptar'));
								});
			} else {
				$mdDialog.show($mdDialog.alert().title("Plan Academico")
						.textContent("Falta agregar las asignaturas")
						.ariaLabel("Plan Academico").ok('Aceptar'));
			}
		};

		self.eliminarAsignatura = function(index) {
			self.planAcademico.asignaturas.splice(index, 1);
		};
	}

	function DialogCtrl($timeout, $q, $scope, $mdDialog, $http, valores) {
		var self = this;

		componentes(self, $http);

		self.items = [];
		self.selectedItemChange = selectedItemChange;
		self.searchTextChange = searchTextChange;
		self.agregarAsignatura = agregarAsignatura;
		self.planAcademico = valores.planAcademico;
		self.searchTextChange("");
		self.cancel = function($event) {
			$mdDialog.cancel();
		};
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
			// console.log('item ' + JSON.stringify(item));
			self.asignatura = item.value;
			self.asignatura.creditos = null;
			self.asignatura.trabajoIndependiente = null;
		}

		function agregarAsignatura(valores) {
			console.log(self.asignatura.codigo);

			if (self.planAcademico.asignaturas.length > 0) {
				for (var a = 0; a < self.planAcademico.asignaturas.length; a++) {
					if (self.planAcademico.asignaturas[a].codigo == self.asignatura.codigo) {
						$mdDialog.show($mdDialog.alert()
								.title("Plan Academico").textContent(
										"Esta asignatura ya fue agregada.")
								.ariaLabel("Plan Academico").ok('Aceptar'));
						$mdDialog.hide();
					}
				}
			}
			console.log(self.asignatura);
			$mdDialog.hide(self.asignatura);
		}
	}

	function componentes(self, $http) {
		$http.get('componenteLista').success(function(data) {
			self.listaComponentes = data;
		}).error(function(data) {
			console.log('Error: ' + data);
		});
	}

	function listarSedes($http, self) {
		$http.get('sedesAll?estado=1').success(function(data) {
			self.JSONSedes = data;
		}).error(function(data) {
			console.log('Error: ' + data);
		});
	}

	function listarProgramas($http, self, proMod) {
		$http.get(
				'programasLista/' + self.planAcademico.sede.codigo + "/"
						+ proMod).success(function(data) {
			self.JSONProgramas = data;
		}).error(function(data) {
			console.log('Error: ' + data);
		});
	}

	function listarResolucion($http, self) {
		$http.get('resolucionLista/0').success(function(data) {
			self.JSONResolucion = data;
		}).error(function(data) {
			console.log('Error: ' + data);
		});
	}
})();