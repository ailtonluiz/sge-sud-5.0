SGE.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		
		
		this.valorTotalItens =  this.tabelaItens.valorTotal();
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}
	
	
	function onValoresAlterados() {
		var valorTotal = this.valorTotalItens;
		this.valorTotalBox.html(SGE.formatarMoeda(valorTotal));

		//	this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
		
	}
	
	return Venda;
	
}());

$(function() {
	
	var autocomplete = new SGE.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new SGE.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new SGE.Venda(tabelaItens);
	venda.iniciar();
	
});