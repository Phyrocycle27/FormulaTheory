package tk.hiddenname.probe.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.objects.Formula;
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;
import tk.hiddenname.probe.viewholders.SubjectViewHolder;
import tk.hiddenname.probe.viewholders.SectionViewHolder;
import tk.hiddenname.probe.viewholders.FormulaViewHolder;

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private ArrayList<Object> items;

   private final int SUBJECT = 0, SECTION = 1, FORMULA = 2;

   public ComplexRecyclerViewAdapter(ArrayList<Object> items) {
	  this.items = items;
   }

   @Override
   public int getItemViewType(int position) {
	  if (items.get(position) instanceof Subject) {
		 return SUBJECT;
	  } else if (items.get(position) instanceof Section) {
		 return SECTION;
	  } else if (items.get(position) instanceof Formula) {
		 return FORMULA;
	  }
	  return -1;
   }

   @Override
   public int getItemCount() {
	  try {
		 return this.items.size();
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
	  return 0;
   }

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
	  RecyclerView.ViewHolder viewHolder = null;
	  LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

	  switch (viewType) {
		 case SUBJECT:
			View v1 = inflater.inflate(R.layout.subject_item, viewGroup, false);
			viewHolder = new SubjectViewHolder(v1);
			break;
		 case SECTION:
			View v2 = inflater.inflate(R.layout.section_item, viewGroup, false);
			viewHolder = new SectionViewHolder(v2);
			break;
		 case FORMULA:
			View v3 = inflater.inflate(R.layout.formula_item, viewGroup, false);
			viewHolder = new FormulaViewHolder(v3);
	  }
	  return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
	  switch (viewHolder.getItemViewType()) {
		 case SUBJECT:
			SubjectViewHolder vh1 = (SubjectViewHolder) viewHolder;
			configureViewHolder1(vh1, position);
			break;
		 case SECTION:
			SectionViewHolder vh2 = (SectionViewHolder) viewHolder;
			configureViewHolder2(vh2, position);
			break;
		 case FORMULA:
			FormulaViewHolder vh3 = (FormulaViewHolder) viewHolder;
			configureViewHolder3(vh3, position);
	  }
   }

   @SuppressLint("SetTextI18n")
   private void configureViewHolder1(SubjectViewHolder vh1, final int position) {
	  Subject subject = (Subject) items.get(position);
	  if (subject != null) {
		 vh1.getLabel1().setText(subject.getName());
		 vh1.getLabel2().setText("Всего формул: " + Integer.toString(subject.getNumOfFormulas()));
		 vh1.getImageView().setImageBitmap(subject.getDrawable());
	  }
   }

   @SuppressLint("SetTextI18n")
   private void configureViewHolder2(SectionViewHolder vh2, final int position) {
	  Section section = (Section) items.get(position);
	  if (section != null) {
		 vh2.getLabel1().setText(section.getName());
		 vh2.getLabel2().setText("Всего формул: " + Integer.toString(section.getNumOfFormulas()));
	  }
   }

   @SuppressLint("SetTextI18n")
   private void configureViewHolder3(FormulaViewHolder vh3, final int position) {
	  Formula formula = (Formula) items.get(position);
	  if (formula != null) {
		 vh3.getLabel1().setText(formula.getFormula());
		 vh3.getLabel2().setText(formula.getName());
	  }
   }
}