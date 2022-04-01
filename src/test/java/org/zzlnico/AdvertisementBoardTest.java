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
    String advertiserName = "Pepe Gotera y Otilio";
    Advertisement advertisement = new Advertisement("title","text",advertiserName);
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();

    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

    Mockito.when(advertiserDatabase.findAdviser(advertiserName)).thenReturn(true);
    Mockito.when(paymentDatabase.advertiserHasFunds(advertiserName)).thenReturn(false);

    int expectedValue, actualValue;
    expectedValue = advertisementBoard.numberOfPublishedAdvertisements();
    advertisementBoard.publish(advertisement,advertiserDatabase,paymentDatabase);
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();
    assertEquals(actualValue,expectedValue);

  }

  @Test
  public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {
    String advertiserName = "Robin Robot";
    Advertisement advertisement = new Advertisement("title","text",advertiserName);
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();

    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

    Mockito.when(advertiserDatabase.findAdviser(advertiserName)).thenReturn(true);
    Mockito.when(paymentDatabase.advertiserHasFunds(advertiserName)).thenReturn(true);

    advertisementBoard.publish(advertisement,advertiserDatabase,paymentDatabase);
    Mockito.verify(paymentDatabase).advertisementPublished(advertiserName);
    Mockito.verify(paymentDatabase,Mockito.times(1)).advertisementPublished(advertiserName);
  }

  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {  }

  @Test
  public void AnExistingAdvertisementIsNotPublished() {}

  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {}
}