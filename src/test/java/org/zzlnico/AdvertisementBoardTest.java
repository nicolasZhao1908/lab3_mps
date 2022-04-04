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
    String advertiserName = "THE Company";
    Advertisement advertisement1 = new Advertisement("title1","text",advertiserName);
    Advertisement advertisement2 = new Advertisement("title2","text",advertiserName);
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
    String advertiserName = "THE Company";
    Advertisement advertisement1 = new Advertisement("title","text",advertiserName);
    Advertisement advertisement2 = new Advertisement("title","text",advertiserName);
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
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {
    /**
     * Tiene sentido usar test double tipo spy porque spy nos permite cambiar el comportamiento del método
     * numberOfPublishedAdvertisements() de la clase AdvertisementBoard para que retorne 20 cuando se llame la función
      */
    String advertiserName = "Tim O'Theo";
    AdvertisementBoard advertisementBoard = new AdvertisementBoard();
    AdvertisementBoard advertisementBoardSpy = Mockito.spy(advertisementBoard);
    AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
    PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);
    Advertisement advertisement = new Advertisement("title","text",advertiserName);

    Mockito.when(advertisementBoardSpy.numberOfPublishedAdvertisements()).thenReturn(20);

    assertThrows(FullBoardException.class, () -> advertisementBoardSpy.publish(advertisement,advertiserDatabase,paymentDatabase));
  }
}