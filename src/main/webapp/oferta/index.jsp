<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="OfertaAcademicaController">
		<md-content class="fondoTabla" flex layout-padding>
		<div id="menu-fijo">
			<div id="menuBtns">
				<fieldset class="fieldsetOferta">
					<legend>Gestión de ofertas.</legend>
					<div layout-gt-xs="row">
						<md-button class="md-raised options" ng-click="editar('Adicionar')">
						<i class="fa fa-plus fa-option" aria-hidden="true"></i> Adicionar </md-button>
						<md-button class="md-raised options" ng-click="editar('Modificar')">
						<i class="fa fa-pencil fa-option" aria-hidden="true"></i> Modificar
						</md-button>
						<md-button class="md-raised options"
							ng-click="dialogEliminar($event)"> <i
							class="fa fa-trash fa-option" aria-hidden="true"></i> Eliminar </md-button>
						<md-button class="md-raised options"
							ng-click="editar('Configurar')"> <i
							class="fa fa-cog fa-option" aria-hidden="true"></i> Configuración </md-button>
					</div>
				</fieldset>
				<fieldset class="fieldsetOferta">
					<legend>Para gestionar requisitos o información a la oferta
						primero seleccionar un registro</legend>
					<div layout-gt-xs="row">
						<span flex></span>
						<md-button class="options md-raised"
							data-ng-click="editar('Requisitos')"> <i
							class="fa fa-check-circle-o" aria-hidden="true"></i> Requisitos </md-button>
						<md-button class="options md-raised"
							data-ng-click="editar('Informacion')"> <i
							class="fa fa-info" aria-hidden="true"></i> Agregar Información </md-button>
						<md-button class="options md-raised"
							data-ng-click="editar('ListaInformacion')"> <i
							class="fa fa-eye" aria-hidden="true"></i> Ver Información </md-button>
					</div>
				</fieldset>
			</div>
		</div>
		<!--<div layout-gt-xs="row">
			<md-select flex-gt-sm="35"
				placeholder="Filtrar por facultad..."
				ng-model=""> <md-option
				ng-repeat="uaa in JSONUaa"
				ng-click="cargarOferta(uaa.codigo)" value="{{uaa.codigo}}">
			{{uaa.nombre}} </md-option> 
			</md-select>
		</div> -->
		<table id="tblOfertaAcademica" class="display responsive" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Código</th>
					<th>Título Otorgado</th>
					<th>imagen</th>
					<th>Calendario</th>
					<th>Fecha&nbsp;Inicio</th>
					<th>Fecha&nbsp;Fin</th>
					<th>Inscripción Fecha&nbsp;Inicio</th>
					<th>Inscripción Fecha&nbsp;Fin</th>
					<th>Pre-Inscritos</th>
					<th>Inscritos</th>
					<th>Tipo Admisión</th>
					<th>Requiere Pago</th>
					<th></th>
					<th>Crear Usuario</th>
					<th>imagen</th>
					<th>Requiere Cupo</th>
					<th>Cupos</th>
					<th>Valor</th>
					<th>Uaa</th>
					<th>Estado</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Código</th>
					<th>Título Otorgado</th>
					<th>imagen</th>
					<th>Calendario</th>
					<th>Fecha Inicio</th>
					<th>Fecha Fin</th>
					<th>Inscripción Fecha Inicio</th>
					<th>Inscripción Fecha Fin</th>
					<th>Pre-Inscritos</th>
					<th>Inscritos</th>
					<th>Tipo Admisión</th>
					<th>Requiere Pago</th>
					<th></th>
					<th>Crear Usuario</th>
					<th>imagen</th>
					<th>Requiere Cupo</th>
					<th>Cupos</th>
					<th>Valor</th>
					<th>Uaa</th>
					<th>Estado</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>
</div>