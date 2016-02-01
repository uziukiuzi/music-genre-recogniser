package com.musicplayer;

import java.util.ArrayList;

public class MusicLibrary {

	
	private static ArrayList<String> mTrainingTitles;
	private static ArrayList<Genre> mTrainingGenres;
	
	public static ArrayList<String> getTrainingTitles(){
		mTrainingTitles = new ArrayList<String>();
		
		
		mTrainingTitles.add("-Elena_Siegman__115"); // Metal
		mTrainingTitles.add("I'm not ready to die"); // Metal
		
		mTrainingTitles.add("Ambition Az A Ridah"); // West Hip Hop
		mTrainingTitles.add("All Bout U"); // West Hip Hop
		mTrainingTitles.add("Skandalouz"); // West Hip Hop
		mTrainingTitles.add("Got My Mind Made Up"); // West Hip Hop
		mTrainingTitles.add("How Do U Want It"); // West Hip Hop
		mTrainingTitles.add("I Ain't Mad At Cha"); // West Hip Hop
		mTrainingTitles.add("Can't C Me"); // West Hip Hop
		mTrainingTitles.add("Holla At Me"); // West Hip Hop
		mTrainingTitles.add("All Eyez On Me"); // West Hip Hop
		mTrainingTitles.add("The $20 Sack Pyramid");
		mTrainingTitles.add("Lyrical Gangbang");
		mTrainingTitles.add("The Day the Niggaz Took Over");
		mTrainingTitles.add("Lil' Ghetto Boy");
		mTrainingTitles.add("A Nigga Witta Gun");
		mTrainingTitles.add("Rat-Tat-Tat-Tat");
		mTrainingTitles.add("High Powered");
		
		mTrainingTitles.add("Baarish"); // Pop
		mTrainingTitles.add("Teri Yaad"); // Pop
		mTrainingTitles.add("05-lady_gaga-alejandro");
		mTrainingTitles.add("25-shontelle-impossible");
		mTrainingTitles.add("We Found Love");
		
		mTrainingTitles.add("Blown Away"); // RnB
		mTrainingTitles.add("Smack That");
		mTrainingTitles.add("Never Took the Time");
		mTrainingTitles.add("Mama Africa");
		mTrainingTitles.add("Gangsta Bop");
		mTrainingTitles.add("Show Out");
		mTrainingTitles.add("Marvin Gaye - Sexual Healing");
		mTrainingTitles.add("Just Like Music");
		mTrainingTitles.add("Killing Me Softly With His Song");
		mTrainingTitles.add("Simple Things");
		mTrainingTitles.add("Bad Girl");
		mTrainingTitles.add("Do It to Me");
		mTrainingTitles.add("Truth Hurts");
		mTrainingTitles.add("Say My Name");
		
		mTrainingTitles.add("Uncut Raw"); // East Hip Hop
		mTrainingTitles.add("Gimme Your's");
		mTrainingTitles.add("Ho Happy Jackie");
		mTrainingTitles.add("Rather Unique");
		mTrainingTitles.add("Sugar Hill");
		mTrainingTitles.add("Mo Money, Mo Murder");
		mTrainingTitles.add("Award Tour");
		mTrainingTitles.add("Buggin' Out");
		mTrainingTitles.add("Electric Relaxation");
		mTrainingTitles.add("Hood Took Me Under");
		mTrainingTitles.add("Retaliation");
		mTrainingTitles.add("Memory Lane (Sittin' in da Park)");
		mTrainingTitles.add("N.Y. State of Mind");
		mTrainingTitles.add("One Time 4 Your Mind");
		mTrainingTitles.add("It Ain't Hard to Tell");
		mTrainingTitles.add("I gave you power");
		mTrainingTitles.add("Take it in blood");
		mTrainingTitles.add("The What Feat. Method Man");
		mTrainingTitles.add("Me & My Bitch Feat. Sybil Pennix");
		mTrainingTitles.add("Warning");
		mTrainingTitles.add("Everyday Struggle");
		
		mTrainingTitles.add("Is This Love"); // Reggae
		mTrainingTitles.add("Could You Be Loved");
		mTrainingTitles.add("Three Little Birds");
		mTrainingTitles.add("Buffalo Soldier");
		mTrainingTitles.add("Get Up, Stand Up");
		mTrainingTitles.add("Stir It Up");
		mTrainingTitles.add("One Love/People Get Ready");
		mTrainingTitles.add("I Shot the Sheriff");
		mTrainingTitles.add("Waiting in Vain");
		mTrainingTitles.add("Jamming");
		
		mTrainingTitles.add("What If"); // Rock
		mTrainingTitles.add("White Shadows");
		mTrainingTitles.add("X&Y");
		mTrainingTitles.add("The Hardest Part");
		mTrainingTitles.add("21st Century Breakdown");
		mTrainingTitles.add("Know Your Enemy");
		mTrainingTitles.add("Before The Lobotomy");
		mTrainingTitles.add("Christian's Inferno");
		mTrainingTitles.add("Last Night On Earth");
		mTrainingTitles.add("East Jesus Nowhere");
		mTrainingTitles.add("Last Of American Girls");
		mTrainingTitles.add("Boulevard Of Broken Dreams");
		mTrainingTitles.add("Extraordinary Girl");
		mTrainingTitles.add("Give Me Novocaine");
		mTrainingTitles.add("This Love");
		mTrainingTitles.add("The Sun");
		mTrainingTitles.add("Sunday Morning");
		mTrainingTitles.add("Vienna");
		mTrainingTitles.add("Tangled");
		mTrainingTitles.add("I Don't Wanna Be");
		mTrainingTitles.add("Flagpole Sitta");
		mTrainingTitles.add("Still Waiting");
		
		mTrainingTitles.add("Layali Baadak"); // Arab
		mTrainingTitles.add("Nesswanje");
		mTrainingTitles.add("Addi Eli Kanou");
		mTrainingTitles.add("Batamenak");
		
		mTrainingTitles.add("Speak Low"); // Jazz/Big Band
		mTrainingTitles.add("Fly Me To The Moon (In Other Words)");
		mTrainingTitles.add("New York, New York");
		mTrainingTitles.add("That's Life");
		mTrainingTitles.add("Shipbuilding");
		
		mTrainingTitles.add("No Hook"); // Post-2000 Hip Hop
		mTrainingTitles.add("Sweet");
		mTrainingTitles.add("I Know");
		mTrainingTitles.add("Say Hello");
		mTrainingTitles.add("Success (Feat. Nas)");
		mTrainingTitles.add("Fallin'");
		mTrainingTitles.add("99 Problems (Produced By Rick Rubin)");
		mTrainingTitles.add("Heart of the City (Ain't No Love)");
		mTrainingTitles.add("Can't Tell Me Nothing");
		mTrainingTitles.add("Good Life");
		mTrainingTitles.add("Big Brother");
		mTrainingTitles.add("We Don't Care");
		mTrainingTitles.add("Good Morning");
		mTrainingTitles.add("Through The Wire");
		mTrainingTitles.add("Touch The Sky (Feat. Lupe Fiasco)");
		mTrainingTitles.add("Gold Digger (Feat. Jamie Foxx)");
		mTrainingTitles.add("Just Might Be OK");
		mTrainingTitles.add("Daydreamin'");
		mTrainingTitles.add("Pressure (Original Version)");
		
		mTrainingTitles.add("Madre"); // South American Hip Hop
		mTrainingTitles.add("Atrevido");
		mTrainingTitles.add("A lo cubano");
		mTrainingTitles.add("Barrio");
		mTrainingTitles.add("Mistica");
		mTrainingTitles.add("Bruja");
		mTrainingTitles.add("Camina");
		mTrainingTitles.add("Burron");
		mTrainingTitles.add("Que Quede Claro");
		mTrainingTitles.add("Publico");
		mTrainingTitles.add("la vacuna");
		mTrainingTitles.add("el kilo");
		mTrainingTitles.add("elegante");
		mTrainingTitles.add("Que Pasa");
		mTrainingTitles.add("Mujer");
		mTrainingTitles.add("Emigrantes");
		
		mTrainingTitles.add("Pyar Ho Gaya"); // Punjabi
		mTrainingTitles.add("Assan Janna Mall-O-Mall");
		mTrainingTitles.add("Billo De Ghar");
		mTrainingTitles.add("Naara_Sada_Ishq_Aay");
		mTrainingTitles.add("Preeto");
		
		mTrainingTitles.add("STAMPEDE ( Available November 18 ) - Dimitri Vegas & Like Mike vs DVBBS & Borgeous"); // Dance
		mTrainingTitles.add("Call Me A Spaceman (Radio Edit)");
		mTrainingTitles.add("Animals (Radio Edit)");
		mTrainingTitles.add("Reload - Sebastian Ingrosso & Tommy Trash (Feat. John Martin)");
		mTrainingTitles.add("I Could Be the One - Avicii & Nicky Romero");
		mTrainingTitles.add("White Noise ft. AlunaGeorge - Disclosure");
		
		mTrainingTitles.add("SUN IS SHINING SMOKE OUT DUBSTEP REMIX - Miroslav Kalinec"); // Dubstep
		mTrainingTitles.add("Bangarang (feat. Sirah) - Skrillex");
		
		mTrainingTitles.add("Jolene"); // Country
		
		return mTrainingTitles;
	}
	
	private static ArrayList<Genre> getTrainingGenres(){
		mTrainingGenres = new ArrayList<Genre>();
		
		
		mTrainingGenres.add(Genre.metal);
		mTrainingGenres.add(Genre.metal);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.westCoastHipHop);
		mTrainingGenres.add(Genre.pop);
		mTrainingGenres.add(Genre.pop);
		mTrainingGenres.add(Genre.pop);
		mTrainingGenres.add(Genre.pop);
		mTrainingGenres.add(Genre.pop);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.rnb);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.eastCoastHipHop);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.reggae);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.rock);
		mTrainingGenres.add(Genre.arab);
		mTrainingGenres.add(Genre.arab);
		mTrainingGenres.add(Genre.arab);
		mTrainingGenres.add(Genre.arab);
		mTrainingGenres.add(Genre.jazz);
		mTrainingGenres.add(Genre.jazz);
		mTrainingGenres.add(Genre.jazz);
		mTrainingGenres.add(Genre.jazz);
		mTrainingGenres.add(Genre.jazz);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.post2000HipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.southAmericanHipHop);
		mTrainingGenres.add(Genre.punjabi);
		mTrainingGenres.add(Genre.punjabi);
		mTrainingGenres.add(Genre.punjabi);
		mTrainingGenres.add(Genre.punjabi);
		mTrainingGenres.add(Genre.punjabi);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dance);
		mTrainingGenres.add(Genre.dubstep);
		mTrainingGenres.add(Genre.dubstep);
		mTrainingGenres.add(Genre.country);
		return mTrainingGenres;
	}
	
	public static String getTestTitle(){
		return "Anthony David";
	}
	
	public static Genre getGenreByTitleIndex(int titleIndex){
		return getTrainingGenres().get(titleIndex);
	}
	
	
	public static enum Genre{
		metal, westCoastHipHop, pop, rnb, eastCoastHipHop, reggae, rock, arab,
		jazz, post2000HipHop, southAmericanHipHop, punjabi, dance, dubstep, country
	}
	
	
}
