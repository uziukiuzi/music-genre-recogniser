/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.musicplayer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;
import math.jwave.Transform;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.daubechies.Daubechies4;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.util.FastMath;

import com.musicplayer.MusicLibrary.Genre;
import com.musicplayer.TPE.ExecutorCallback;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
//import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

/**
 * 
 * @author taehwan
 *
 */
public class AudioDecoderThread {
	private static final int TIMEOUT_US = 1000;
	private static final long WINDOW_START = 10000000;
	private final AudioCallback mAudioCallback;
	private boolean eosReceived = false;
	
	
	ArrayList<Uri> mContentUris;
	ArrayList<Genre> mClassLabels;
	private final Context mContext;
	
	private ExecutorCallback mExecutorCallback;
	
	private static final int NUM_CHUNKS = 150;
	private static final double ROLLOFF_PROPORTIONAL_ERROR = 0.001;
	private static final double ROLLOFF_CONSTANT = 0.85;
	private static final int DOWN_FACTOR = 48;
	private static final double ALPHA = 0.99;
	
	
	public AudioDecoderThread(AudioCallback ac, Context context, ExecutorCallback callback){
		mAudioCallback = ac;
		mContext = context;
		mExecutorCallback = callback;
	}
	
	
	
	/**
	 * 
	 * @param filePath
	 */
	public void startPlay(ArrayList<Uri> contentUris, ArrayList<Genre> classLabels) {
		mContentUris = contentUris;
		mClassLabels = classLabels;
		
		ThreadManager threadManager = ThreadManager.sInstance;
		threadManager.setExecutorCallback(mExecutorCallback);
		for(int i = 0; i < mContentUris.size(); i++){
		threadManager.addTask(new ProcessTrackRunnable(mContentUris.get(i), mClassLabels.get(i), mContext));
		}
	}
	


	/**
	 * The code profile, Sample rate, channel Count is used to
	 * produce the AAC Codec SpecificData.
	 * Android 4.4.2/frameworks/av/media/libstagefright/avc_utils.cpp refer
	 * to the portion of the code written.
	 * 
	 * MPEG-4 Audio refer : http://wiki.multimedia.cx/index.php?title=MPEG-4_Audio#Audio_Specific_Config
	 * 
	 * @param audioProfile is MPEG-4 Audio Object Types
	 * @param sampleRate
	 * @param channelConfig
	 * @return MediaFormat
	 */
//	private MediaFormat makeAACCodecSpecificData(int audioProfile, int sampleRate, int channelConfig) {
//		MediaFormat format = new MediaFormat();
//		format.setString(MediaFormat.KEY_MIME, "audio/mp4a-latm");
//		format.setInteger(MediaFormat.KEY_SAMPLE_RATE, sampleRate);
//		format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channelConfig);
//		
//	    int samplingFreq[] = {
//	        96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050,
//	        16000, 12000, 11025, 8000
//	    };
//	    
//	    // Search the Sampling Frequencies
//	    int sampleIndex = -1;
//	    for (int i = 0; i < samplingFreq.length; ++i) {
//	    	if (samplingFreq[i] == sampleRate) {
//	    		Log.d("TAG", "kSamplingFreq " + samplingFreq[i] + " i : " + i);
//	    		sampleIndex = i;
//	    	}
//	    }
//	    
//	    if (sampleIndex == -1) {
//	    	return null;
//	    }
//	    
//		ByteBuffer csd = ByteBuffer.allocate(2);
//		csd.put((byte) ((audioProfile << 3) | (sampleIndex >> 1)));
//		
//		csd.position(1);
//		csd.put((byte) ((byte) ((sampleIndex << 7) & 0x80) | (channelConfig << 3)));
//		csd.flip();
//		format.setByteBuffer("csd-0", csd); // add csd-0
//		
//		for (int k = 0; k < csd.capacity(); ++k) {
//			Log.e("TAG", "csd : " + csd.array()[k]);
//		}
//		
//		return format;
//	}
	
//	Runnable AACDecoderAndPlayRunnable = new Runnable() {
//		
//		
//		
//		@Override
//		public void run() {
//			Log.v("", "Start of run");
//			AACDecoderAndPlay();
//			Log.v("", "End of run");
//		}
//	};

	/**
	 * After decoding AAC, Play using Audio Track.
	 * 
	 */
	
	public void processTrack(Uri syncContentUri, final Genre classLabel, Context context, ProcessTrackRunnable lock) {
		
		// INITIALISE EXTRACTOR AND DECODER
		Log.v("", "Break Point 1");
		
	MediaExtractor extractor = new MediaExtractor();
	int sampleRate = 0;
	Uri contentUri = null;
	synchronized(lock){
	contentUri = syncContentUri;
	}
		try {
			extractor.setDataSource(context, contentUri, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int channel = 0;
		
		for (int i = 0; i < extractor.getTrackCount(); i++) {
			MediaFormat format = extractor.getTrackFormat(i);
			String mime = format.getString(MediaFormat.KEY_MIME);
			if (mime.startsWith("audio/")) {
				extractor.selectTrack(i);
				Log.d("", "format : " + format);
//				ByteBuffer csd = format.getByteBuffer("csd-0");
//				if(csd == null){
//				Log.v("", "csd is null");
//				} else{
//					Log.v("", "csd is not null");
//				}
//				for (int k = 0; k < csd.capacity(); ++k) {
//					Log.v("", "inside for loop 1");
//					Log.e("TAG", "csd : " + csd.array()[k]);
//				}
				sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
				channel = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
				break;
			}
		}
//		MediaFormat format = makeAACCodecSpecificData(MediaCodecInfo.CodecProfileLevel.AACObjectLC, mSampleRate, channel);
//		if (format == null)
//			return;
		int countt = 0;
		boolean found = false;
		MediaFormat format = null;
		String mime = null;
		
		while(countt < extractor.getTrackCount() && !found){
			format = extractor.getTrackFormat(countt);
			mime = format.getString(MediaFormat.KEY_MIME);
			sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
			if (mime.startsWith("audio/")) {
				found = true;
			}
			countt++;
		}
		//format = mExtractor.getTrackFormat(count);
		//MediaCodecInfo codec = selectCodec(mime);
		//String name = codec.getName();
		MediaCodec decoder = MediaCodec.createDecoderByType(mime);
		
		//mDecoder = MediaCodec.createDecoderByType("audio/mp4a-latm");
		decoder.configure(format, null, null, 0);

		if (decoder == null) {
			Log.e("DecodeActivity", "Can't find video info!");
			return;
		}

		decoder.start();
		
		Log.v("", "Break Point 2");	
		
		
		
		
		// Get decoded bytes
		
		ByteBuffer[] inputBuffers = decoder.getInputBuffers();
		ByteBuffer[] outputBuffers = decoder.getOutputBuffers();
		
		BufferInfo info = new BufferInfo();
		
//		int buffsize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
//        // create an audiotrack object
//		AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
//                AudioFormat.CHANNEL_OUT_STEREO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                buffsize,
//                AudioTrack.MODE_STREAM);
//		audioTrack.play();
		
		extractor.seekTo(WINDOW_START, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
		
		long start = SystemClock.elapsedRealtimeNanos();
		
		
		Log.v("", "Break Point 3");	
		
		
		
		// MUSICAL SURFACE FEATURES
		
		double[] flux = new double[NUM_CHUNKS];
		double[] zeroCrossings = new double[NUM_CHUNKS];
		double[] centroid = new double[NUM_CHUNKS];
		int[] rolloff = new int[NUM_CHUNKS];
		double[] rolloffFreq = new double[NUM_CHUNKS];
		double lowEnergy = 0.0;
		
		// Means across all chunks
		double fluxMean = 0.0;
		double zeroCrossingsMean = 0;
		double centroidMean = 0.0;
		double rolloffMean = 0;
		
		// Standard deviations across all chunks
		double fluxStdDeviation = 0.0;
		double zeroCrossingsStdDeviation = 0;
		double centroidStdDeviation = 0.0;
		double rolloffStdDeviation = 0;
		
		
		// Initialise some variables to use while iterating
		double[] fftSums = new double[NUM_CHUNKS];
		int iter = 0;
		int count = 0;
		FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
		double po2 = 0.0;
		Complex[] input = null;
		Complex[] output = null;
		Complex[] previousOutput = null;
		Complex[] temp = null;
		double frequency = 0.0;
		double centroidNum = 0.0;
		double centroidDen = 0.0;
		double fftValue = 0.0;
		double fftPrevious = 0.0;
		double fluxSquared = 0.0;
		int r = 0;
		boolean foundRolloff = false;
		double sum = 0;
		ArrayList<Double> data = new ArrayList<Double>();
		ArrayList<Double> currentChunk = new ArrayList<Double>();
		int gap = 0;
		int tempCount = 0;
		byte[] chunk = null;
		ArrayList<Double> outputExample = new ArrayList<Double>();
		double normConst = 0.0;
		
		// Iterate through the chunks
		Log.v("", "count: " + String.valueOf(count));	
		while (!eosReceived && count < NUM_CHUNKS) {
			Log.v("", "Break Point " + String.valueOf(count + 4));
			Log.v("", "Inside While Loop Break Point 1");
			if(count == 0){
			//	Log.v("", "Timestamp of chunk 0: " + String.valueOf(extractor.getSampleTime()));
			}
			
			int inIndex = decoder.dequeueInputBuffer(TIMEOUT_US);
			if (inIndex >= 0) {
				ByteBuffer buffer = inputBuffers[inIndex];
				int sampleSize = extractor.readSampleData(buffer, 0);
				if (sampleSize < 0) {
					// We shouldn't stop the playback at this point, just pass the EOS
					// flag to mDecoder, we will get it again from the
					// dequeueOutputBuffer
					//Log.d("DecodeActivity", "InputBuffer BUFFER_FLAG_END_OF_STREAM");
					decoder.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
					
				} else {
					decoder.queueInputBuffer(inIndex, 0, sampleSize, extractor.getSampleTime(), 0);
					extractor.advance();
				}

				int outIndex = decoder.dequeueOutputBuffer(info, TIMEOUT_US);
				Log.v("", "Inside While Loop Break Point 2");
				switch (outIndex) {
				case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
					Log.d("DecodeActivity", "INFO_OUTPUT_BUFFERS_CHANGED");
					outputBuffers = decoder.getOutputBuffers();
					break;
					
				case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
					MediaFormat mediaFormat = decoder.getOutputFormat();
					Log.d("DecodeActivity", "New format " + mediaFormat);
				//	audioTrack.setPlaybackRate(mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE));
					
					break;
				case MediaCodec.INFO_TRY_AGAIN_LATER:
					Log.d("DecodeActivity", "dequeueOutputBuffer timed out!");
					break;
					
				default:
					
					Log.v("", "Inside While Loop Break Point 3");
					ByteBuffer outBuffer = outputBuffers[outIndex];
					//Log.v("DecodeActivity", "We can't use this buffer but render it due to the API limit, " + outBuffer);
					
					chunk = new byte[info.size];
					if(chunk.length == 0){
						continue;
					}
					outBuffer.get(chunk); // Read the buffer all at once
					outBuffer.clear(); // ** MUST DO!!! OTHERWISE THE NEXT TIME YOU GET THIS SAME BUFFER BAD THINGS WILL HAPPEN
					
					gap = chunk.length/DOWN_FACTOR;
					currentChunk.clear();
					Log.v("", "Inside While Loop Break Point 4a");
					// ZERO CROSSINGS
					
					int increment = 1;
					if(chunk.length > 1000){
					increment = (int) ((double) chunk.length / ((double) 1000));
					}
					
					// Downsampling
					for(int i = 0; i < chunk.length; i = i + increment){
							data.add((double) chunk[i]);
							currentChunk.add((double) chunk[i]);
							tempCount++;
						
					if(currentChunk.size() > 1){
						iter += FastMath.abs(sign(currentChunk.get(currentChunk.size() - 1)) - sign(currentChunk.get(currentChunk.size() - 2)));
						
					}	
					}
					increment = 0;
					
					tempCount = 0;
					zeroCrossings[count] = 0.5 * iter;
					
					
					
					po2 = FastMath.ceil(FastMath.log(currentChunk.size())/FastMath.log(2));
					input = new Complex[(int) (FastMath.pow(2.0, po2))];
					
					Log.v("", "chunk length: " + chunk.length);
					Log.v("", "input length: " + input.length);
					for(int i = 0; i < input.length; i++){
						if(i < currentChunk.size()){
						input[i] = new Complex((double) currentChunk.get(i));
						} else{
							input[i] = new Complex(0.0);
						}
					}
					
					
					// FFT
					output = transformer.transform(input, TransformType.FORWARD);
						
							outputExample.add(centroidDen);
							
					// CENTROID AND FLUX		
						
						
					for(int i = 0; i < output.length; i++){
						
						if(count > 0){
						fftPrevious = fftValue;
						}
						fftValue = FastMath.hypot(output[i].getReal(), output[i].getImaginary());
						fluxSquared += (fftValue - fftPrevious) * (fftValue - fftPrevious);
						
						centroidNum += i*fftValue;
						centroidDen += fftValue;
						
						
					}
					
//					for(int i = 0; i < output.length; i++){
//						
//						normConst += FastMath.hypot(output[i].getReal(), output[i].getImaginary()) *
//								FastMath.hypot(output[i].getReal(), output[i].getImaginary());
//						
//						
//					}
					
//					fluxSquared = fluxSquared / normConst;
					flux[count] = FastMath.sqrt(fluxSquared) / 1000.0;
					
					// ROLLOFF
					
					while(!foundRolloff && r < output.length - 1){
						r++;
						sum += FastMath.hypot(output[r].getReal(), output[r].getImaginary());
						foundRolloff = checkRolloff(ROLLOFF_PROPORTIONAL_ERROR, sum, centroidDen);
					}
					
					fftSums[count] = centroidDen;
					if(centroidDen != 0.0){
					centroid[count] = centroidNum/centroidDen;
					} else{
						centroid[count] = 0.0;
					}
					rolloff[count] = r;
					
					iter = 0;
					fluxSquared = 0.0;
					centroidNum = 0.0;
					centroidDen = 0.0;
					r = 0;
					sum = 0.0;
					foundRolloff = false;
					count++;
					//audioTrack.write(chunk, info.offset, info.offset + info.size); // AudioTrack write data
					decoder.releaseOutputBuffer(outIndex, false);
					
					break;
				}
				
				// All decoded frames have been rendered, we can stop playing now
				if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
					Log.d("DecodeActivity", "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
					break;
				}
				
				if(count > 0){
					previousOutput = output;
					output = null;
				}
			}
			if(count == NUM_CHUNKS){
		//	Log.v("", "Timestamp of last chunk: " + String.valueOf(extractor.getSampleTime()));
			decoder.stop();
			decoder.release();
			extractor.release();
			}
			
		} // while loop
		
					
					currentChunk.clear();
					currentChunk = null;

		
//		for(int i = 0; i < centroid.length; i++){
//		Log.v("", "centroid: " + String.valueOf(centroid[i]));
//		}
		double energySum = 0.0;
		double energyAverage = 0.0;
		int lowEnergyCount = 0;
		
		
		for(int i = 0; i < NUM_CHUNKS; i++){
			energySum += fftSums[i];
		}
		
		energyAverage = energySum/NUM_CHUNKS;
		for(int i = 0; i < NUM_CHUNKS; i++){
			if(fftSums[i] < energyAverage){
				lowEnergyCount++;
			}
		}
		
		lowEnergy = 100.0 * (((double) lowEnergyCount) / ((double) NUM_CHUNKS));
		
		
		
		// Work out the means and standard deviations
		
		
		
		for(int i = 0; i < NUM_CHUNKS; i++){
		
		fluxMean += flux[i];
		zeroCrossingsMean += zeroCrossings[i];
		centroidMean += centroid[i];
		rolloffMean += rolloff[i];
		
		}
		
		fluxMean = fluxMean/flux.length;
		zeroCrossingsMean = zeroCrossingsMean/zeroCrossings.length;
		centroidMean = centroidMean/centroid.length;
		rolloffMean = rolloffMean/rolloff.length;
		
		
	
		
		for(int i = 0; i < NUM_CHUNKS; i++){
			
		fluxStdDeviation += (flux[i] - fluxMean) * (flux[i] - fluxMean);	
		zeroCrossingsStdDeviation += (zeroCrossings[i] - zeroCrossingsMean) * (zeroCrossings[i] - zeroCrossingsMean);
		centroidStdDeviation += (centroid[i] - centroidMean) * (centroid[i] - centroidMean);
		rolloffStdDeviation += (rolloff[i] - rolloffMean) * (rolloff[i] - rolloffMean);	
			
		}
		
		fluxStdDeviation = Math.sqrt(fluxStdDeviation/flux.length);
		zeroCrossingsStdDeviation = Math.sqrt(zeroCrossingsStdDeviation/zeroCrossings.length);
		centroidStdDeviation = Math.sqrt(centroidStdDeviation/centroid.length);
		rolloffStdDeviation = Math.sqrt(rolloffStdDeviation/rolloff.length);
		
		Log.v("", "fluxMean: " + String.valueOf(fluxMean));
		Log.v("", "zeroCrossingsMean: " + String.valueOf(zeroCrossingsMean));
		Log.v("", "centroidMean: " + String.valueOf(centroidMean));
		Log.v("", "rolloffMean: " + String.valueOf(rolloffMean));
		
		Log.v("", "fluxStdDeviation: " + String.valueOf(fluxStdDeviation));
		Log.v("", "zeroCrossingsStdDeviation: " + String.valueOf(zeroCrossingsStdDeviation));
		Log.v("", "centroidStdDeviation: " + String.valueOf(centroidStdDeviation));
		Log.v("", "rolloffStdDeviation: " + String.valueOf(rolloffStdDeviation));
		
		Log.v("", "lowEnergy: " + String.valueOf(lowEnergy));
		
		Log.v("", "data size: " + String.valueOf(data.size()));
		
		
		
		// BEAT ANALYSIS
		
		Transform t = new Transform( new FastWaveletTransform( new Daubechies4( ) ) );

		double[] dataArray = new double[data.size()];
		for(int i = 0; i < data.size(); i++){
			dataArray[i] = data.get(i);
		}
		data.clear();
		data = null;
		
		double powerOf2 = FastMath.ceil(FastMath.log(chunk.length)/FastMath.log(2));
		double[] dataArrayPo2 = Arrays.copyOf(dataArray, (int) (FastMath.pow(2.0, powerOf2)));
		dataArray = null;
		
		double[] dataCurrentInputArray = null;
		double[] dataCurrentOutputArray = null;
		double[] dataCumulativeArray = new double[dataArrayPo2.length];
		for(int i = 0; i < dataCumulativeArray.length; i++){
			dataCumulativeArray[i] = 0.0;
		}
		double temp1 = 0.0;
		double temp2 = 0.0;
		ArrayList<Double> tempList = new ArrayList<Double>();
		int k = 16; // Downsampling factor
		int tempCount1 = 0;
		double mean = 0.0;
		for(int level = 0; level < (int) FastMath.log(2.0, dataArrayPo2.length); level++){
			
			dataCurrentInputArray = t.forward(dataArrayPo2, level);
			dataCurrentOutputArray = dataCurrentInputArray;
			dataCurrentOutputArray[0] = 0.0;
			for(int i = 1; i < dataCurrentOutputArray.length; i++){
				temp1 = FastMath.abs(dataCurrentInputArray[i]); // Full-wave rectification
				dataCurrentOutputArray[i] = (1.0 - ALPHA) * temp1 - ALPHA * dataCurrentOutputArray[i - 1]; // Low-pass filtering
			}
			tempCount1 = 0;
			mean = 0.0;
			while(k * tempCount1 < dataCurrentOutputArray.length){
				tempList.add(dataCurrentOutputArray[k * tempCount1]); // Downsampling by k
				mean += dataCurrentOutputArray[k * tempCount1];
				tempCount1++;
			}
			mean = mean / dataCurrentOutputArray.length;
			
			tempCount1 = 0;
			while(k * tempCount1 < dataCurrentOutputArray.length){
				dataCumulativeArray[k * tempCount1] += tempList.get(tempCount1) - mean; // Mean removal
				tempCount1++;
			}
			
			
		}
		int N = dataCumulativeArray.length;
		ArrayList<Double> dataList = new ArrayList<Double>();
		double dataElement = 0.0;
		
		for(int i = 0; i < N; i++){
			if(dataCumulativeArray[i] != 0.0){
				dataElement = autocorrelate(i, N, dataCumulativeArray);
				dataList.add(dataElement);
				Log.v("", "dataList: " + String.valueOf(dataElement));
			}
		}
		
		PeakDetector peakDetector = new PeakDetector(dataList);
		int[] peakIndices = peakDetector.process(5, 2);
		HashSet<Integer> hs = new HashSet<Integer>();
		for(int i = 0; i < peakIndices.length; i++){
			hs.add(peakIndices[i]);
		}
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		ArrayList<Double> valuesList = new ArrayList<Double>();
		
		
		
		indicesList.addAll(hs);
		Double tempDoub = 0.0;
		
		HashMap<Double, Integer> hm = new HashMap<Double, Integer>();
		for(int i = 0; i < indicesList.size(); i++){
			tempDoub = dataList.get(indicesList.get(i));
			hm.put(tempDoub, indicesList.get(i));
		}
		
		indicesList.clear();
		valuesList.clear();
		
		Entry<Double, Integer> tempEntry = null;
		Iterator<Entry<Double, Integer>> it = hm.entrySet().iterator();
		while(it.hasNext()){
			tempEntry = (Entry<Double, Integer>) it.next();
			if(tempEntry.getValue() < 75){
				it.remove();
			} else{
				//indicesList.add(tempEntry.getValue());
				valuesList.add(tempEntry.getKey());
			}
		}
		
		
		
		Collections.sort(valuesList);
		for(int i = 0; i < valuesList.size(); i++){
			indicesList.add(hm.get(valuesList.get(i)));
		}
		
		
		double valuesSum = 0.0;
		double histogramSum = 0.0;
		
		double beatStrength = 0.0;
		double P1 = 0.0;
		double P2 = 0.0;
		double A1 = 0.0;
		double A2 = 0.0;
		double RA = 0.0;
		
		for(int i = 0; i < dataList.size(); i++){
			histogramSum += dataList.get(i);
		}
		
		for(int i = 0; i < valuesList.size(); i++){
			valuesSum += valuesList.get(i);
		}
		
//		if(histogramSum != 0.0 && valuesList.size() != 0){
//			SUM = (1000.0 * valuesSum) / (histogramSum * valuesList.size());
//		}
		if(valuesList.size() != 0){
		beatStrength = valuesSum / valuesList.size();
		}
		
		if(indicesList.size() > 0){
			
		// Set P1 as the largest peak
		P1 = (double) indicesList.get(indicesList.size() - 1);

		
		}
		
		if(indicesList.size() > 1){
			int beatCount = indicesList.size() - 2;
			boolean beatFound = false;
			
			// Start with P2 as the second largest peak
			P2 = (double) indicesList.get(indicesList.size() - 2);
			double diff = 0;
			
			// Iterate backwards through the peaks, largest to smallest
			while(!beatFound && beatCount > -1){
				diff = ((double) indicesList.get(beatCount)) - P1;
				
				if(FastMath.abs(diff) / P1 > 0.3){
					// Set P2 as the period of the first peak that is reasonably different from P1
					P2 = (double) indicesList.get(beatCount);
					beatFound = true;
				}
				beatCount--;
			}
		}
		
		if(indicesList.size() > 0){
			
			A1 = FastMath.abs(dataList.get((int) P1)) / histogramSum;
			if(P2 != 0.0){
			A2 = FastMath.abs(dataList.get((int) P2)) / histogramSum;
			}
			
			if(A1 != 0.0){
				RA = A2 / A1;
			
			}
		}
		
		
		for(int i = 0; i < valuesList.size(); i++){
			Log.v("", String.valueOf(i) + ") valuesList: " + String.valueOf(valuesList.get(i)));
		}
		Log.v("", "P1: " + String.valueOf(P1));
		Log.v("", "P2: " + String.valueOf(P2));
		Log.v("", "A1: " + String.valueOf(A1));
		Log.v("", "A2: " + String.valueOf(A2));
		Log.v("", "RA: " + String.valueOf(RA));
		Log.v("", "SUM: " + String.valueOf(histogramSum));
		Log.v("", "Number of Peaks: " + String.valueOf(valuesList.size()));
		double[] result = {fluxMean, zeroCrossingsMean, centroidMean, rolloffMean,
				fluxStdDeviation, zeroCrossingsStdDeviation, centroidStdDeviation,
				rolloffStdDeviation, lowEnergy, P1, P2, A1, A2, RA, histogramSum, valuesList.size()};
		final DenseInstance denseInstance = new DenseInstance(result);
	if(P1 + P2 + A1 + A2 + RA != 0.0){
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new ReturnResultsRunnable(lock, mAudioCallback, denseInstance, classLabel));
		
	} else{
		Log.v("", "Track could not be classified!");
	}
		
//		for(int i = 0; i < dataList.size(); i++){
//			Log.v("", String.valueOf(i) + ") autocorrelation: " + String.valueOf(dataList.get(i)));
//			histogramSum += dataList.get(i);
//		}
//		Log.v("", "indicesList size: " + String.valueOf(indicesList.size()));
//		for(int i = 0; i < valuesList.size(); i++){
//			Log.v("", "indicesList: " + String.valueOf(indicesList.get(i)) + ", value: " + String.valueOf(valuesList.get(i)));
//			valuesSum += valuesList.get(i);
//		}
//Classifier c = new KNearestNeighbors(5);

		
		
//		double A0 = valuesList.get(valuesList.size() - 1) / valuesSum;
//		double A1 = valuesList.get(valuesList.size() - 2) / valuesSum;
//		double RA = A1 / A0;
//		double P0 = 1 / ((double) indicesList.get(indicesList.size() - 1));
//		double P1 = 1 / ((double) indicesList.get(indicesList.size() - 2));
//		
//		Log.v("", "A0: " + String.valueOf(A0));
//		Log.v("", "A1: " + String.valueOf(A1));
//		Log.v("", "RA: " + String.valueOf(RA));
//		Log.v("", "P0: " + String.valueOf(P0));
//		Log.v("", "P1: " + String.valueOf(P1));
//		Log.v("", "SUM: " + String.valueOf(histogramSum));
		


		
		long durationUs = SystemClock.elapsedRealtimeNanos() - start;
		double durationSecs = ((double) durationUs)/1000000000.0;
		Log.v("", "count = " + String.valueOf(count) + ", Sample rate: " + String.valueOf(sampleRate) + ", Duration: " + String.valueOf(durationSecs));
		
		
		
		
//		audioTrack.stop();
//		audioTrack.release();
//		audioTrack = null;
	}

	private double sign(double x) {
		// TODO Auto-generated method stub
		return (FastMath.signum(x) / 2.0) + 0.5;
	}

	private double autocorrelate(int k, int N, double[] data) {
		// TODO Auto-generated method stub
		double sum = 0.0;
		
		for(int i = k; i < N; i++){
			sum += data[i] * data[i - k];
		}
		
		sum = sum / N;
		
		return sum;
	}

	private boolean checkRolloff(double rolloffProportionalError, double sum1, double sum2) {
		// TODO Auto-generated method stub
	//	return Math.abs(sum1 - ROLLOFF_CONSTANT * sum2) < rolloffProportionalError * sum2;
		return sum1 > ROLLOFF_CONSTANT * sum2;
	}


	
	public interface AudioCallback{
		public void onFeaturesFound(DenseInstance result, final Genre classLabel);
	}
	
	private class ReturnResultsRunnable implements Runnable{

		private final ProcessTrackRunnable mLock;
		private final AudioCallback mAudioCallback;
		private final DenseInstance mDenseInstance;
		private final Genre mClassLabel;
		
		public ReturnResultsRunnable(ProcessTrackRunnable lock, AudioCallback audioCallback,
				DenseInstance denseInstance, Genre classLabel){
			mLock = lock;
			mAudioCallback = audioCallback;
			mDenseInstance = denseInstance;
			mClassLabel = classLabel;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(mLock){
				mAudioCallback.onFeaturesFound(mDenseInstance, mClassLabel);
			}
		}
		
	}

	
	private class ProcessTrackRunnable implements Runnable{

		
		private final Uri mRunnableContentUri;
		private final Genre mRunnableClassLabel;
		private final Context mRunnableContext;
		
		
		
		public ProcessTrackRunnable(Uri contentUri, Genre classLabel, Context context){
			synchronized(this){
			mRunnableContentUri = contentUri;
			mRunnableClassLabel = classLabel;
			mRunnableContext = context;
			}
			
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			processTrack(mRunnableContentUri, mRunnableClassLabel, mRunnableContext, this);
		}
		
	}


	public void stop() {
		// TODO Auto-generated method stub
		eosReceived = true;
	}

}