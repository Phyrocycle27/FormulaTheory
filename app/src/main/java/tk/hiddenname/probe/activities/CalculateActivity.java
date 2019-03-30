package tk.hiddenname.probe.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    }

    @SuppressLint("SetTextI18n")
    private View addField(String str) {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.calculate_item, null);
        TextView later = view.findViewById(R.id.component_name);
        EditText value = view.findViewById(R.id.value);
        later.setText(str + " = ");
        if (str.equals("g")) value.setText("9.81");
        addedViews.add(view);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btn = findViewById(R.id.calculate_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View unknown = new View(getApplicationContext());
                for (int i = 0; i < addedViews.size(); i++) {
                    String str = ((EditText) addedViews.get(i).findViewById(R.id.value)).getText().toString();
                    if (str.equals("")) unknown = addedViews.get(i);
                    values.put(formula.getComponentByIndex(i), str);
                }
                ((EditText) unknown.findViewById(R.id.value)).setText(formula.solve(values));
                for (String key : values.keySet()) values.put(key, null);
            }
        });
    }
}