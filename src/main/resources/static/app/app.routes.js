(function(angular){
    'use strict';
	angular
		.module('administrador')
      	.config(function($mdThemingProvider, $mdIconProvider, $routeProvider, $locationProvider){
        	$routeProvider
        	$routeProvider.when('/', {
				templateUrl : 'view/home.jsp',
				controller : ''
			}).when('/asignatura', {
				templateUrl : 'asignatura/',
				controller : ''
			}).when('/resolucionesLista', {
				templateUrl : 'listaResoluciones/',
				controller : ''
			}).when('/nuevoPlanAcademico', {
				templateUrl : 'planAcademico/forma.planAcademico.jsp',
				controller : ''
			}).when('/planAcademicoLista', {
				templateUrl : 'planAcademico/',
				controller : ''
			}).when('/uaaLista', {
				templateUrl : 'UAA/',
				controller : ''
			}).when('/programaLista', {
				templateUrl : 'programa/',
				controller : ''
			}).when('/ofertaAcademica', {
				templateUrl : 'oferta/'
			}).when('/correo', {
				templateUrl : 'correo/'
			}).when('/nivelAcademico', {
				templateUrl : 'nivelAcademico/'
			}).when('/convenioLista', {
				templateUrl : 'convenio/'
			}).when('/perfil', {
				templateUrl : 'perfil/'
			}).when('/usuariosInscritos', {
				templateUrl : 'inscritos/',
				controller : ''
			}).when('/usuariosMatriculados', {
				templateUrl : 'matriculados/',
				controller : ''
			}).when('/certificadosxMatriculados', {
				templateUrl : 'certificados/',
				controller : ''
			}).when('/solicitud', {
				templateUrl : 'perfil/solicitud.jsp'
			}).when('/nbc', {
				templateUrl : 'nbc/'
			}).when('/videoconferencia/agendar', {
				templateUrl : 'videoconferencia/agendaSala/'
			}).when('/videoconferencia/configuracion', {
				templateUrl : 'videoconferencia/configuraciones/'
			}).when('/videoconferencia/listaSolicitudes', {
				templateUrl : 'videoconferencia/solicitudes/'
			}).when('/videoconferencia/SolicitudesAdjudicadas', {
				templateUrl : 'videoconferencia/adjudicaciones/'
			}).when('/sliderAdmin', {
				templateUrl : 'slider/'
			}).when('/programacion-academica/cursos',{
				templateUrl : 'prog-academica/cursos/'
			}).when('/programacion-academica/horarios',{
				templateUrl : 'prog-academica/horarios/'
			}).otherwise({redirectTo:'/'})
            $locationProvider.html5Mode(false)
            
            $mdIconProvider
            	.icon("menu", "app/assets/img/admin/menu.svg", 24)
            	.defaultIconSet("app/assets/img/admin/user/avatars.svg", 128);

            $mdThemingProvider
            	.definePalette('UscoPalette', {
	                '50': 'FFEBEE',
	                '100': 'FFCDD2',
	                '200': 'EF9A9A',
	                '300': 'E57373',
	                '400': 'EF5350',
	                '500': 'FFFFFF',
	                '600': 'E53935',
	                '700': 'D32F2F',
	                '800': 'C62828',
	                '900': 'B71C1C',
	                'A100': 'FF8A80',
	                'A200': 'FF5252',
	                'A400': 'FF1744',
	                'A700': 'D50000',
	                'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
	                                // on this palette should be dark or light
	                'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
	                 '200', '300', '400', 'A100'],
	                'contrastLightColors': undefined  
           		});
              
            $mdThemingProvider
            	.theme('default')
                .primaryPalette('UscoPalette')
                /*.primaryPalette('red', {
                	'default': '700' // by default use shade 400 from the pink palette for primary intentions
                })*/
                .accentPalette('red', {
                	'default': '700' // use shade 200 for default, and keep all other shades the same
                });
    	});

	
	angular
	.module('administrador')
  	.directive('validNumber', function() {
  	    return {
  	        require : '?ngModel',
  	        link : function(scope, element, attrs, ngModelCtrl) {
  	            if (!ngModelCtrl) {
  	                return;
  	            }

  	            ngModelCtrl.$parsers.push(function(val) {

  	                if (angular.isUndefined(val)) {
  	                    var val = '';
  	                }

  	                var clean = val.replace(/[^0-9]/g, '');
  	                
  	                var negativeCheck = clean.split('-');
  	                var decimalCheck = clean.split('.');
  	                if (!angular.isUndefined(negativeCheck[1])) {
  	                    negativeCheck[1] = negativeCheck[1].slice(0,
  	                            negativeCheck[1].length);
  	                    clean = negativeCheck[0] + '-' + negativeCheck[1];
  	                    if (negativeCheck[0].length > 0) {
  	                        clean = negativeCheck[0];
  	                    }

  	                }

  	                if (!angular.isUndefined(decimalCheck[1])) {
  	                    decimalCheck[1] = decimalCheck[1].slice(0, 2);
  	                    clean = decimalCheck[0] + '.' + decimalCheck[1];
  	                }

  	                if (val !== clean) {
  	                    ngModelCtrl.$setViewValue(clean);
  	                    ngModelCtrl.$render();
  	                }
  	            

  	                return parseFloat(clean);
  	            });

  	            element.bind('keypress', function(event) {

  	                if (event.keyCode === 32) {

  	                    event.preventDefault();
  	                }
  	            });

  	        }
  	    };
  	});
  	
  	
  	
	
})(window.angular);

