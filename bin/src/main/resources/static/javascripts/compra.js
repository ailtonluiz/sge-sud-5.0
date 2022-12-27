SGE.Compra = (function() {

	function Compra(tabelaItensCompra) {
		this.tabelaItensCompra = tabelaItensCompra;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');

		this.valorTotalItens = this.tabelaItensCompra.valorTotal();
		this.valorFrete = this.valorFreteInput.data('valor');
		this.valorDesconto = this.valorDescontoInput.data('valor');
	}

	Compra.prototype.iniciar = function() {
		this.tabelaItensCompra.on('tabela-itens-atualizada', onTabelaItensCompraAtualizada.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));

		this.tabelaItensCompra.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.valorFreteInput.on('keyup', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));
		

		onValoresAlterados.call(this);
	}

	function onTabelaItensCompraAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}

	function onValorFreteAlterado(evento) {
		this.valorFrete = SGE.recuperarValor($(evento.target).val());
	}

	function onValorDescontoAlterado(evento) {
		this.valorDesconto = SGE.recuperarValor($(evento.target).val());
	}

	function onValoresAlterados() {
		var valorTotalCompra = numeral(this.valorTotalItens) + numeral(this.valorFrete) - numeral(this.valorDesconto);
		this.valorTotalBox.html(SGE.formatarMoeda(valorTotalCompra));

		this.valorTotalBoxContainer.toggleClass('negativo', valorTotalCompra < 0);

	}

	return Compra;

}());

$(function() {

	var autocomplete = new SGE.Autocomplete();
	autocomplete.iniciar();

	var tabelaItensCompra = new SGE.TabelaItensCompra(autocomplete);
	tabelaItensCompra.iniciar();

	var compra = new SGE.Compra(tabelaItensCompra);
	compra.iniciar();

});