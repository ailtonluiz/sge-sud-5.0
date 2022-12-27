SGE = SGE || {};

SGE.Autocomplete = (function() {

	function Autocomplete() {
		this.codigoBarrasOuNomeOuReferenciaInput = $('.js-barra-nome-referencia-produto-input');
		var htmlTemplateAutocomplete = $('#template-autocomplete-produto').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(codigoBarrasOuNomeOuReferencia) {
				return this.codigoBarrasOuNomeOuReferenciaInput.data('url') + '?codigoBarrasOuNomeOuReferencia='
					+ codigoBarrasOuNomeOuReferencia;
			}.bind(this),
			getValue: 'nomeProduto',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: template.bind(this)
			},
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		};

		this.codigoBarrasOuNomeOuReferenciaInput.easyAutocomplete(options);
	}

	function onItemSelecionado() {
		this.emitter.trigger('item-selecionado', this.codigoBarrasOuNomeOuReferenciaInput.getSelectedItemData());
		this.codigoBarrasOuNomeOuReferenciaInput.val('');
		this.codigoBarrasOuNomeOuReferenciaInput.focus();
	}

	function template(nomeProduto, produto) {
		produto.valorFormatado = SGE.formatarMoeda(produto.custoVenda);
		return this.template(produto);
	}

	return Autocomplete

}());