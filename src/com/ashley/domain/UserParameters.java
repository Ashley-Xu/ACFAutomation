package com.ashley.domain;

public class UserParameters {

	private int n; 
	private int T; 
	private double PoC_0; 
	private double sigma; 
	private double rf; 
	private double PoP_0;
	private double AoC; 
	private double CoO; 
	private double SL; 
	private RealOption[] realOptions;
	//private int options;
	
	public UserParameters(int n, int t, double PoC_0, double sigma, double rf, double PoP_0, double AoC, double CoO, RealOption[] realOptions) { // removed input int options
		super();
		this.n = n;
		this.T = t;
		this.PoC_0 = PoC_0;
		this.sigma = sigma;
		this.rf = rf;
		this.PoP_0 = PoP_0;
		this.AoC = AoC;
		this.CoO = CoO;
		this.realOptions = realOptions;
		//this.options = options;
	}
	
	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}


	public int getT() {
		return T;
	}


	public void setT(int t) {
		T = t;
	}


	public double getPoC_0() {
		return PoC_0;
	}


	public void setPoC_0(double poC_0) {
		PoC_0 = poC_0;
	}


	public double getSigma() {
		return sigma;
	}


	public void setSigma(double sigma) {
		this.sigma = sigma;
	}


	public double getRf() {
		return rf;
	}


	public void setRf(double rf) {
		this.rf = rf;
	}


	public double getPoP_0() {
		return PoP_0;
	}


	public void setPoP_0(double poP_0) {
		PoP_0 = poP_0;
	}


	public double getAoC() {
		return AoC;
	}


	public void setAoC(double aoC) {
		AoC = aoC;
	}


	public double getCoO() {
		return CoO;
	}


	public void setCoO(double coO) {
		CoO = coO;
	}

//
//
//	public int getOptions() {
//		return options;
//	}
//
//
//	public void setOptions(int options) {
//		this.options = options;
//	}

	public RealOption[] getRealOptions() {
		return realOptions;
	}
	
}
