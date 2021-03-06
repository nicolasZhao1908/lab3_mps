package org.zzlnico;

import java.util.ArrayList;
import java.util.List;

/**
 * Board to publish advertisements
 *
 @author Antonio J. Nebro
 */
public class AdvertisementBoard {
  private static int MAX_BOARD_SIZE = 20 ;
  private List<Advertisement> advertisementList;

  /**
   * Constructor
   */
  public AdvertisementBoard() {
    advertisementList = new ArrayList<>();
    Advertisement advertisement = new Advertisement(
        "Welcome",
        "This board is intended for your advertisements",
        "THE Company");
    advertisementList.add(advertisement);
  }

  /**
   * Returns the number of advertisements published
   * @return
   */
  public int numberOfPublishedAdvertisements() {
    return advertisementList.size();
  }

  /**
   * Publishes an advertisement. The advertiser called "THE Company" is the owner of the advertisement
   * board, so it can publish freely, without any constraints
   *
   */
  public void publish(Advertisement advertisement,
      AdvertiserDatabase advertiserDatabase,
      PaymentDatabase paymentDatabase) {

    if (!isInList(advertisement)){
      if (advertisement.advertiser.equals("THE Company"))
        advertisementList.add(advertisement);
      else {
        if(numberOfPublishedAdvertisements() >= MAX_BOARD_SIZE)
          throw new FullBoardException();
        if ((advertiserDatabase.findAdviser(advertisement.advertiser)) &&
                (paymentDatabase.advertiserHasFunds(advertisement.advertiser))) {
          advertisementList.add(advertisement);
          paymentDatabase.advertisementPublished(advertisement.advertiser);
        }
      }
    }
  }

  /**
   * Finds an advertisement form its title
   *
   * @param
   * @return
   */
  public Advertisement findByTitle(String title) {
    Advertisement result = null;
    for (Advertisement advertisement : advertisementList) {
      if (advertisement.title.equals(title))
         result = advertisement;
    }
    return result;
  }

  /**
   * Deletes an advertisement from its name and advertiser
   * @param title
   * @param advertiserName
   */
  public void deleteAdvertisement(String title, String advertiserName) {
    for (Advertisement advertisement : advertisementList) {
      if ((advertisement.title.equals(title) && (advertisement.advertiser.equals(advertiserName))))
        advertisementList.remove(advertisement);
    }
  }
  private boolean sameAdvertisement(Advertisement ad1, Advertisement ad2){
    return ad1.title.equals(ad2.title) && ad1.advertiser.equals(ad2.advertiser);
  }

  private boolean isInList(Advertisement advertisement){
    int index = 0;
    while (index < advertisementList.size() && !sameAdvertisement(advertisementList.get(index),advertisement)){
      index++;
    }
    return index < advertisementList.size();
  }
}
