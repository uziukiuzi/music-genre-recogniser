package com.musicplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.tools.data.FileHandler;

import com.musicplayer.AudioDecoderThread.AudioCallback;
import com.musicplayer.MusicLibrary.Genre;
import com.musicplayer.MusicRetriever.Item;
import com.musicplayer.TPE.ExecutorCallback;

import android.app.Activity;
import android.app.Fragment;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AudioDecoderActivity extends Activity{
	
	protected static AudioDecoderThread mAudioDecoder;
	private static Dataset mDataset;
	private static int mTaskCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_decoder);

		if (savedInstanceState == null) {
			
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mAudioDecoder.stop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements AudioCallback, ExecutorCallback{
		//private static final String SAMPLE = Environment.getExternalStorageDirectory() + "/temp.aac";
		private String SAMPLE;
		private MusicRetriever mMusicRetriever;
		private Uri mContentUri;

		public PlaceholderFragment() {
			
		}

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			mAudioDecoder = new AudioDecoderThread(this, getActivity().getApplicationContext(), this);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_audio_decoder, container, false);
			
			
			mMusicRetriever = new MusicRetriever(getActivity().getApplicationContext().getContentResolver());
			mMusicRetriever.prepare();
			//Uri uri = mMusicRetriever.getRandomItem().getURI();
			//Uri uri = mMusicRetriever.getTestItem(0).getURI();
			
			
			//getRealPathFromURI(uri);
			
			
			
			final Button btn = (Button) rootView.findViewById(R.id.play);
			btn.setOnClickListener(new OnClickListener() {
				
				

				@Override
				public void onClick(View v) {
					
					mDataset = new DefaultDataset();
					ArrayList<Uri> uris = new ArrayList<Uri>();
					ArrayList<Genre> classLabels = new ArrayList<Genre>();
					classLabels.add(Genre.arab);
					Item item = null;
					ArrayList<String> titles = MusicLibrary.getTrainingTitles();
//					for(int i = 0; i < 1; i++){
//						item = mMusicRetriever.getItemByTitle(titles.get(i));
//						if(item != null){
//						classLabels.add(MusicLibrary.getGenreByTitleIndex(i));
//						mContentUri = item.getURI();
//						uris.add(mContentUri);
//						}
//					}
					
					titles.clear();
					titles.add(MusicLibrary.getTestTitle());
					Log.v("", "Track: " + titles.get(0) + ", number of titles: " + titles.size());
					item = mMusicRetriever.getItemByTitle(titles.get(0));
					uris.add(item.getURI());
					mTaskCount = uris.size();
					mAudioDecoder.startPlay(uris, classLabels);
				}
			});
			
			
			

			
			
			
			return rootView;
		}

		@Override
		public void onFeaturesFound(DenseInstance result, Genre classLabel) {
			// TODO Auto-generated method stub
			//result.setClassValue(classLabel);
			//mDataset.add(result);
			//Log.v("", "Just done track " + String.valueOf(mDataset.size()));
			Log.v("", "onFeaturesFound");
			try {
				Dataset trainingData = FileHandler.loadDataset(new File(Environment.getExternalStorageDirectory(), "trainingDataUsman1.txt"), 4, ",");
				Classifier knn = new KNearestNeighbors(1);
				knn.buildClassifier(trainingData);
				Log.v("", "check point 1");
				Object predictedClass = knn.classify(result);
				Log.v("", "check point 2");
				if(predictedClass == null){
				Log.v("", "predictedClass is null");
				} else{
					Log.v("", "predicted class: " + predictedClass.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(ThreadManager.sInstance.mThreadPoolExecutor.getQueue().isEmpty()){
				ThreadManager.sInstance.mThreadPoolExecutor.shutdown();
			}
			
		}

		@Override
		public void onTerminated() {
			// TODO Auto-generated method stub
			Log.v("", "Finished!");
			Log.v("", String.valueOf(mDataset));
			
		if(isExternalStorageWritable()){	
			File file = new File(Environment.getExternalStorageDirectory(), "trainingDataUsman2.txt");
			
			try {
				file.createNewFile();
				FileHandler.exportDataset(mDataset, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{
			Log.v("", "External storage isn't writable!!");
		}
	}
		
		
		public boolean isExternalStorageWritable() {
		    String state = Environment.getExternalStorageState();
		    if (Environment.MEDIA_MOUNTED.equals(state)) {
		        return true;
		    }
		    return false;
		}
		
		
	}




	
}
