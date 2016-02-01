package com.musicplayer;

import android.provider.BaseColumns;

public class ProcessingDBContract {

	
	 public ProcessingDBContract() {}

	    /* Inner class that defines the table contents */
	    public static abstract class TimeDomainTable implements BaseColumns {
	        public static final String TABLE_NAME = "time";
	        public static final String[] COLUMN_NAMES = {"block0", "block1", "block2", "block3", "block4", "block5", "block6", "block7", "block8",
	        	"block9", "block10", "block11", "block12", "block13", "block14", "block15", "block16", "block17",
	        	"block18", "block19", "block20", "block21", "block22", "block23", "block24", "block25", "block26",
	        	"block27", "block28", "block29", "block30", "block31", "block32", "block33", "block34", "block35",
	        	"block36", "block37", "block38", "block39", "block40", "block41", "block42", "block43", "block44",
	        	"block45", "block46", "block47", "block48", "block49"};
	        
	        
	        private static final String TEXT_TYPE = " TEXT";
	        private static final String COMMA_SEP = ",";
	        public static final String SQL_CREATE_ENTRIES =
	            "CREATE TABLE " + TABLE_NAME + " (" +
	            TimeDomainTable._ID + " INTEGER PRIMARY KEY,"
	            + COLUMN_NAMES[0] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[1] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[2] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[3] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[4] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[5] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[6] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[7] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[8] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[9] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[10] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[11] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[12] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[13] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[14] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[15] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[16] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[17] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[18] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[19] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[20] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[21] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[22] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[23] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[24] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[25] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[26] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[27] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[28] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[29] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[30] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[31] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[32] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[33] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[34] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[35] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[36] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[37] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[38] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[39] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[40] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[41] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[42] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[43] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[44] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[45] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[46] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[47] + TEXT_TYPE + COMMA_SEP
	            + COLUMN_NAMES[48] + TEXT_TYPE + COMMA_SEP + COLUMN_NAMES[49] + TEXT_TYPE + COMMA_SEP
	            + " )";
	            
	            
	        


	        public static final String SQL_DELETE_ENTRIES =
	            "DROP TABLE IF EXISTS " + TABLE_NAME;
	        
	    }
	
	
}
