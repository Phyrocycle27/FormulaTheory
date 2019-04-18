package tk.hiddenname.probe.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import tk.hiddenname.probe.R;
import tk.hiddenname.probe.activities.main.ListActivity;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Units;

public class CalculateActivity extends AppCompatActivity {

   private Formula formula;
   private Units unit;
   private HashMap<String, Double> enteredValues = new HashMap<>(), selectedUnits = new HashMap<>();
   private List<View> addedViews = new ArrayList<>();
   private ArrayList<String[]> list = new ArrayList<>();

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_calculate);
	  ListView listView = findViewById(R.id.listview);

	  formula = getIntent().getParcelableExtra("formula");
	  ArrayAdapter<String> adapter =
			  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formula.getFormulas());
	  listView.setAdapter(adapter);


	  unit = ListActivity.getUnits();

	  final LinearLayout linear = findViewById(R.id.linear);
	  for (int i = 0; i < formula.getComponents().length; i++) {
		 String component = formula.getComponentByIndex(i);
		 list.add(ListActivity.getUnits().getUnits(component));
		 linear.addView(addField(component, i));
	  }
	  Log.d("Calculate", "(onCreate) HashMap \"values\" is: " + enteredValues.toString());
   }

   @SuppressLint("SetTextI18n")
   private View addField(final String component, final int i) {
	  @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.calculate_field, null);
	  TextView letter = view.findViewById(R.id.component_name);
	  EditText valueField = view.findViewById(R.id.value_field);
	  Spinner spinner = view.findViewById(R.id.units_spinner);
	  spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String[] tmpArr = list.get(i);
			selectedUnits.put(component, unit.getCoef(component, tmpArr[position]));
			Log.d("(addField) Calculate", "HashMap \"units\" is: " + selectedUnits.toString());
		 }

		 @Override
		 public void onNothingSelected(AdapterView<?> parent) {

		 }
	  });
	  try {
		 ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
				 unit.getUnits(component));
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinner.setAdapter(adapter);
		 valueField.setHint(unit.getHint(component));
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  letter.setText(component + " = ");
	  if (component.equals("g")) valueField.setText("9.81");
	  addedViews.add(view);
	  return view;
	  //hey
   }

   @Override
   protected void onResume() {
	  super.onResume();
	  // Определяем кнопку "Решить" и устанавливаем на неё слушатель
	  final Button btn = findViewById(R.id.calculate_btn);
	  btn.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View v) {
			//Убираем клавиатуру
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(btn.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			View unknown = new View(getApplicationContext());
			//Передаём введённые значения в
			byte count = 0;
			for (int i = 0; i < addedViews.size(); i++) {
			   String str = ((EditText) addedViews.get(i).findViewById(R.id.value_field)).getText().toString();
			   if (str.equals("")) {
				  count++;
				  unknown = addedViews.get(i);
			   }
			   try {
				  enteredValues.put(formula.getComponentByIndex(i), Double.valueOf(str));
			   } catch (NumberFormatException e) {
				  enteredValues.put(formula.getComponentByIndex(i), 0.0);
			   }
			}
			// Логируем значения данных в HashMap
			Log.d("Calculate", "(onResume) HashMap \"values\" is: " + enteredValues.toString());
			Log.d("Calculate", "(onResume) HashMap \"units\" is: " + selectedUnits.toString());
			// Выводим ответ в нужном формате
			if (count == 1) {
			   String tmpAnswr = String.valueOf(formula.solve(enteredValues, selectedUnits));
			   String answer;
			   String[] tmpArr = tmpAnswr.split("[.]");
			   Log.d("Calculate", "Answer: " + tmpAnswr);
			   if (tmpArr[1].startsWith("000")) answer = tmpArr[0];
			   else answer = tmpAnswr;
			   ((EditText) unknown.findViewById(R.id.value_field)).setText(answer);
			}
			// Иначе выводим тост с подсказкой
			else if (count >= 2) {
			   Toasty.warning(CalculateActivity.this, R.string.not_enough_data_to_solve, Toast.LENGTH_LONG, true).show();
			} else {
			   Toasty.warning(CalculateActivity.this, R.string.nothing_to_solve).show();
			}
			// очищаем массив
			for (String key : formula.getComponents()) enteredValues.put(key, null);
		 }
	  });
   }
}