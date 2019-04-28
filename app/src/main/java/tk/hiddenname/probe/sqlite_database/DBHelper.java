package tk.hiddenname.probe.sqlite_database;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;

public class DBHelper extends SQLiteOpenHelper {

   private static final int DATABASE_VERSION = 1;
   private static final String DATABASE_NAME = "sample_database";
   private Context context;

   DBHelper(Context context) {
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  Log.d("LogDB", "создаём или открываем БД");
	  this.context = context;
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
	  Log.d("LogDB", "создаём таблицы");
	  db.execSQL(DBConstants.SubjectEntity.CREATE_TABLE);
	  db.execSQL(DBConstants.SectionEntity.CREATE_TABLE);
	  db.execSQL(DBConstants.FormulaObjectEntity.CREATE_TABLE);
	  db.execSQL(DBConstants.FormulaEntity.CREATE_TABLE);
	  db.execSQL(DBConstants.UnitObjectEntity.CREATE_TABLE);
	  db.execSQL(DBConstants.UnitEntity.CREATE_TABLE);
	  firstAddDataToDB(db);
   }

   private void firstAddDataToDB(SQLiteDatabase db) {
	  List<Subject> subjects = createDataSubject();
	  for (Subject subject : subjects) {
		 Log.d("LogDB", subject.toString());
	  }
	  ContentValues cv = new ContentValues();
	  int subjectId = 0, sectionId = 0, formulaSubSectionId = 0, formulaId = 0;
	  //***************** Добавление предметов в таблицу subject ***********************************
	  if (subjects.size() > 0)
		 for (Subject subject : subjects) {
			cv.clear();
			cv.put(DBConstants.SubjectEntity.COLUMN_NAME, subject.getName());
			cv.put(DBConstants.SubjectEntity._ID, subjectId);
			cv.put(DBConstants.SubjectEntity.COLUMN_NUM_OF_FORMULAS, subject.getNumOfFormulas());
			cv.put(DBConstants.SubjectEntity.COLUMN_PHOTO_ID, subject.getDrawableId());
			cv.put(DBConstants.SubjectEntity.COLUMN_COLOR, subject.getColor());
			db.insert(DBConstants.SubjectEntity.TABLE_NAME, null, cv);
			//******************** Добавление разделов предмета в таблицу section ******************
			if (subject.getSections() != null)
			   for (Section section : subject.getSections()) {
				  cv.clear();
				  cv.put(DBConstants.SectionEntity.COLUMN_NAME, section.getName());
				  cv.put(DBConstants.SectionEntity._ID, sectionId);
				  cv.put(DBConstants.SectionEntity.COLUMN_NUM_OF_FORMULAS, section.getNumOfFormulas());
				  cv.put(DBConstants.SectionEntity.COLUMN_SUBJECT_ID, subjectId);
				  db.insert(DBConstants.SectionEntity.TABLE_NAME, null, cv);
				  // ******* Добравления подраздела с формулами в таблицу formula_subsection *******
				  if (section.getFormulas() != null)
					 for (Formula formula : section.getFormulas()) {
						cv.clear();
						cv.put(DBConstants.FormulaObjectEntity.COLUMN_DESCRIPTION, formula.getName());
						cv.put(DBConstants.FormulaObjectEntity._ID, formulaSubSectionId);
						cv.put(DBConstants.FormulaObjectEntity.COLUMN_SECTION_ID, sectionId);
						db.insert(DBConstants.FormulaObjectEntity.TABLE_NAME, null, cv);
						//****************** Добавление формул в таблицу formula *******************
						if (formula.getFormulas().length > 0)
						   for (String formula_str : formula.getFormulas()) {
							  cv.clear();
							  cv.put(DBConstants.FormulaEntity._ID, formulaId);
							  cv.put(DBConstants.FormulaEntity.COLUMN_FORMULA, formula_str);
							  cv.put(DBConstants.FormulaEntity.COLUMN_FORMULA_SUBSECTION_ID, formulaSubSectionId);
							  formulaId++;
						   }
						formulaSubSectionId++;
					 }
				  sectionId++;
			   }
			subjectId++;
		 }
   }

   private List<Subject> createDataSubject() {
	  Log.d("LogDB", "создаём данные");
	  /* *********** СПИСОК ФОРМУЛ **************** */
	  ArrayList<Formula> formulas = new ArrayList<>();
	  formulas.add(new Formula(R.array.pressOfBody, context));
	  formulas.add(new Formula(R.array.pressOfWater, context));
	  /* ********** СПИСОК РАЗДЕЛОВ *************** */
	  ArrayList<Section> sections = new ArrayList<>();
	  sections.add(new Section(R.string.hydrostatics, formulas, context));
	  /* ********** СПИСОК ПРЕДМТОВ *************** */
	  ArrayList<Subject> subjects = new ArrayList<>();
	  subjects.add(new Subject(R.string.physics, R.drawable.physics, android.R.color.holo_blue_light, sections, context));
	  subjects.add(new Subject(R.string.chemistry, R.drawable.chemistry, android.R.color.holo_red_light, null, context));
	  subjects.add(new Subject(R.string.algebra, R.drawable.algebra, android.R.color.holo_orange_light, null, context));
	  subjects.add(new Subject(R.string.geometry, R.drawable.geometry, android.R.color.holo_green_light, null, context));
	  return subjects;
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }
}