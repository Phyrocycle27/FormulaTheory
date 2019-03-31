package tk.hiddenname.probe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;
import tk.hiddenname.probe.objects.Formula;

public class FormulasList extends AppCompatActivity {

   private RecyclerView rv;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

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

   private ArrayList<Object> getData() {
	  try {
		 return new ArrayList<Object>(getIntent().getParcelableArrayListExtra("formulas"));
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return null;
   }

   private void next(int position) {
	  Intent intent = new Intent(FormulasList.this, CalculateActivity.class);
	  Formula formula = (Formula) getData().get(position);
	  intent.putExtra("formula", formula);
	  startActivity(intent);
   }
}