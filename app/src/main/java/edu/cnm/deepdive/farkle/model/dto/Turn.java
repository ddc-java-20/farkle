package edu.cnm.deepdive.farkle.model.dto;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.UUID;

public class Turn {
  //add @expose to all
  @Expose(serialize = false)
  private UUID key;

  @Expose(serialize = false)
  private Instant startTime;
  private boolean finished;
  private User user;
  private boolean farkle;
  private Roll currentRoll;
  private int turnScore;

}


/*
  "currentTurn": {
      "key": "aac4ec7d-77c0-4bad-8143-c396ec149779",
      "startTime": "2025-04-03T21:57:12.066406Z",
      "finished": false,
      "user": {
      "key": "6d5980df-c935-46b8-8e9f-6e3f50fa62e3",
      "displayName": "Mine"
      },
      "farkle": false,
      "currentRoll": {
      "rollScore": 0,      ***** int
      "farkle": false,      boolean
      "numberDice": 6,        int
      "dice": [         class- Die  -- value & group are ints
      {
      "value": 2,
      "group": 0
      },
      {
      "value": 3,
      "group": 0
      },
      {
      "value": 3,
      "group": 0
      },
      {
      "value": 5,
      "group": 0
      },
      {
      "value": 5,
      "group": 0
      },
      {
      "value": 6,
      "group": 0
      }
      ],
      "timestamp": "2025-04-03T21:57:12.067398Z"
      },
      "turnScore": 0
      },
      }
      "rollCount": 1*/
