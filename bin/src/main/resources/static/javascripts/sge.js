var SGE = SGE || {};

SGE.MaskMoney = (function() {

	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}

	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({
			decimal : ',',
			thousands : '.'
		});
		this.plain.maskMoney({
			precision : 0,
			thousands : '.'
		});
	}

	return MaskMoney;

}());


SGE.MaskPhoneNumber = (function() {

	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}

	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function(val) {
			return val.replace(/\D/g, '').length === 9 ? '(000) 000-000' : '(00) 000-00-00-00';
		};

		var options = {
			onKeyPress : function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}
		};

		this.inputPhoneNumber.mask(maskBehavior, options);
	}

	return MaskPhoneNumber;

}());

SGE.MaskDate = (function() {

	function MaskDate() {
		this.inputDate = $('.js-date');
	}

	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation : 'bottom',
			language : 'es-ES',
			autoclose : true
		});
	}

	return MaskDate;

}());

SGE.Security = (function() {

	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}

	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}

	return Security;

}());

numeral.language('pt-br');

SGE.formatarMoeda = function(valor) {
	numeral.language('pt-br');
	return numeral(valor).format('0,0.00');
}

SGE.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

$(function() {

	var maskMoney = new SGE.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new SGE.MaskPhoneNumber();
	maskPhoneNumber.enable();

	var security = new SGE.Security();
	security.enable();

	var maskDate = new SGE.MaskDate();
	maskDate.enable();

});