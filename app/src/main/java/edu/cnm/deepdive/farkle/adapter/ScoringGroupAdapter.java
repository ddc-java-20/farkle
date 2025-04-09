package edu.cnm.deepdive.farkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import edu.cnm.deepdive.farkle.R;
import java.util.List;

public class ScoringGroupAdapter extends BaseAdapter {

  private final Context context;
  private final List<int[]> scoringGroups;
  private final LayoutInflater inflater;


  public ScoringGroupAdapter(Context context, List<int[]> scoringGroups, LayoutInflater inflater) {
    this.context = context;
    this.scoringGroups = scoringGroups;
    this.inflater = inflater;
  }

  @Override
  public int getCount() {
    return scoringGroups.size();
  }

  @Override
  public Object getItem(int position) {
    return scoringGroups.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_scoring_group, parent, false);

      // Initialize the holder and attach the views
      holder = new ViewHolder();
      holder.diceViews = new ImageView[] {
          convertView.findViewById(R.id.die_1),
          convertView.findViewById(R.id.die_2),
          convertView.findViewById(R.id.die_3),
          convertView.findViewById(R.id.die_4),
          convertView.findViewById(R.id.die_5),
          convertView.findViewById(R.id.die_6)
      };

      convertView.setTag(holder); // Store the holder as a tag on the view for future reuse.
    } else {
      // Reuse the already-created view
      holder = (ViewHolder) convertView.getTag(); // Retrieve holder from tag.
    }

    // Populate dice values into the view
    populateRow(holder, scoringGroups.get(position));

    return convertView;
  }

  private void populateRow(ViewHolder holder, int[] diceValues) {
    for (int i = 0; i < holder.diceViews.length; i++) {
      if (i < diceValues.length) {
        holder.diceViews[i].setVisibility(View.VISIBLE);
        holder.diceViews[i].setImageResource(getDiceImage(diceValues[i]));
      } else {
        holder.diceViews[i].setVisibility(View.GONE);
      }
    }
  }

  private int getDiceImage(int value) {
    switch (value) {
      case 1: return R.drawable.dice_face_1;
      case 2: return R.drawable.dice_face_2;
      case 3: return R.drawable.dice_face_3;
      case 4: return R.drawable.dice_face_4;
      case 5: return R.drawable.dice_face_5;
      case 6: return R.drawable.dice_face_6;
      default: throw new IllegalArgumentException("Invalid dice value: " + value);
    }
  }

  public void updateScoringGroups(List<int[]> newGroups) {
    scoringGroups.clear();
    scoringGroups.addAll(newGroups);
    notifyDataSetChanged();
  }

  // View holder to store references to the dice views.
  private static class ViewHolder {
    ImageView[] diceViews;
  }

}

