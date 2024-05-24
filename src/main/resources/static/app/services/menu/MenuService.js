(function() {
	'use strict';

	angular.module('menu.service').service('menuService', [ '$q', function($q) {
		var menu = [ {
			name : 'Plan de Estudio',
			icon : 'fa fa-archive',
			type : 'toggle',
			content : [ 
			{
				name : 'UAA',
				icon : 'fa fa-list',
				type : 'link',
				url : 'uaaLista'
			
			}, 
			{
				name : 'Resoluciones',
				icon : 'fa fa-pied-piper',
				type : 'link',
				url : 'resolucionesLista'

			}, 
			{
				name : 'Asignatura',
				icon : 'fa fa-book',
				type : 'link',
				url : 'asignatura'

			},
			{
				name : 'Programa',
				icon : 'fa fa-book',
				type : 'link',
				url : 'programaLista'

			},
			{
				name : 'Nuevo Plan Académico',
				icon : 'fa fa-file-o',
				type : 'link',
				url : 'nuevoPlanAcademico'

			},
			{
				name : 'Lista Plan Académico',
				icon : 'fa fa-list',
				type : 'link',
				url : 'planAcademicoLista'

			} ]
		}, {
			name : 'Oferta Académica',
			icon : 'fa fa-ticket',
			type : 'link',
			url : 'ofertaAcademica'
		}, ];

		return {
			loadAllMenu : function() {
				return $q.when(menu);
			}
		}
	} ]);

})();