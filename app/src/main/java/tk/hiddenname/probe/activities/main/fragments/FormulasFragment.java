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
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;
import tk.hiddenname.probe.objects.Formula;

public class FormulasFragment extends Fragment {

   private ParentFragment parent;
   private RecyclerView rv;
   private long sectionId = 0;
   private ArrayList<Object> formulas;


   void setSectionId(long id) {
	  this.sectionId = id;
   }

   private void getFormulas() {
	  formulas = parent.getListActivity().getFormulasTwo(sectionId);
   }

   void setParent(ParentFragment parent) {
	  this.parent = parent;
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