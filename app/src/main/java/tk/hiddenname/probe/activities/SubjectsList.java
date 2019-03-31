package tk.hiddenname.probe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Hint;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;
import tk.hiddenname.probe.objects.Units;

public class SubjectsList extends AppCompatActivity {

   private RecyclerView rv;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  createData();

	  rv = findViewById(R.id.recycle);
	  rv.setHasFixedSize(false);
	  rv.setLayoutManager(new LinearLayoutManager(this));
	  rv.setAdapter(new ComplexRecyclerViewAdapter(getData()));
   }

   @Override
   protected void onResume() {
	  super.onResume();
	  rv.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv,
			  new RecyclerItemClickListener.OnItemClickListener() {
				 @Override
				 public void onItemClick(View view, int position) {
					next(position);
				 }

				 @Override
				 public void onLongItemClick(View view, int position) {

				 }
			  }));
   }

   private static ArrayList<Object> subjects = new ArrayList<>();
   private static Units units = new Units();
   private static Hint hints = new Hint();

   private void createData() {
	  /* *********** СПИСОК ФОРМУЛ **************** */
	  ArrayList<Formula> formulas = new ArrayList<>();
	  formulas.add(new Formula(R.array.pressOfBody, this));
	  // 	formulas.add(new Formula(R.array.pressOfWater, this));
	  // 	formulas.add(new Formula(R.array.pressOfWaterWithAtmospherical, this));
	  /* ********** СПИСОК РАЗДЕЛОВ *************** */
	  ArrayList<Section> sections = new ArrayList<>();
	  sections.add(new Section(R.string.hydrostatics, formulas, this));
	  /* ********** СПИСОК ПРЕДМТОВ *************** */
	  subjects = new ArrayList<>();
	  subjects.add(new Subject(R.string.physics, R.drawable.physics, sections, this));
	  subjects.add(new Subject(R.string.chemistry, R.drawable.chemistry, null, this));
	  subjects.add(new Subject(R.string.algebra, R.drawable.algebra, null, this));
	  subjects.add(new Subject(R.string.geometry, R.drawable.geometry, null, this));

	  // Экземплюры классов едениц измерения
	  // Давление
	  HashMap<String, Double> pressure = new HashMap<>();
	  pressure.put("Па", 1.0);  pressure.put("гПа", 100.0);  pressure.put("кПа", 1000.0);
	  pressure.put("МПа", 1000000.0);  pressure.put("мПа", 0.001); pressure.put("мкПа", 0.000001);
	  units.addUnit("P", units.new Unit(pressure));
	  // Сила
	  HashMap<String, Double> force = new HashMap<>();
	  force.put("Н", 1.0);  force.put("кН", 1000.0);  force.put("МН", 1000000.0);
	  force.put("мН", 0.001);  force.put("мкН", 0.000001);
	  units.addUnit("F", units.new Unit(force));
	  // Площадь
	  HashMap<String, Double> square = new HashMap<>();
	  square.put("км²", 1000000.0);  square.put("га", 10000.0);  square.put("а", 100.0);
	  square.put("дм²", 0.01);  square.put("см²", 0.0001);  square.put("мм²", 0.000001);
	  units.addUnit("S", units.new Unit(square));

	  // Подсказки в поле ввода
	  //
	  hints.getHints().put("P", "давление"); 	hints.getHints().put("S", "площадь");
	  hints.getHints().put("F", "сила");
   }

   private static ArrayList<Object> getData() {
	  return subjects;
   }

   public static Units getUnits() {
	  return units;
   }

   public static Hint getHints(){
      return hints;
   }

   private void next(int position) {
	  Intent intent = new Intent(SubjectsList.this, SectionsList.class);
	  Subject subject = (Subject) getData().get(position);
	  intent.putExtra("sections", subject.getSections());
	  startActivity(intent);
   }
}