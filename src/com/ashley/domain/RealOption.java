package com.ashley.domain;

public class RealOption {

	private double gainOrLoss;
	private double salesLevel;
	
	
	public RealOption(double cost, double salesLevel) {
		super();
		this.gainOrLoss = cost;
		this.salesLevel = salesLevel;
	}


	public double getGainOrLoss() {
		return gainOrLoss;
	}


	public void setCost(double cost) {
		this.gainOrLoss = cost;
	}


	public double getSalesLevel() {
		return salesLevel;
	}


	public void setSalesLevel(double salesLevel) {
		this.salesLevel = salesLevel;
	}
	

}
