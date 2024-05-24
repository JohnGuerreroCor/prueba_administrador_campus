<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="app/assets/css/inscritos.css" />
<md-dialog aria-label="Lista Oferta Información" ng-cloak flex="90">
	<h1 class="template-title">Información de la matricula número: {{inscripcion.codigo}}</h1>
	<md-dialog-content>
	<md-subheader class="md-warn" data-ng-if="msgLista">No hay información del inscrito.</md-subheader>
	<div layout-gt-xs="row" id="container-list-subject">
		<md-list class="list-subjects" flex>
			<md-list-item class="md-3-line">
	          <div class="md-list-item-text" layout="column">
	          	<br>
	          	
	          	<table>
	          		<tr>
	          			<td colspan="2"><h2><b>Información Personal</b></h2></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Nombre </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.nombre}} {{JSONInformacionPersonalInscrito.persona.apellido}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Indentificación </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.tipoIdentificacion.nombre}} ({{JSONInformacionPersonalInscrito.persona.tipoIdentificacion.nombreCorto}}) Nro. {{JSONInformacionPersonalInscrito.persona.identificacion}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Fecha de Expedición </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.fechaExpedicion}} en {{JSONInformacionPersonalInscrito.persona.lugarExpedicion.nombre}}</h3></td>
	          		</tr>
	          		
	          		<tr>
	          			<td><h3><b>Genero </b></h3></td>
	          			<td><h3 ng-if="JSONInformacionPersonalInscrito.persona.genero=='M'">Masculino</h3> <h3 ng-if="JSONInformacionPersonalInscrito.persona.genero=='F'">Femenino</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Estado Civil </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.estadoCivil.nombre}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Grupo Sanguineo </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.grupoSanguineo.nombre}}</h3></td>
	          		</tr>	          		
	          		<tr>
	          			<td><h3><b>Email </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.email}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Lengua Nativa </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.lenguaNativa.nombre}}</h3></td>
	          		</tr>
	          		
	          		
	          		<tr>
	          			<td colspan="2"><h2><b>Información Complementaria</b></h2></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Fecha y lugar de Nacimiento </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.fechaNacimiento}} en {{JSONInformacionPersonalInscrito.persona.lugarNacimiento.nombre}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Lugar Residencia Actualmente </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.lugarResidencia.nombre}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Dirección y Barrio </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.direccion}}, {{JSONInformacionPersonalInscrito.persona.barrio}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Teléfono </h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.telefono}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Estrato Socioeconomico </h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.estrato.nombre}}</h3></td>
	          		</tr>
	          		
	          		<tr>
	          			<td><h3><b>Eps </h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.eps.nombre}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Tipo Afiliación e Identificación Cotizante </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.persona.tipoAfiliacion.nombre}} Nro. {{JSONInformacionPersonalInscrito.persona.identificacionCotizante}}</h3></td>
	          		</tr>
	          		
	          		<tr>
	          			<td colspan="2"><h2><b>Información Oferta Académica</b></h2></td>
	          		</tr>
	          		
	          		<tr>
	          			<td><h3><b>Codigo </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.oferta.codigo}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Fecha Inscripción </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.oferta.fechaIncripcionInicio}} a {{JSONInformacionPersonalInscrito.oferta.fechaIncripcionFin}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Fecha Oferta </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.oferta.fechaInicio}} a {{JSONInformacionPersonalInscrito.oferta.fechaFin}}</h3></td>
	          		</tr>
	          		<tr>
	          			<td><h3><b>Programa </b></h3></td>
	          			<td><h3>{{JSONInformacionPersonalInscrito.oferta.programa.titulo_otorgado}} ({{JSONInformacionPersonalInscrito.oferta.programa.uaa.nombre}})</h3></td>
	          		</tr>
	          		
	          		<tr>
	          			<td colspan="2"><h2><b>Requisitos Oferta Académica</b></h2></td>
	          		</tr>
	          		
	          		<tr>
	          			<td colspan="2">
	          				<h3 data-ng-repeat="estudios in JSONEstudiosAnterioresInscripcion">
	          				<b>Titulo:</b> {{estudios.titulo}} - <b>Institucion:</b> {{estudios.institucion}} - <b>Nivel Academico:</b> {{estudios.nivelAcademico.nombre}} - <b>Año de Grado:</b> {{estudios.anio}}
	          				</h3>
	          			</td> <!-- {{archivos.ruta}} -->
	          		</tr>
	          		
	          		<tr>
	          			<td colspan="2"><h2><b>Requisitos Oferta Académica</b></h2></td>
	          		</tr>
	          		
	          		<tr>
	          			<td><h2>Archivos Necesarios </h2></td>
	          			<td><h2>Archivos Adjuntos </h2></td>{{archivos.ruta}} 
	          		</tr>
	          		
	          		<tr>
	          			<td><h3 data-ng-repeat="requisitos in JSONRequisitosOfertaInscrita">{{requisitos.ofertaRequisitos.descripcion}} ({{requisitos.ofertaRequisitos.nombreTipo}})</h3></td>
	          			<td><h3 data-ng-repeat="archivos in JSONArchivosAdjuntosInscripcion"><a href="api/imagen/{{archivos.nombreEncriptado}}">Descargar {{archivos.nombreCompleto}}</a></h3></td>  {{archivos.ruta}}
	          		</tr>
	          	</table>
	          	<br>
	          </div>
	          <md-divider></md-divider>
	        </md-list-item>
		</md-list>
		
	</div>			
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	 <md-button class="md-raised md-primary" data-ng-click="hide($event)">
		Cerrrar 
	</md-button> 
	</md-dialog-actions>
</form>
</md-dialog>