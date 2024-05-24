<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
#calendar {
	max-width: 900px;
	margin: 0 auto;
}
u20161147937
</style>
<div data-ng-cloak data-ng-controller="AgendaSalaController as ctrl">

	<div layout="row" flex="100" layout-xs="column">
		<div layout="column" flex="10">
			<md-button class="" ng-disabled="true" aria-label="En espera">
			<i class="fa fa-circle" aria-hidden="true" style="color: #DDD"></i>
			En espera </md-button>
		</div>
		<div layout="column" flex="10">
			<md-button class=" " ng-disabled="true" aria-label="Adjudicada">
			<i class="fa fa-circle" aria-hidden="true" style="color: #83f493;"></i>
			Adjudicada </md-button>
		</div>
		<div layout="column" flex="10">
			<md-button class="" ng-disabled="true" ng-disabled="true"
				aria-label="Rechazada"> <i class="fa fa-circle"
				aria-hidden="true" style="color: #ff0000;"></i> Rechazada </md-button>
		</div>
	</div>
	<button class="btn" id="btnCalendar" style="display: none"
		ng-click="ctrl.changeView('month','myCalendar1')">MES</button>
	<div class="calendar" ng-model="ctrl.eventSources"
		calendar="myCalendar1" config="ctrl.uiConfig.calendar"
		ui-calendar="ctrl.uiConfig.calendar"></div>

</div>