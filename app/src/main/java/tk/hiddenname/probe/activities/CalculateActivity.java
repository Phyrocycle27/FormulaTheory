package tk.hiddenname.probe.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import tk.hiddenname.probe.R;
import tk.hiddenname.probe.objects.Formula;

public class CalculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);
        ListView listView = findViewById(R.id.listview);

        Formula formula = getIntent().getParcelableExtra("formula");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formula.getFormulas());
        listView.setAdapter(adapter);
        final LinearLayout linear = findViewById(R.id.linear);
        for (String component : formula.getComponents()) {
            addField(component, linear);
        }
    }

    @SuppressLint("SetTextI18n")
    private void addField(String str, LinearLayout linear) {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.calculate_item, null);
        TextView later = view.findViewById(R.id.component_name);
        EditText value = view.findViewById(R.id.value);
        later.setText(str + " = ");
        if (str.equals("g")) value.setText("9.81");
        linear.addView(view);
    }
}
