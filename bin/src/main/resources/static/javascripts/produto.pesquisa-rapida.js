SGE = SGE || {};

SGE.PesquisaRapidaFornecedor = (function() {
	
	function PesquisaRapidaFornecedor() {
		this.pesquisaRapidaFornecedoresModal = $('#pesquisaRapidaFornecedores');
		this.nomeInput = $('#nomeFornecedorModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-fornecedores-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaFornecedores');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-fornecedor').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaFornecedor.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaFornecedoresModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaFornecedoresModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			}, 
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaFornecedorPesquisaRapida = new SGE.TabelaFornecedorPesquisaRapida(this.pesquisaRapidaFornecedoresModal);
		tabelaFornecedorPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaFornecedor;
	
}());

SGE.TabelaFornecedorPesquisaRapida = (function() {
	
	function TabelaFornecedorPesquisaRapida(modal) {
		this.modalFornecedor = modal;
		this.fornecedor = $('.js-fornecedor-pesquisa-rapida');
	}
	
	TabelaFornecedorPesquisaRapida.prototype.iniciar = function() {
		this.fornecedor.on('click', onFornecedorSelecionado.bind(this));
	}
	
	function onFornecedorSelecionado(evento) {
		this.modalFornecedor.modal('hide');
		
		var fornecedorSelecionado = $(evento.currentTarget);
		$('#nomeFornecedor').val(fornecedorSelecionado.data('nome'));
		$('#codigoFornecedor').val(fornecedorSelecionado.data('codigo'));
	}
	
	return TabelaFornecedorPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaFornecedor = new SGE.PesquisaRapidaFornecedor();
	pesquisaRapidaFornecedor.iniciar();
});