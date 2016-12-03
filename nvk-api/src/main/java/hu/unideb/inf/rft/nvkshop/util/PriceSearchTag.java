package hu.unideb.inf.rft.nvkshop.util;

public class PriceSearchTag {

	private Double lowerLimit;

	private Double upperLimit;

	public PriceSearchTag(Double lowerLimit, Double upperLimit) {
		super();
		this.lowerLimit = lowerLimit==null?0D:lowerLimit;
		this.upperLimit = upperLimit==null?Double.MAX_VALUE:upperLimit;
	}

	public PriceSearchTag() {
		super();
		lowerLimit = 0D;
		upperLimit = Double.MAX_VALUE;
	}

	public Double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Double upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	

}
