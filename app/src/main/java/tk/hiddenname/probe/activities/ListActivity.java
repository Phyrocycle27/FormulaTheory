package tk.hiddenname.probe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ListActivity extends AppCompatActivity {

   private static int subjectIndex = 0, sectionIndex = 0, stage = 0;
   private static ArrayList<Object> subjects = new ArrayList<>();
   private static Units units = new Units();
   private static Hint hints = new Hint();
   private FragmentActivity activity = this;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_list);
	  createData();
	  SubjectsFragment fragment = new SubjectsFragment();
	  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	  ft.replace(R.id.container, fragment);
	  ft.commit();
   }

   // ФРАГМЕНТ ПРЕДМЕТОВ
   public static class SubjectsFragment extends Fragment {

	  private RecyclerView rv;

	  @Nullable
	  @Override
	  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_content, container, false);
		 rv = view.findViewById(R.id.recycler);
		 rv.setHasFixedSize(false);
		 rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		 rv.setAdapter(new ComplexRecyclerViewAdapter(getSubjects(), getActivity()));
		 return view;
	  }

	  @Override
	  public void onResume() {
		 super.onResume();
		 rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
				 new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
					   subjectIndex = position;
					   SectionsFragment fragment = new SectionsFragment();
					   FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					   ft.replace(R.id.container, fragment);
					   ft.addToBackStack(null);
					   ft.commit();
					}

					@Override
					public void onLongItemClick(View view, int position) {

					}
				 }));
	  }
   }

   // ФРАГМЕНТ РАЗДЕЛОВ
   public static class SectionsFragment extends Fragment {

	  private RecyclerView rv;

	  @Nullable
	  @Override
	  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_content, container, false);
		 rv = view.findViewById(R.id.recycler);
		 rv.setHasFixedSize(false);
		 rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		 rv.setAdapter(new ComplexRecyclerViewAdapter(getSections(), getActivity()));
		 return view;
	  }

	  @Override
	  public void onResume() {
		 super.onResume();
		 rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
				 new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
					   sectionIndex = position;
					   FormulasFragment fragment = new FormulasFragment();
					   FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					   ft.replace(R.id.container, fragment);
					   ft.addToBackStack(null);
					   ft.commit();
					}

					@Override
					public void onLongItemClick(View view, int position) {

					}
				 }));
	  }
   }

   // ФРАГМЕНТ ФОРМУЛ
   public static class FormulasFragment extends Fragment {

	  private RecyclerView rv;

	  @Nullable
	  @Override
	  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_content, container, false);
		 rv = view.findViewById(R.id.recycler);
		 rv.setHasFixedSize(false);
		 rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		 rv.setAdapter(new ComplexRecyclerViewAdapter(getFormulas(), getActivity()));
		 return view;
	  }

	  @Override
	  public void onResume() {
		 super.onResume();
		 rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
				 new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
					   Intent intent = new Intent(getContext(), CalculateActivity.class);
					   intent.putExtra("formula", (Formula) getFormulas().get(position));
					   startActivity(intent);
					}

					@Override
					public void onLongItemClick(View view, int position) {

					}
				 }));
	  }
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
	  subjects.add(new Subject(R.string.physics, R.drawable.physics, sections, this));
	  subjects.add(new Subject(R.string.chemistry, R.drawable.chemistry, null, this));
	  subjects.add(new Subject(R.string.algebra, R.drawable.algebra, null, this));
	  subjects.add(new Subject(R.string.geometry, R.drawable.geometry, null, this));

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

   private static ArrayList<Object> getSubjects() {
	  return subjects;
   }

   private static ArrayList<Object> getSections() {
      try {
		 Subject subject = (Subject) getSubjects().get(subjectIndex);
		 return new ArrayList<Object>(subject.getSections());
	  } catch (NullPointerException e) {
         e.printStackTrace();
	  }
	  return null;
   }

   private static ArrayList<Object> getFormulas() {
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