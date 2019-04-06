package tk.hiddenname.probe.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.objects.Formula;

public class CalculateActivity extends AppCompatActivity {

   private Formula formula;
   private HashMap<String, String> values;
   private List<View> addedViews;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_calculate);
	  ListView listView = findViewById(R.id.listview);

	  formula = getIntent().getParcelableExtra("formula");
	  ArrayAdapter<String> adapter =
			  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formula.getFormulas());
	  listView.setAdapter(adapter);

	  addedViews = new ArrayList<>();
	  values = new HashMap<>();
	  for (String key : formula.getComponents()) values.put(key, null);

	  final LinearLayout linear = findViewById(R.id.linear);
	  for (String component : formula.getComponents()) {
		 linear.addView(addField(component));
	  }
	  Log.d("Calculate", "HashMap \"values\" is: " + values.toString());
   }

   @SuppressLint("SetTextI18n")
   private View addField(String str) {
	  @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.calculate_field, null);
	  TextView letter = view.findViewById(R.id.component_name);
	  EditText value = view.findViewById(R.id.value_field);
	  Spinner spinner = view.findViewById(R.id.units_spinner);
	  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ListActivity.getUnits().getUnitsByLetter(str));
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  spinner.setAdapter(adapter);
	  letter.setText(str + " = ");
	  value.setHint(ListActivity.getHints().getHints().get(str));
	  if (str.equals("g")) value.setText("9.81");
	  Log.d("Calculate", "New view created: " + view.toString());
	  addedViews.add(view);
	  return view;
   }

   @Override
   protected void onResume() {
	  super.onResume();
	  // Определяем кнопку "Решить" и устанавливаем на неё слушатель
	  Button btn = findViewById(R.id.calculate_btn);
	  btn.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View v) {
			View unknown = new View(getApplicationContext());
			//Передаём введённые значения в
			byte count = 0;
			for (int i = 0; i < addedViews.size(); i++) {
			   String str = ((EditText) addedViews.get(i).findViewById(R.id.value_field)).getText().toString();
			   if (str.equals("")) {
				  count++;
				  unknown = addedViews.get(i);
			   }
			   values.put(formula.getComponentByIndex(i), str);
			}
			Log.d("Calculate", "HashMap \"values\" is: " + values.toString());
			if (count == 1) {
			   ((EditText) unknown.findViewById(R.id.value_field)).setText(String.valueOf(formula.solve(values)));
			   for (String key : values.keySet()) values.put(key, null);
			}
		 }
	  });
   }
}