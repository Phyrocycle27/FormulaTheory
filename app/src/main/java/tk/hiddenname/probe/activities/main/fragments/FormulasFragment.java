package tk.hiddenname.probe.activities.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.activities.CalculateActivity;
import tk.hiddenname.probe.activities.main.ListActivity;
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;
import tk.hiddenname.probe.objects.Formula;

public class FormulasFragment extends Fragment {

   private RecyclerView rv;
   private int subjectIndex = 0, sectionIndex = 0;
   private ArrayList<Object> formulas;


   void setSubjAndSectIndex(int subjectIndex, int sectionIndex) {
	  this.subjectIndex = subjectIndex;
	  this.sectionIndex = sectionIndex;
   }

   private void getFormulas() {
	  formulas = ListActivity.getFormulas(subjectIndex, sectionIndex);
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  View view;
	  getFormulas();
	  if (formulas != null) {
		 view = inflater.inflate(R.layout.fragment_content, container, false);
		 createRecyclerView(view);
	  } else {
		 view = inflater.inflate(R.layout.fragment_empty, container, false);
	  }
	  return view;
   }

   private void createRecyclerView(View view) {
	  rv = view.findViewById(R.id.recycler);
	  rv.setHasFixedSize(false);
	  rv.setLayoutManager(new LinearLayoutManager(getActivity()));
	  rv.setAdapter(new ComplexRecyclerViewAdapter(formulas, getActivity()));
   }

   @Override
   public void onResume() {
	  super.onResume();
	  if (rv != null)
		 rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
				 new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
					   Intent intent = new Intent(getContext(), CalculateActivity.class);
					   intent.putExtra("formula", (Formula) formulas.get(position));
					   startActivity(intent);
					}

					@Override
					public void onLongItemClick(View view, int position) {

					}
				 }));
   }
}