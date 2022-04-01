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
    assertEquals(expectedValue, actualValue);
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

    assertEquals(expectedValue, actualValue);
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

    assertEquals(expectedValue, actualValue);

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
  }

  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {
    Advertisement advertisement1 = new Advertisement("title1","text","THE Company");
    Advertisement advertisement2 = new Advertisement("title2","text","THE Company");
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();
    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

    int expectedValue, actualValue;
    advertisementBoard.publish(advertisement1,advertiserDatabase,paymentDatabase);
    advertisementBoard.publish(advertisement2,advertiserDatabase,paymentDatabase);
    expectedValue = advertisementBoard.numberOfPublishedAdvertisements()-1;
    advertisementBoard.deleteAdvertisement(advertisement1.title,advertisement1.advertiser);
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();

    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void AnExistingAdvertisementIsNotPublished() {
    Advertisement advertisement1 = new Advertisement("title","text","THE Company");
    Advertisement advertisement2 = new Advertisement("title","text","THE Company");
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();
    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

    int expectedValue, actualValue;
    advertisementBoard.publish(advertisement1,advertiserDatabase,paymentDatabase);
    expectedValue = advertisementBoard.numberOfPublishedAdvertisements();
    advertisementBoard.publish(advertisement2,advertiserDatabase,paymentDatabase);
    actualValue = advertisementBoard.numberOfPublishedAdvertisements();

    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {}
}