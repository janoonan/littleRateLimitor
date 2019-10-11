package com.c1sc0.cloudcalling.edge;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;

public class SimpleRateLimiterTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void constructorInputPositivePositiveOutputNotNull() {

    final int arg0 = 10;
    final int arg1 = 10;

    final SimpleRateLimiter actual = new SimpleRateLimiter(arg0, arg1);

    Assert.assertNotNull(actual);
    Assert.assertEquals(10, actual.recentInMilliseconds);
    Assert.assertEquals(10, actual.limit);
    Assert.assertNotNull(actual.recentlyTransmittedPackets);
  }

  @Test
  public void executeActionIfCapacityInputNullOutputNullPointerException() {

    final SimpleRateLimiter thisObj = new SimpleRateLimiter(10, 10);
    final Action arg0 = null;

    thrown.expect(NullPointerException.class);
    thisObj.executeActionIfCapacity(arg0);
  }

  @PrepareForTest({Action.class})
  @Test
  public void executeActionIfCapacityInputNotNullOutputVoid() {

    final int arg0 = 10;
    final int arg1 = 10;

    final SimpleRateLimiter simpleRateLimiter = new SimpleRateLimiter(arg0, arg1);
    simpleRateLimiter.recentInMilliseconds = 0;
    final ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(0);
    simpleRateLimiter.recentlyTransmittedPackets = arrayList;
    simpleRateLimiter.limit = 1;
    final Action action = PowerMockito.mock(Action.class);
    PowerMockito.when(action.getTimeStamp()).thenReturn(0).thenReturn(0);

    simpleRateLimiter.executeActionIfCapacity(action);

    final ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
    arrayList1.add(0);
    Assert.assertEquals(arrayList1, simpleRateLimiter.recentlyTransmittedPackets);
  }
}
