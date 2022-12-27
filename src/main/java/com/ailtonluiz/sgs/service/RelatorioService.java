package com.ailtonluiz.sgs.service;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailtonluiz.sgs.dto.FiltrosRelatorio;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;

	public byte[] gerarRelatorioVendasEmitidas(FiltrosRelatorio filtrosRelatorio) throws Exception {
		Date dataInicio = Date.from(LocalDateTime.of(filtrosRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(filtrosRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		parametros.put("status", filtrosRelatorio.getStatusVenda());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_vendas_emitidas.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioEntrada(FiltrosRelatorio filtrosRelatorio) throws Exception {
		Date dataInicio = Date.from(LocalDateTime.of(filtrosRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(filtrosRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		parametros.put("status", filtrosRelatorio.getStatusCompra());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_entradas.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioEstoquesGeralSemEstoque(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass()
				.getResourceAsStream("/relatorios/relatorio_estoque_geral_sem_estoque.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

//Relatório de produtos não apto para consumo
	public byte[] gerarRelatorioEstoquesPendentes(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");

		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_estoque_pendente.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioEstoquesGrupoMarca(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("codigo_grupo", filtrosRelatorio.getGrupoProdutos().getCodigo());
		parametros.put("stock", filtrosRelatorio.isStock());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_estoque_por_grupo.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioEstoquesGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("stock", filtrosRelatorio.isStock());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_estoque.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioEstoqueTabaco(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());
		parametros.put("stock", filtrosRelatorio.isStock());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_tabaco.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioCliente(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_clientes.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioFornecedor(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_fornecedor.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioUsuario(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_usuario.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioSugestaoCompra(FiltrosRelatorio filtrosRelatorio) throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("codigo_grupo", filtrosRelatorio.getGrupoProdutos().getCodigo());
		parametros.put("ativo", filtrosRelatorio.isAtivo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_sugestao_estoque.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}
	
	public byte[] gerarRelatorioSugestaoCompraGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("ativo", filtrosRelatorio.isAtivo());
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_sugestao_estoque_geral.jasper");
		
		Connection con = this.dataSource.getConnection();
		
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioSaidaProduto(FiltrosRelatorio filtrosRelatorio) throws Exception {
		Date dataInicio = Date.from(LocalDateTime.of(filtrosRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(filtrosRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		parametros.put("codigo_grupo", filtrosRelatorio.getGrupoProdutos().getCodigo());

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_saida_produto.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

	public byte[] gerarRelatorioSaidaProdutoGeral(FiltrosRelatorio filtrosRelatorio) throws Exception {
		Date dataInicio = Date.from(LocalDateTime.of(filtrosRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(filtrosRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		parametros.put("tabaco", filtrosRelatorio.isTabaco());

		InputStream inputStream = this.getClass()
				.getResourceAsStream("/relatorios/relatorio_saida_produto_geral.jasper");

		Connection con = this.dataSource.getConnection();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}

}
