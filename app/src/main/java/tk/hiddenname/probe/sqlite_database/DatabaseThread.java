package tk.hiddenname.probe.sqlite_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DatabaseThread extends Thread {

   private Context context;

   public DatabaseThread(Context context) {
	  this.context = context;
   }

   public void run() {
      DBHelper dbHelper = new DBHelper(context);
      SQLiteDatabase db;
      try {
         Log.d("LogDB", "открываем БД для письма");
         db = dbHelper.getWritableDatabase();
      } catch (SQLiteException ex) {
         db = dbHelper.getReadableDatabase();
      }
      Cursor cursor;
   }
}
