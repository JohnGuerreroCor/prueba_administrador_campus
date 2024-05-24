(function(){
	'use strict';

	angular
		.module('menu.controller')
		.controller('MenuController',[
			'menuService',
			'$mdSidenav',
			'$mdBottomSheet', 
			'$timeout',
			'$log',
			function (menuService,$mdSidenav,$mdBottomSheet, $timeout, $log, $window){
				var self = this;

				self.selected = null;
				self.menu = [];
				self.selectMenu = selectMenu;
				self.toggleMenu   = toggleMenuList;
				self.cargarCalendar = cargarCalendar;
				self.salir = salir;
				self.portal = portal;
				
				menuService
					.loadAllMenu()
					.then( function(menu){
						self.menu = [].concat(menu);
					});


				function toggleMenuList() {
			      $mdSidenav('right').toggle();
			    }
				
				function cargarCalendar(){
					setTimeout(function() {$('#btnCalendar').click();}, 700);
					setTimeout(function() {$('#btnCalendar').click();}, 300);
				}

			    function selectMenu ( link ) {
			      self.selected = angular.isNumber(link) ? $scope.menu[link] : link;
			    }
			    
			    function salir(){
			    	window.open('app.salir','_top');
			    }

			    function portal(){
			    	window.open('http://localhost:8080/campusvirtual','top');
			    }
			    
			    function toggleSubMenuList( thing ) {
			      	if(thing){
			      		thing=false;
				    }else{
				    	thing=true;
				    }
				    return thing;
				}
			    
			    self.scrollIntervalID = setInterval(stickIt, 100);

				function stickIt() {
					//console.log(screen.width);
				  var orgElementPos = $('#menu-fijo').offset();
				  if(orgElementPos != undefined){
					  var orgElementTop = orgElementPos.top;        
					  if ($(window).scrollTop() >= (orgElementTop)) {
				  		  $('#menuBtns').addClass('menu-fijo');  
					  } else {
						  $('#menuBtns').removeClass('menu-fijo');
					  }
				  }
				}

			}]);

})();