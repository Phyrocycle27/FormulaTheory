package tk.hiddenname.probe.activities.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.activities.main.fragments.ParentFragment;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;
import tk.hiddenname.probe.objects.Units;
import tk.hiddenname.probe.sqlite_database.DatabaseThread;

public class ListActivity extends AppCompatActivity {

   private static ArrayList<Subject> subjects = new ArrayList<>();
   private static Units units = new Units();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  createData();

	  final Toolbar toolbar = findViewById(R.id.toolbar);
	  setSupportActionBar(toolbar);

	  if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.app_name);
	  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	  final ViewPager viewPager = findViewById(R.id.viewpager);
	  setupViewPager(viewPager);

	  TabLayout tabLayout = findViewById(R.id.tabs);
	  tabLayout.setupWithViewPager(viewPager);

	  final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

	  runDB();
   }

   private void setupViewPager(ViewPager viewPager) {
	  Adapter adapter = new Adapter(getSupportFragmentManager());
	  for (int i = 0; i < getSubjects().size(); i++) {
		 ParentFragment fragment = new ParentFragment();
		 fragment.setSubjectIndex(i);
		 adapter.addFragment(fragment, (getSubjects().get(i)).getName());
	  }
	  viewPager.setAdapter(adapter);
   }

   private void createData() {
	  /* *********** СПИСОК ФОРМУЛ **************** */
	  ArrayList<Formula> formulas = new ArrayList<>();
	  formulas.add(new Formula(R.array.pressOfBody, this));
	  formulas.add(new Formula(R.array.pressOfWater, this));
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
	  units.addUnit("P", "давление", units.new Unit(pressure));
	  // Сила
	  HashMap<String, Double> force = new HashMap<>();
	  force.put("Н", 1.0);
	  force.put("кН", 1000.0);
	  force.put("МН", 1000000.0);
	  force.put("мН", 0.001);
	  force.put("мкН", 0.000001);
	  units.addUnit("F", "сила", units.new Unit(force));
	  // Площадь
	  HashMap<String, Double> square = new HashMap<>();
	  square.put("км²", 1000000.0);
	  square.put("га", 10000.0);
	  square.put("а", 100.0);
	  square.put("дм²", 0.01);
	  square.put("см²", 0.0001);
	  square.put("мм²", 0.000001);
	  square.put("м²", 1.0);
	  units.addUnit("S", "площадь", units.new Unit(square));
	  // Длина
	  HashMap<String, Double> length = new HashMap<>();
	  length.put("м", 1.0);
	  length.put("см", 0.01);
	  length.put("дм", 0.1);
	  length.put("мм", 0.001);
	  length.put("км", 1000.0);
	  units.addUnit("h", "длина", units.new Unit(length));
	  // Плотность
	  HashMap<String, Double> density = new HashMap<>();
	  density.put("кг/м³", 1.0);
	  density.put("г/см³", 0.001);
	  units.addUnit("ρ", "плотность", units.new Unit(density));
	  // Ускорение свободного падения
	  HashMap<String, Double> accelerationOfGravity = new HashMap<>();
	  accelerationOfGravity.put("м/c²", 1.0);
	  units.addUnit("g", "ускорение свободного падения", units.new Unit(accelerationOfGravity));
   }

   private void runDB() {
	  DatabaseThread thread = new DatabaseThread(this);
	  thread.start();
   }

   private class Adapter extends FragmentPagerAdapter {

	  private final List<Fragment> mFragments = new ArrayList<>();
	  private final List<String> mFragmentTitles = new ArrayList<>();

	  Adapter(FragmentManager fm) {
		 super(fm);
	  }

	  void addFragment(Fragment fragment, String title) {
		 mFragments.add(fragment);
		 mFragmentTitles.add(title);
	  }

	  @Override
	  public Fragment getItem(int position) {
		 return mFragments.get(position);
	  }

	  @Override
	  public int getCount() {
		 return mFragments.size();
	  }

	  @Override
	  public CharSequence getPageTitle(int position) {
		 return mFragmentTitles.get(position);
	  }
   }

   public static ArrayList<Subject> getSubjects() {
	  return subjects;
   }

   public static ArrayList<Object> getSections(int subjIndex) {
	  try {
		 Subject subject = getSubjects().get(subjIndex);
		 return new ArrayList<Object>(subject.getSections());
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   public static ArrayList<Object> getFormulas(int subjIndex, int sectIndex) {
	  try {
		 Section section = (Section) getSections(subjIndex).get(sectIndex);
		 return new ArrayList<Object>(section.getFormulas());
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   public static Units getUnits() {
	  return units;
   }
}