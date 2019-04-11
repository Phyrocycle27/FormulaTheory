package tk.hiddenname.probe.activities.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Hint;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;
import tk.hiddenname.probe.objects.Units;

public class ListActivity extends AppCompatActivity {

   private static int subjectIndex = 0, sectionIndex = 0, stage = 0;
   private static ArrayList<Object> subjects = new ArrayList<>();
   private static Units units = new Units();
   private static Hint hints = new Hint();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_list);
	  createData();
	  //создаём фрагмент предметов
	  SubjectsFragment fragment = new SubjectsFragment();
	  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	  ft.add(R.id.container, fragment);
	  ft.commit();
   }

   private void createData() {
	  /* *********** СПИСОК ФОРМУЛ **************** */
	  ArrayList<Formula> formulas = new ArrayList<>();
	  formulas.add(new Formula(R.array.pressOfBody, this));
	  formulas.add(new Formula(R.array.pressOfWater, this));
	  formulas.add(new Formula(R.array.pressOfWaterWithAtmospherical, this));
	  /* ********** СПИСОК РАЗДЕЛОВ *************** */
	  ArrayList<Section> sections = new ArrayList<>();
	  sections.add(new Section(R.string.hydrostatics, formulas, this));
	  /* ********** СПИСОК ПРЕДМТОВ *************** */
	  subjects = new ArrayList<>();
	  subjects.add(new Subject(R.string.physics, R.drawable.physics, android.R.color.holo_blue_light, sections, this));
	  subjects.add(new Subject(R.string.chemistry, R.drawable.chemistry, android.R.color.holo_red_light, null, this));
	  subjects.add(new Subject(R.string.algebra, R.drawable.algebra, android.R.color.holo_orange_light, null, this));
	  subjects.add(new Subject(R.string.geometry, R.drawable.geometry, android.R.color.holo_green_light, null, this));

	  // Экземпляры классов едениц измерения
	  // Давление
	  HashMap<String, Double> pressure = new HashMap<>();
	  pressure.put("Па", 1.0);
	  pressure.put("гПа", 100.0);
	  pressure.put("кПа", 1000.0);
	  pressure.put("МПа", 1000000.0);
	  pressure.put("мПа", 0.001);
	  pressure.put("мкПа", 0.000001);
	  units.addUnit("P", units.new Unit(pressure));
	  // Сила
	  HashMap<String, Double> force = new HashMap<>();
	  force.put("Н", 1.0);
	  force.put("кН", 1000.0);
	  force.put("МН", 1000000.0);
	  force.put("мН", 0.001);
	  force.put("мкН", 0.000001);
	  units.addUnit("F", units.new Unit(force));
	  // Площадь
	  HashMap<String, Double> square = new HashMap<>();
	  square.put("км²", 1000000.0);
	  square.put("га", 10000.0);
	  square.put("а", 100.0);
	  square.put("дм²", 0.01);
	  square.put("см²", 0.0001);
	  square.put("мм²", 0.000001);
	  square.put("м²", 1.0);
	  units.addUnit("S", units.new Unit(square));

	  // Подсказки в поле ввода
	  hints.getHints().put("P", "давление");
	  hints.getHints().put("S", "площадь");
	  hints.getHints().put("F", "сила");
   }

   static void setSubjectIndex(int a) {
	  subjectIndex = a;
   }

   static void setSectionIndex(int a) {
	  sectionIndex = a;
   }

   static ArrayList<Object> getSubjects() {
	  return subjects;
   }

   static ArrayList<Object> getSections() {
	  try {
		 Subject subject = (Subject) getSubjects().get(subjectIndex);
		 return new ArrayList<Object>(subject.getSections());
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   static ArrayList<Object> getFormulas() {
	  try {
		 Section section = (Section) getSections().get(sectionIndex);
		 return new ArrayList<Object>(section.getFormulas());
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   public static Units getUnits() {
	  return units;
   }

   public static Hint getHints() {
	  return hints;
   }
}