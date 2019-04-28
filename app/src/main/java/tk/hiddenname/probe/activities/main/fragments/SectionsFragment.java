package tk.hiddenname.probe.activities.main.fragments;

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
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;

public class SectionsFragment extends Fragment {

   private ParentFragment parent;
   private RecyclerView rv;
   private ArrayList<Object> sections;

   void setParent(ParentFragment parent) {
	  this.parent = parent;
   }

   private void setSections() {
	  sections = parent.getListActivity().getSectionsTwo(parent.getSubjectId());
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  View view;
	  setSections();
	  if (sections != null) {
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
	  rv.setAdapter(new ComplexRecyclerViewAdapter(sections, rv.getContext()));
   }

   @Override
   public void onResume() {
	  super.onResume();
	  if (rv != null)
		 rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
				 new RecyclerItemClickListener.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
					   /*FormulasFragment formulasFragment = new FormulasFragment();
					   Section section = (Section) sections.get(position);
					   formulasFragment.setSectionId(section.getId());
					   formulasFragment.setParent(parent);
					   parent.replace(formulasFragment);*/
					}

					@Override
					public void onLongItemClick(View view, int position) {

					}
				 }));
   }
}