package com.musicplayer;

import java.util.ArrayList;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;

public class SalienceFunctionUtils {
	
	private static final double ALPHA = 1.0;

	public static double salience(Complex[] fftData, Complex[] previousData, int[] peakIndices, int queryBin,
			int numHarmonics, double magCompression, double samplingRate){
		
		double sum = 0.0;
		
		for(int h = 0; h < numHarmonics; h++){
			for(int i = 0; i < peakIndices.length; i++){
				sum += weighting(queryBin, h, findFrequency(fftData, previousData, i, samplingRate))
						* FastMath.pow(FastMath.hypot(fftData[i].getReal(), fftData[i].getImaginary()), magCompression);
			}
		}
		
		return sum;
	}

	private static double weighting(int queryBin, int h, double frequency) {
		// TODO Auto-generated method stub
		double delta = FastMath.abs(findBin(frequency / h) - queryBin);
		if(delta <= 1){
		return FastMath.cos(delta * (FastMath.PI / 2.0)) * FastMath.cos(delta * (FastMath.PI / 2.0))
				* FastMath.pow(ALPHA, h - 1);
		} else{
			return 0.0;
		}
	}

	private static double findFrequency(Complex[] fftData, Complex[] previousData, int i, double samplingRate) {
		// TODO Auto-generated method stub
		return (i + binOffset(i, fftData, previousData)) * (samplingRate / fftData.length);
	}

	private static int binOffset(int i, Complex[] fftData, Complex[] previousData) {
		// TODO Auto-generated method stub
		double temp = FastMath.atan2(fftData[i].getReal(), fftData[i].getImaginary())
						- FastMath.atan2(previousData[i].getReal(), previousData[i].getImaginary())
						- ((2 * FastMath.PI) / fftData.length) * i;
		return (int) ((fftData.length / (2 * FastMath.PI)) * principleArgument(temp));
	}

	private static double principleArgument(double in) {
		// TODO Auto-generated method stub
		double out = in;
		out = out % (2 * FastMath.PI);
		if(out < FastMath.PI){
			return out;
		} else if(out > FastMath.PI){
			return 2 * FastMath.PI - out;
		}
		return 0;
	}
	
	private static double findBin(double frequency) {
		// TODO Auto-generated method stub
		return FastMath.floor((120 * FastMath.log(2.0, frequency / 55.0)) + 1.0);
	}
	
	
}
