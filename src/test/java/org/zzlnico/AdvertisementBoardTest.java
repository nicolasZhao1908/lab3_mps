package org.zzlnico;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisementBoardTest {
  @Test
  public void ABoardHasAnAdvertisementWhenItIsCreated() {
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();
    int expectedValue, actualValue;
    expectedValue = 1;
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();
    assertEquals(actualValue,expectedValue);
  }

  @Test
  public void PublishAnAdvertisementByTheCompanyIncreasesTheNumberOfAdvertisementsByOne() {
    Advertisement advertisement = new Advertisement("title","text","THE Company");
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();
    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);
    int expectedValue, actualValue;
    expectedValue = advertisementBoard.numberOfPublishedAdvertisements()+1;
    advertisementBoard.publish(advertisement,advertiserDatabase,paymentDatabase);
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();
    assertEquals(actualValue,expectedValue);
  }

  @Test
  public void WhenAnAdvertiserHasNoFoundsTheAdvertisementIsNotPublished() {
    Advertisement advertisement = new Advertisement("title","text","Pepe Gotera y Otilio");
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();

    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

    Mockito.when(advertiserDatabase.findAdviser("Pepe Gotera y Otilio")).thenReturn(true);
    Mockito.when(paymentDatabase.advertiserHasFunds("Pepe Gotera y Otilio")).thenReturn(false);

    int expectedValue, actualValue;
    expectedValue = advertisementBoard.numberOfPublishedAdvertisements();
    advertisementBoard.publish(advertisement,advertiserDatabase,paymentDatabase);
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();
    assertEquals(actualValue,expectedValue);

  }

  @Test
  public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {

  }

  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {  }

  @Test
  public void AnExistingAdvertisementIsNotPublished() {}

  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {}
}