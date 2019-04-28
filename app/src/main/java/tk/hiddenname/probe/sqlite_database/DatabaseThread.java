package tk.hiddenname.probe.sqlite_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;

public class DatabaseThread extends Thread {

   private Cursor cursor;
   private DBHelper dbHelper;
   private SQLiteDatabase db;

   public DatabaseThread(Context context) {
	  dbHelper = new DBHelper(context);
   }

   public void run() {
	  Log.d("LogDB", "открываем БД для письма");
	  db = dbHelper.getWritableDatabase();
	  /*List<Subject> subjects = getSubjects();
	  for (Subject subject : subjects) {
		 Log.d("LogDB", subject.toString());
	  }
	  List<Section> sections = getSections(0);
	  for (Section section : sections) {
		 Log.d("LogDB", section.toString());
	  }
	  List<Formula> formulas = getFormulaObjects(0);
	  for (Formula formula : formulas) {
		 Log.d("LogDB", formula.toString());
	  }*/
   }

   public List<Subject> getSubjects() {
	  cursor = db.query(DBConstants.SubjectEntity.TABLE_NAME,
			  new String[]{
					  DBConstants.SubjectEntity._ID,
					  DBConstants.SubjectEntity.COLUMN_NAME,
					  DBConstants.SubjectEntity.COLUMN_NUM_OF_FORMULAS,
					  DBConstants.SubjectEntity.COLUMN_COLOR,
					  DBConstants.SubjectEntity.COLUMN_PHOTO_ID},
			  null, null, null, null, null);
	  if (cursor != null && cursor.moveToFirst()) {
		 List<Subject> subjects = new ArrayList<>();
		 do {
			Subject subject = new Subject();
			for (String column : cursor.getColumnNames()) {
			   switch (column) {
				  case DBConstants.SubjectEntity._ID:
					 subject.setId(cursor.getLong(cursor.getColumnIndex(column)));
				  case DBConstants.SubjectEntity.COLUMN_NAME:
					 subject.setName(cursor.getString(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.SubjectEntity.COLUMN_COLOR:
					 subject.setColor(cursor.getInt(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.SubjectEntity.COLUMN_NUM_OF_FORMULAS:
					 subject.setNumOfFormulas(cursor.getInt(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.SubjectEntity.COLUMN_PHOTO_ID:
					 subject.setDrawableId(cursor.getInt(cursor.getColumnIndex(column)));
			   }
			}
			subjects.add(subject);
		 } while (cursor.moveToNext());
		 cursor.close();
		 Log.d("LogDB", "эта часть кода успешно пройдена");
		 return subjects;
	  } else {
		 Log.d("LogDB", "Cursor is null");
		 return null;
	  }
   }

   public List<Section> getSections(long subjectId) {
	  cursor = db.query(DBConstants.SectionEntity.TABLE_NAME,
			  new String[]{
					  DBConstants.SectionEntity.COLUMN_NAME,
					  DBConstants.SectionEntity.COLUMN_NUM_OF_FORMULAS,
					  DBConstants.SectionEntity.COLUMN_SUBJECT_ID,
					  DBConstants.SectionEntity._ID},
			  DBConstants.SectionEntity.COLUMN_SUBJECT_ID + " = ?",
			  new String[]{Long.toString(subjectId)}, null, null, null);
	  if (cursor != null && cursor.moveToFirst()) {
		 List<Section> sections = new ArrayList<>();
		 do {
			Section section = new Section();
			for (String column : cursor.getColumnNames()) {
			   switch (column) {
				  case DBConstants.SectionEntity._ID:
					 section.setId(cursor.getLong(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.SectionEntity.COLUMN_NAME:
					 section.setName(cursor.getString(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.SectionEntity.COLUMN_NUM_OF_FORMULAS:
					 section.setNumOfFormulas(cursor.getInt(cursor.getColumnIndex(column)));
			   }
			}
			sections.add(section);
		 } while (cursor.moveToNext());
		 cursor.close();
		 Log.d("LogDB", "эта часть кода тоже успешно пройдена");
		 return sections;
	  } else {
		 Log.d("LogDB", "Cursor is null");
		 return null;
	  }
   }

   public List<Formula> getFormulaObjects(long sectionId) {
	  cursor = db.query(DBConstants.FormulaObjectEntity.TABLE_NAME,
			  new String[]{
					  DBConstants.FormulaObjectEntity._ID,
					  DBConstants.FormulaObjectEntity.COLUMN_DESCRIPTION,
					  DBConstants.FormulaObjectEntity.COLUMN_SECTION_ID},
			  DBConstants.FormulaObjectEntity.COLUMN_SECTION_ID + " = ?",
			  new String[]{Long.toString(sectionId)}, null, null, null);
	  if (cursor != null && cursor.moveToFirst()) {
		 List<Formula> formulasObject = new ArrayList<>();
		 do {
			Formula formula = new Formula();
			for (String column : cursor.getColumnNames()) {
			   switch (column) {
				  case DBConstants.FormulaObjectEntity._ID:
					 formula.setId(cursor.getLong(cursor.getColumnIndex(column)));
					 break;
				  case DBConstants.FormulaObjectEntity.COLUMN_DESCRIPTION:
					 formula.setName(cursor.getString(cursor.getColumnIndex(column)));
			   }
			}
			formula.setFormulas(getFormulas(formula.getId()));
			formulasObject.add(formula);
		 } while (cursor.moveToNext());
		 cursor.close();
		 return formulasObject;
	  } else {
		 Log.d("LogDB", "Cursor is null");
		 return null;
	  }
   }

   private String[] getFormulas(long formulaObjectId) {
	  Cursor c = db.query(DBConstants.FormulaEntity.TABLE_NAME,
			  new String[]{
					  DBConstants.FormulaEntity.COLUMN_FORMULA,
					  DBConstants.FormulaEntity.COLUMN_FORMULA_SUBSECTION_ID},
			  DBConstants.FormulaEntity.COLUMN_FORMULA_SUBSECTION_ID + " = ?",
			  new String[]{Long.toString(formulaObjectId)}, null, null, null);
	  if (c != null && c.moveToFirst()) {
		 List<String> formulas = new ArrayList<>();
		 do {
			for (String column : c.getColumnNames()) {
			   if (column.equals(DBConstants.FormulaEntity.COLUMN_FORMULA)) {
				  formulas.add(c.getString(c.getColumnIndex(column)));
			   }
			}
		 } while (c.moveToFirst());
		 c.close();
		 return (String[]) formulas.toArray();
	  } else {
		 Log.d("LogDB", "Cursor is null");
		 return null;
	  }
   }
}