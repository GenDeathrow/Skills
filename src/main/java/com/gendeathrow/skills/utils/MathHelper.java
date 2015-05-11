package com.gendeathrow.skills.utils;

import java.math.BigDecimal;

import com.ibm.icu.text.DecimalFormat;

public class MathHelper 
{

	public static double RoundTo2Decimals(double val) 
	{
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
	}
	
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
