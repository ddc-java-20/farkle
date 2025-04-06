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
import edu.cnm.deepdive.farkle.R;
import edu.cnm.deepdive.farkle.databinding.FragmentGameBinding;
import edu.cnm.deepdive.farkle.databinding.FragmentHomeBinding;
import edu.cnm.deepdive.farkle.model.dto.Die;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.entity.Roll;
import edu.cnm.deepdive.farkle.model.entity.User;
import edu.cnm.deepdive.farkle.viewmodel.GameViewModel;
import edu.cnm.deepdive.farkle.viewmodel.LoginViewModel;
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
      List<int[]> selectedDiceGroups = new ArrayList<>();
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
          selectedDiceGroups.add(new int[]{i + 1});
        }
      }
      frozenGroups.add(selectedDiceGroups.toArray(new int[0]));
      for (ToggleButton button : diceButtons) {
        button.setChecked(false); // Reset toggles.
      }
      Snackbar.make(binding.getRoot(), "Scoring group added. Select more dice or click Submit Choice.", Snackbar.LENGTH_SHORT).show();
    });

    binding.submitChoiceButton.setOnClickListener((v) -> {
      UUID gameKey = getGameKey(); // Retrieve the current game key.
      UUID userId = getUserId(); // Retrieve the current UserId
      int[][] frozenGroups = getFrozenGroups();

      viewModel.submitRollChoice(gameKey, frozenGroups, finished, new User(userId));
      // TODO: 4/6/2025 clear frozenGroups
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
    List<Roll.Die> dice = game.getCurrentTurn().getCurrentRoll().getDice();
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
        Roll.Die die = dice.get(i);

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

  private List<Die> getSelectedDice() {
    List<Die> selectedDice = new ArrayList<>();

    // Iterate through dice views to find selected dice (pseudo-logic).
    // Replace with logic to extract selected dice from UI components.
    return selectedDice;
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