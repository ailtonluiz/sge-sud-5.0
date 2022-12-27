var SGE = SGE || {};

SGE.GraficoVendaPorMes = (function() {

	function GraficoVendaPorMes() {
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');
	}

	GraficoVendaPorMes.prototype.iniciar = function() {
		$.ajax({
			url: 'vendas/totalPorMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}

	function onDadosRecebidos(vendaMes) {
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj) {
			meses.unshift(obj.mes);
			valores.unshift(obj.total);
		});

		var graficoVendasPorMes = new Chart(this.ctx, {
			type: 'bar',
			data: {
				labels: meses,
				datasets: [{
					label: 'Salidas por mes',
					backgroundColor: "rgba(26,179,148,0.5)",
					pointBorderColor: "rgba(26,179,148,1)",
					pointBackgroundColor: "#fff",
					data: valores
				}]
			},
		});
	}

	return GraficoVendaPorMes;

}());


$(function() {
	var graficoVendaPorMes = new SGE.GraficoVendaPorMes();
	graficoVendaPorMes.iniciar();

});