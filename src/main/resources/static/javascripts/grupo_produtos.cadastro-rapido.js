$(function() {
	
	var modal = $('#modalCadastroRapidoGrupo');
	var botaoSalvar = modal.find('.js-modal-cadastro-grupo-produtos-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeGrupo = $('#nomeGrupo');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-grupo-produtos');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose)
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeGrupo.focus();
	}
	
	function onModalClose() {
		inputNomeGrupo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeGrupo = inputNomeGrupo.val().trim();
		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeGrupo }),
			error: onErroSalvandoGrupo,
			success: onGrupoSalvo
		});
	}
	
	function onErroSalvandoGrupo(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
	}
	
	function onGrupoSalvo(grupoProdutos) {
		var comboGrupo = $('#grupo');
		comboGrupo.append('<option value=' + grupoProdutos.codigo + '>' + grupoProdutos.nome + '</option>');
		comboGrupo.val(grupoProdutos.codigo);
		modal.modal('hide');
	}
	
});