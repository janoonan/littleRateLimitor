package com.c1sc0.cloudcalling.edge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

interface Action {
  int getTimeStamp();
  void execute();
}

public class SimpleRateLimiter {

  List<Integer> recentlyTransmittedPackets = Collections.synchronizedList(new LinkedList<>());
  int recentInMilliseconds = 1000;
  int limit = 10;

  public SimpleRateLimiter(int recentInMilliseconds, int limit)
  {
    this.recentInMilliseconds = recentInMilliseconds;
    this.limit = limit;
  }

  public void executeActionIfCapacity(Action action) {
    if(checkIfCapacityForAction(action)) {
      recordAction(action);
      action.execute();
    }
  }

  private void removeOldActionRecords(int currentTime) {
    recentlyTransmittedPackets.removeIf(n -> (n + recentInMilliseconds > currentTime));
  }
  private boolean hasCapacity()
  {
    return recentlyTransmittedPackets.size() < limit;
  }

  private boolean checkIfCapacityForAction(Action action) {
    removeOldActionRecords(action.getTimeStamp());
    return hasCapacity();
  }

  private void recordAction(Action action) {
    recentlyTransmittedPackets.add(action.getTimeStamp());
  }

}
