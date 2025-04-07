package edu.cnm.deepdive.farkle.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.farkle.databinding.FragmentGameBinding;
import edu.cnm.deepdive.farkle.model.dto.Die;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.viewmodel.GameViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AndroidEntryPoint
public class GameFragment extends Fragment {

  private static final String TAG = HomeFragment.class.getSimpleName();
  private FragmentGameBinding binding;
  private GameViewModel viewModel;
  private boolean finished = false;
  private List<int[]> frozenGroups = new ArrayList<>();

  public GameFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGameBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = new ViewModelProvider(this).get(GameViewModel.class);

    viewModel.getGame().observe(getViewLifecycleOwner(), this::updateUi);

    binding.endTurnButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
      finished = isChecked; // Update e
    });

    binding.selectGroupButton.setOnClickListener((v) -> {
      List<Integer> selectedDice = new ArrayList<>();
      ToggleButton[] diceButtons = {
          binding.dice1,
          binding.dice2,
          binding.dice3,
          binding.dice4,
          binding.dice5,
          binding.dice6
      };

      for (int i = 0; i < diceButtons.length; i++) {
        if (diceButtons[i].isChecked()) {
          selectedDice.add(i + 1);
        }
      }
      int[] diceGroup = selectedDice.stream().mapToInt(Integer::intValue).toArray();
      frozenGroups.add(diceGroup);
      for (ToggleButton button : diceButtons) {
        button.setChecked(false); // Reset toggles.
      }
      Snackbar.make(binding.getRoot(),
          "Scoring group added. Select more dice or click Submit Choice.",
          Snackbar.LENGTH_SHORT).show();
    });

    binding.submitChoiceButton.setOnClickListener((v) -> {
      UUID gameKey = getGameKey(); // Retrieve the current game key.
      UUID userId = getUserId(); // Retrieve the current UserId
      int[][] frozenGroups = getFrozenGroups();

      viewModel.submitRollChoice(gameKey, userId, frozenGroups, finished);
      frozenGroups = new int[0][0];

    });

    binding.quitButton.setOnClickListener(v -> {
      Navigation.findNavController(binding.getRoot()
      ).navigate(GameFragmentDirections.navigateToHomeFragment());
    });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  private void updateUi(Game game) {
    List<Die> dice = game.getCurrentTurn().getCurrentRoll().getDice();
    ToggleButton[] diceButtons = {
        binding.dice1,
        binding.dice2,
        binding.dice3,
        binding.dice4,
        binding.dice5,
        binding.dice6
    };

    for (int i = 0; i < diceButtons.length; i++) {
      if (i < dice.size()) {
        Die die = dice.get(i);

        diceButtons[i].setTextOn(String.valueOf(die.getValue()));
        diceButtons[i].setTextOff(String.valueOf(die.getValue()));
        diceButtons[i].setChecked(false);

        diceButtons[i].setVisibility(View.VISIBLE);
      }
    }
  }

  private int[][] getFrozenGroups() {
    return frozenGroups.toArray(new int[0][0]); // Convert to 2D array for API.
  }

  private UUID getGameKey() {
    // Return the current game's external key (mock value for now).
    return UUID.randomUUID();
  }

  private UUID getUserId() {
    String userIdString = PreferenceManager.getDefaultSharedPreferences(requireContext())
        .getString("user_id_key", null);
    return UUID.fromString(userIdString);
  }

}