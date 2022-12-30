var SGE = SGE || {};

SGE.ComboGrupoProdutos = (function() {
	
	function ComboGrupoProdutos() {
		this.combo = $('#grupoProdutos');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboGrupoProdutos.prototype.iniciar = function() {
		this.combo.on('change', onGrupoProdutosAlterado.bind(this));
	}
	
	function onGrupoProdutosAlterado() {
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboGrupoProdutos;
	
}());

SGE.ComboSubgrupoProdutos = (function() {
	
	function ComboSubgrupoProdutos(ComboGrupoProdutos) {
		this.ComboGrupoProdutos = ComboGrupoProdutos;
		this.combo = $('#subgrupoProdutos');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenSubgrupoProdutosSelecionada = $('#inputHiddenSubgrupoProdutosSelecionada');
	}
	
	ComboSubgrupoProdutos.prototype.iniciar = function() {
		reset.call(this);
		this.ComboGrupoProdutos.on('alterado', onGrupoProdutosAlterado.bind(this));
		var codigoGrupoProdutos = this.ComboGrupoProdutos.combo.val();
		inicializarSubgruposProdutos.call(this, codigoGrupoProdutos);
	}
	
	function onGrupoProdutosAlterado(evento, codigoGrupoProdutos) {
		this.inputHiddenSubgrupoProdutosSelecionada.val('');
		inicializarSubgruposProdutos.call(this, codigoGrupoProdutos);
	}
	
	function inicializarSubgruposProdutos(codigoGrupoProdutos) {
		if (codigoGrupoProdutos) {
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'grupoProdutos': codigoGrupoProdutos }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarSubgruposProdutosFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarSubgruposProdutosFinalizado(subgruposProdutos) {
		var options = [];
		subgruposProdutos.forEach(function(subgrupoProdutos) {
			options.push('<option value="' + subgrupoProdutos.codigo + '">' + subgrupoProdutos.nome + '</option>');
		});
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		
		var codigoSubgrupoProdutosSelecionada = this.inputHiddenSubgrupoProdutosSelecionada.val();
		if (codigoSubgrupoProdutosSelecionada) {
			this.combo.val(codigoSubgrupoProdutosSelecionada);
		}
	}
	
	function reset() {
		this.combo.html('<option value="">Seleccione o subgrupo</option>');
		this.combo.val('select');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboSubgrupoProdutos;
	
}());

$(function() {
	
	var ComboGrupoProdutos = new SGE.ComboGrupoProdutos();
	ComboGrupoProdutos.iniciar();
	
	var ComboSubgrupoProdutos = new SGE.ComboSubgrupoProdutos(ComboGrupoProdutos);
	ComboSubgrupoProdutos.iniciar();
	
});

