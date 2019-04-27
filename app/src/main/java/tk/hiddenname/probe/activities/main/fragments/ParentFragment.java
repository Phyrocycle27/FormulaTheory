package tk.hiddenname.probe.activities.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.hiddenname.probe.R;

public class ParentFragment extends Fragment {

   private int subjectIndex;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	  return inflater.inflate(R.layout.fragment_container, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
	  SectionsFragment sectionsFragment = new SectionsFragment();
	  sectionsFragment.setSubjectIndex(subjectIndex);
	  sectionsFragment.setParent(this);
	  replace(sectionsFragment);
   }

   public void setSubjectIndex(int index) {
	  subjectIndex = index;
   }

   public void goToFormulas(int sectionIndex) {
	  FormulasFragment formulasFragment = new FormulasFragment();
	  formulasFragment.setSubjAndSectIndex(subjectIndex, sectionIndex);
	  replace(formulasFragment);
   }

   private void replace(Fragment childFragment) {
	  FragmentTransaction ft = getChildFragmentManager().beginTransaction();
	  ft.replace(R.id.child_fragment_container, childFragment).addToBackStack(null).commit();
   }

}