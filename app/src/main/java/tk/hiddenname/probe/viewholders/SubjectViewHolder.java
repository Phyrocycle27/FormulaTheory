package tk.hiddenname.probe.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tk.hiddenname.probe.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder {

    private TextView label1, label2;
    private ImageView imageView1;

    public SubjectViewHolder(View v) {
        super(v);
        label1 = v.findViewById(R.id.subject);
        label2 = v.findViewById(R.id.numOfFormulas);
        imageView1 = v.findViewById(R.id.subject_photo);
    }

    public TextView getLabel1() {
        return label1;
    }

    public void setLabel1(TextView label1) {
        this.label1 = label1;
    }

    public TextView getLabel2() {
        return label2;
    }

    public void setLabel2(TextView label2) {
        this.label2 = label2;
    }

    public ImageView getImageView() {
        return imageView1;
    }

    public void setImageView1(ImageView imageView1) {
        this.imageView1 = imageView1;
    }
}