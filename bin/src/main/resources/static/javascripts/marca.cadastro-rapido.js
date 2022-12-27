$(function() {
	
	var modal = $('#modalCadastroRapidoMarca');
	var botaoSalvar = modal.find('.js-modal-cadastro-marca-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeMarca = $('#nomeMarca');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-marca');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose)
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeMarca.focus();
	}
	
	function onModalClose() {
		inputNomeMarca.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeMarca = inputNomeMarca.val().trim();
		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeMarca }),
			error: onErroSalvandoMarca,
			success: onMarcaSalvo
		});
	}
	
	function onErroSalvandoMarca(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
	}
	
	function onMarcaSalvo(marca) {
		var comboMarca = $('#marca');
		comboMarca.append('<option value=' + marca.codigo + '>' + marca.nome + '</option>');
		comboMarca.val(marca.codigo);
		modal.modal('hide');
	}
	
});