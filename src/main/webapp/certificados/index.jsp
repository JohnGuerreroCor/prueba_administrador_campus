<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="app/assets/css/inscritos.css" />
<div ng-cloak>
	<div id="dialogContainer" ng-controller="CertificadosController">
		<md-content class="fondoTabla" flex layout-padding>



		<div layout-gt-xs="row">
			<md-select
				placeholder="Seleccionar el Programa para obtener los matriculados..."
				ng-model="UsersInscritoCtrl.oferta"> <md-option
				ng-repeat="oferta in JSONUsuariosMatriculados"
				ng-click="cargarMatriculadosCertificados(oferta.codigo)"
				value="{{oferta.codigo}}"> {{oferta.codigo}} -
			{{oferta.nombre}} </md-option> </md-select>
		</div>
		<div class="table-container">
			<table id="tblMatriculados" class="tablet display responsive"
				cellspacing="0" width="100%" border="1">
				<thead>
					<tr>
						<th>SELECCIONAR</th>
						<th>MATRICULA</th>
						<th>IDENTIFICACION</th>
						<th>NOMBRE</th>
						<th>CALENDARIO</th>
					</tr>
				</thead>
				<tfoot>
					<tr ng-repeat="matricula in JSONPersonasCertificados">
						<td><input type="checkbox" value="{{matricula.codigo}}" name="chekEmail" ng-model="UsersInscritoCtrl.sendMail">
						</td>
						<td>{{matricula.codigo}}</td>
						<td>{{matricula.persona.identificacion}}</td>
						<td>{{matricula.persona.nombre}}
							{{matricula.persona.apellido}}</td>
						<td>{{matricula.calendario.nombre}}</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div layout-gt-xs="row">
			<span flex></span>
			<md-button class="options md-raised _250"
				data-ng-click="enviarCertificado()"> Enviar Certificado </md-button>
		</div>
		</md-content>
	</div>
	</body>
	</html>