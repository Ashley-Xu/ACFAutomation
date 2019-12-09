package com.ashley.main;

import com.ashley.domain.Cube;
import com.ashley.domain.RealOption;
import com.ashley.domain.UnderlyingTree;
import com.ashley.domain.UserParameters;
import com.ashley.util.CubeHelper;

public class CubeProcessor {

	private static UserParameters userParams;
	
	public static void main(String[] args) {
		
		userParams = getUserParams();
		UnderlyingTree assetPrices = new UnderlyingTree(2, 2, 1600, 0.36);
		Cube cube = new Cube(userParams, assetPrices);
		
		cube.startProcessing();
		
		CubeHelper.nicePrint2DArray("assetPrices", assetPrices.getTree());
	}
	
	private static UserParameters getUserParams() {
		//At some point we gonna ask the user
		RealOption oi0 = new RealOption(0, 10000);
		RealOption oi1 = new RealOption(-250000, 20000);
		RealOption oi2 = new RealOption(0, 0);
		RealOption[] oIa = new RealOption[3];
		oIa[0] = oi0;
		oIa[1] = oi1;
		oIa[2] = oi2;
		
		//N, t, PoC_0, sigma, rf, PoP_0, AoC, CoO, CS, OI[], options
		//N is including the current period
		//OptionNum is one less than total (excluding current level)
		return new UserParameters(2, 2, 1600,0.36, 0.05, 350, 0.15, 100, oIa);
	}
	
	
}
