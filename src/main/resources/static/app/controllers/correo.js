function buscar(){
	$.get('consultarPersona?codigo='+$('#txtId').val(),function(data){
		console.log(data);
		$('#txtNombre').val(data.nombre+" "+data.apellido);
		$('#txtCorreo').val(data.email);
		$('#txtCodigo').val(data.id);
	},'json')
}

function guardar(){
	$.get('persona-correo?codigo='+$('#txtCodigo').val()+'&correo='+$('#txtCorreo').val(),function(data){
		alert(data.mensaje);
	},'json')
}

