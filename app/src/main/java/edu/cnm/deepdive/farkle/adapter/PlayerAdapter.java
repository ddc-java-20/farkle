package edu.cnm.deepdive.farkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.farkle.R;
import edu.cnm.deepdive.farkle.databinding.ItemPlayerBinding;
import edu.cnm.deepdive.farkle.model.dto.GamePlayer;
import edu.cnm.deepdive.farkle.model.dto.Turn;
import java.util.List;

public class PlayerAdapter extends ArrayAdapter<GamePlayer> {

  private final LayoutInflater inflater;
  private final Turn currentTurn;

  public PlayerAdapter(@NonNull Context context,
      @NonNull List<GamePlayer> players, Turn currentTurn) {
    super(context, R.layout.item_player, players);
    inflater = LayoutInflater.from(context);
    this.currentTurn = currentTurn;
  }


  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    ItemPlayerBinding binding = (convertView == null)
        ? ItemPlayerBinding.inflate(inflater, parent, false)
        : ItemPlayerBinding.bind(convertView);

    GamePlayer player = getItem(position);
    if (currentTurn != null && currentTurn.getUser().equals(player.getUser())) {

      binding.getRoot().setBackgroundColor(getContext().getColor(R.color.active_player_highlight));
    } else {
      binding.getRoot().setBackgroundColor(getContext().getColor(R.color.default_background));
    }
    binding.displayName.setText(player.getUser().getDisplayName());
    binding.score.setText(String.valueOf(player.getScore()));
    return binding.getRoot();
  }
}


