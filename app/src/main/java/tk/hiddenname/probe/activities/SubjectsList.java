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
import tk.hiddenname.probe.objects.Section;
import tk.hiddenname.probe.objects.Subject;

public class SubjectsList extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();

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

    private static ArrayList<Object> subjects;

    private void createData() {
        /* *********** СПИСОК ФОРМУЛ **************** */
        ArrayList<Formula> formulas = new ArrayList<>();
        formulas.add(new Formula(R.array.pressOfBody, this));
        formulas.add(new Formula(R.array.pressOfWater, this));
        formulas.add(new Formula(R.array.pressOfWaterWithAtmospherical, this));
        /* ********** СПИСОК РАЗДЕЛОВ *************** */
        ArrayList<Section> sections = new ArrayList<>();
        sections.add(new Section(R.string.hydrostatics, formulas, this));
        /* ********** СПИСОК ПРЕДМТОВ *************** */
        subjects = new ArrayList<>();
        subjects.add(new Subject(R.string.physics, R.drawable.physics, sections, this));
        subjects.add(new Subject(R.string.chemistry, R.drawable.chemistry, null, this));
        subjects.add(new Subject(R.string.algebra, R.drawable.algebra, null, this));
        subjects.add(new Subject(R.string.geometry, R.drawable.geometry, null, this));
    }

    private static ArrayList<Object> getData() {
        return subjects;
    }

    private void next(int position) {
        Intent intent = new Intent(SubjectsList.this, SectionsList.class);
        Subject subject = (Subject) getData().get(position);
        intent.putExtra("sections", subject.getSections());
        startActivity(intent);
    }
}