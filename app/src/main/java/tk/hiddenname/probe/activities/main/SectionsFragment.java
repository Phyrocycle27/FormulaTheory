package tk.hiddenname.probe.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.adapters.ComplexRecyclerViewAdapter;
import tk.hiddenname.probe.adapters.RecyclerItemClickListener;

public class SectionsFragment extends Fragment {

   private RecyclerView rv;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  View view = inflater.inflate(R.layout.fragment_content, container, false);
	  rv = view.findViewById(R.id.recycler);
	  rv.setHasFixedSize(false);
	  rv.setLayoutManager(new LinearLayoutManager(getActivity()));
	  rv.setAdapter(new ComplexRecyclerViewAdapter(ListActivity.getSections(), rv.getContext()));
	  return view;
   }

   @Override
   public void onResume() {
	  super.onResume();
	  rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), rv,
			  new RecyclerItemClickListener.OnItemClickListener() {
				 @Override
				 public void onItemClick(View view, int position) {
					ListActivity.setSectionIndex(position);
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
