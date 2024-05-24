(function(angular) {
	'use strict';
	angular.module('administrador', [ 'ngMaterial', 'ngRoute', 'ngAnimate',
			'menu.controller', 'asignatura.controller',
			'resoluciones.controller', 'planAcademico.controller',
			'planAcademicoLista.controller', 'uaa.controller',
			'programa.controller', 'ofertaAcademica.controller',
			'nivelAcademico.controller', 'convenio.controller',
			'perfil.controller', 'nbc.controller', 'agendasala.controller',
			'slider.controller', 'asignarHoras.controller',
			'inscritos.controller', 'matriculados.controller',
			'configuracionreserva.controller', 'solicitudes.controller',
			'adjudicacionesreserva.controller', 'cursos.controller',
			'horario.controller','certificados.controller' ]);

	angular.module('menu.controller', [ 'menu.service', 'ngMaterial' ]);
	angular.module('menu.service', []);
	angular.module('asignatura.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('resoluciones.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('planAcademico.controller', [ 'ngMaterial', 'ngResource','ngMessages', 'ngRoute' ]);
	angular.module('planAcademicoLista.controller', [ 'ngMaterial','ngMessages' ]);
	angular.module('uaa.controller',[ 'ngMaterial', 'ngResource', 'ngMessages' ]);
	angular.module('programa.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('ofertaAcademica.controller', [ 'ngMaterial', 'ngResource','ngMessages', 'ui.tinymce', 'angularjs-datetime-picker' ]);
	angular.module('nivelAcademico.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('convenio.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('perfil.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('nbc.controller',[ 'ngMaterial', 'ngResource', 'ngMessages' ]);
	angular.module('agendasala.controller', [ 'ui.calendar', 'ngMaterial','ngResource', 'ngMessages' ]);
	angular.module('asignarHoras.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('slider.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('inscritos.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('matriculados.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('configuracionreserva.controller', [ 'ngMaterial','ngResource', 'ngMessages' ]);
	angular.module('solicitudes.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('adjudicacionesreserva.controller', [ 'ngMaterial' ]);
	angular.module('cursos.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('horario.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
	angular.module('certificados.controller', [ 'ngMaterial', 'ngResource','ngMessages' ]);
})(window.angular);
