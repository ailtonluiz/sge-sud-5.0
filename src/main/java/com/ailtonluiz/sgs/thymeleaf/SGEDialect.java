package com.ailtonluiz.sgs.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.ailtonluiz.sgs.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.ailtonluiz.sgs.thymeleaf.processor.MenuAttributeTagProcessor;
import com.ailtonluiz.sgs.thymeleaf.processor.MessageElementTagProcessor;
import com.ailtonluiz.sgs.thymeleaf.processor.OrderElementTagProcessor;
import com.ailtonluiz.sgs.thymeleaf.processor.PaginationElementTagProcessor;

public class SGEDialect extends AbstractProcessorDialect {

	public SGEDialect() {
		super("Ailton Luiz SGE", "sge", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
