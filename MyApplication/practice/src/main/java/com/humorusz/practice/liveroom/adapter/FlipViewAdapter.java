package com.humorusz.practice.liveroom.adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;


    import java.util.List;

    import com.humorusz.practice.R;

public class FlipViewAdapter extends ArrayAdapter<String> {
  private int resource;

  public FlipViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
    super(context, resource, objects);
    this.resource = resource;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View v;
    if (convertView == null) {
      v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    } else {
      v = convertView;
    }
    TextView tv = v.findViewById(R.id.flip_item_text);
    tv.setText(getItem(position));
    return v;
  }
}
