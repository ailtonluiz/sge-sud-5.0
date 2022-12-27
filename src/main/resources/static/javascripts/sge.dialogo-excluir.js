SGE = SGE || {};

SGE.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn')
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1) {
			swal('¡Pronto!', 'Eliminado con éxito!', 'success');
		}
	}
	
	function onExcluirClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: '¿Estás seguro?',
			text: '¿Eliminar "' + objeto + '"? No podrás recuperarte más tarde.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: '¡Sí, borra ahora!',
			cancelButtonText: 'No',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onErroExcluir(e) {
		console.log('ahahahah', e.responseText);
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
	
}());

$(function() {
	var dialogo = new SGE.DialogoExcluir();
	dialogo.iniciar();
});
