package edu.cnm.deepdive.farkle.controller;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.farkle.adapter.PlayerAdapter;
import edu.cnm.deepdive.farkle.databinding.FragmentGameBinding;
import edu.cnm.deepdive.farkle.model.dto.Die;
import edu.cnm.deepdive.farkle.model.dto.Game;
import edu.cnm.deepdive.farkle.model.dto.State;
import edu.cnm.deepdive.farkle.model.dto.User;
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
  private ImageButton[] diceButtons;
  private Game game;
  private User user;

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGameBinding.inflate(inflater, container, false);

    diceButtons = new ImageButton[]{
        binding.dice1,
        binding.dice2,
        binding.dice3,
        binding.dice4,
        binding.dice5,
        binding.dice6
    };

    bindEndTurnButton();

    bindSelectGroupButton();

    bindSubmitChoice();

    bindGoHomeButton();

    bindClearButton();

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = new ViewModelProvider(this).get(GameViewModel.class);

    getLifecycle().addObserver(viewModel);

    viewModel.startOrJoin();

    LifecycleOwner owner = getViewLifecycleOwner();
    viewModel.getGame().observe(owner, this::updateUi);

    viewModel.getUser().observe(owner, (user) -> {
      this.user = user;
    });

  }

  private void bindSelectGroupButton() {
    binding.selectGroupButton.setOnClickListener((v) -> {
      List<Integer> selectedDice = new ArrayList<>();
      List<Die> dice = game.getCurrentTurn().getLastRoll().getDice();
      for (int i = 0; i < diceButtons.length; i++) {
        if (diceButtons[i].getTag() != null && (Boolean) diceButtons[i].getTag()) {
          selectedDice.add(dice.get(i).getValue());
        }
      }
      int[] diceGroup = selectedDice.stream().mapToInt(Integer::intValue).toArray();
      frozenGroups.add(diceGroup);
      for (ImageButton button : diceButtons) {
        button.setTag(false); // Reset toggles.
      }
      Snackbar.make(binding.getRoot(),
          "Scoring group added. Select more dice or click Submit Choice.",
          Snackbar.LENGTH_SHORT).show();
    });
  }

  private void bindEndTurnButton() {
    binding.endTurnButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
      finished = isChecked; // Update e
    });
  }

  private void bindGoHomeButton() {
    binding.quitButton.setOnClickListener(v -> {
      Navigation.findNavController(binding.getRoot()
      ).navigate(GameFragmentDirections.navigateToHomeFragment());
    });
  }

  private void bindSubmitChoice() {
    binding.submitChoiceButton.setOnClickListener((v) -> {
      int[][] frozenGroups = getFrozenGroups();

      viewModel.submitRollChoice(frozenGroups, finished);
    });
  }

  private void bindClearButton() {
    binding.clearButton.setOnClickListener((v) -> {
      frozenGroups.clear();
    });
  }

  private void updateUi(Game game) {
    this.game = game;
    PlayerAdapter adapter = new PlayerAdapter(requireContext(), game.getPlayers(),
        game.getCurrentTurn());
    binding.players.setAdapter(adapter);
    switch (game.getState()) {
      case PRE_GAME:
        for (ImageButton button : diceButtons) {
          button.setImageDrawable(null);
        }
        break;
      case IN_PLAY:
        if (game.getCurrentTurn().getLastRoll() != null && game.getCurrentTurn().getUser()
            .equals(user)) {
          List<Die> dice = game.getCurrentTurn().getLastRoll().getDice();

          for (int i = 0; i < diceButtons.length; i++) {
            if (i < dice.size()) {
              Die die = dice.get(i);

              ImageButton button = diceButtons[i];
              button.setVisibility(View.VISIBLE);
              button.setEnabled(true);
              button.getDrawable().setLevel(die.getValue());
              button.setOnClickListener((v) -> {
                Boolean selected = (Boolean) button.getTag();
                if (selected == null || !selected) {
                  button.getDrawable().setAlpha(64);
                  button.setTag(true);
                } else {
                  button.getDrawable().setAlpha(255);
                  button.setTag(false);
                }

              });

            } else {
              diceButtons[i].setVisibility(View.INVISIBLE); // Hide button
              diceButtons[i].setEnabled(false);
            }
          }
        } else {
          for (ImageButton button : diceButtons) {
            button.setVisibility(View.INVISIBLE);
          }
        }
        break;
      case FINISHED:
        break;

      default:
        throw new IllegalStateException("Unexpected game state: " + game.getState());

    }
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  private int[][] getFrozenGroups() {
    return frozenGroups.toArray(new int[0][0]); // Convert to 2D array for API.
  }

}