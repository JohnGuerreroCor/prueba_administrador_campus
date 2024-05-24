$(document).ready(function() {

	$.get('cantidadSolicitudSer/2', function(data) {
		if (data > 0) {
			show_alert(data);
		}
	}, 'json');
})
function show_alert(data) {
	if(data == '1'){
		s = 'solicitud';
	}else{
		s = 'solicitudes';
	}
	msg = '<div class="alert-reserva"><i class="fa fa-envelope" aria-hidden="true"></i> Usted tiene <b>'
			+ data
			+ '</b> '+s+' de reserva de espacio para videoconferencia por adjudicar o rechazar, por favor revisar las solicitudes. <a class="alerta-link" href="#/videoconferencia/listaSolicitudes">Aqui</a></div>';
	alertify.message(msg, 0);
}