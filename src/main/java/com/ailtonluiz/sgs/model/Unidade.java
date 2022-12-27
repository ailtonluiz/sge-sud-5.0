package com.ailtonluiz.sgs.model;

public enum Unidade {

	UND("UND"), KG("KG"),  PAQ("PAQ"), CJ("CJ"), PACK("PACK");

	private String desc;

	Unidade(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
