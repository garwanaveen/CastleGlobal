package com.castleglobal.assignment.Objects;

import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by naveenkumar on 24/09/17.
 */

public class Restaurant {

    private String apikey;
    private int id;
    private String name;
    private String url;
    private Location location;
    private int switchToOrderMenu;
    private String cuisines;
    private int averageCostForTwo;
    private int priceRange;
    private String currency;
    private String offers = null;
    private String thumb;
    private UserRating userRating;
    private String photosUrl;
    private String menuUrl;
    private String featuredImage;
    private Boolean hasOnlineDelivery;
    private boolean isDeliveringNow;
    private String deeplink;
    private String orderUrl;
    private String orderDeeplink;
    private Boolean hasTableBooking;
    private String eventsUrl;
    private Boolean isFav;

    /**
     * No args constructor for use in serialization
     *
     */
    public Restaurant() {
    }

    /**
     *
     * @param cuisines
     * @param eventsUrl
     * @param hasTableBooking
     * @param orderUrl
     * @param userRating
     * @param location
     * @param hasOnlineDelivery
     * @param offers
     * @param orderDeeplink
     * @param isDeliveringNow
     * @param url
     * @param switchToOrderMenu
     * @param apikey
     * @param currency
     * @param id
     * @param averageCostForTwo
     * @param priceRange
     * @param menuUrl
     * @param photosUrl
     * @param name
     * @param deeplink
     * @param thumb
     * @param featuredImage
     */
    public Restaurant(String cuisines, String eventsUrl, boolean hasTableBooking,
                      String orderUrl, UserRating userRating, Location location,
                      boolean hasOnlineDelivery, String offers, String orderDeeplink,
                      boolean isDeliveringNow, String url, int switchToOrderMenu,
                      String apikey, String currency, int id,
                      int averageCostForTwo, int priceRange, String menuUrl,
                      String photosUrl, String name, String deeplink,
                      String thumb, String featuredImage) {
        super();
        this.apikey = apikey;
        this.id = id;
        this.name = name;
        this.url = url;
        this.location = location;
        this.switchToOrderMenu = switchToOrderMenu;
        this.cuisines = cuisines;
        this.averageCostForTwo = averageCostForTwo;
        this.priceRange = priceRange;
        this.currency = currency;
        this.offers = offers;
        this.thumb = thumb;
        this.userRating = userRating;
        this.photosUrl = photosUrl;
        this.menuUrl = menuUrl;
        this.featuredImage = featuredImage;
        this.hasOnlineDelivery = hasOnlineDelivery;
        this.isDeliveringNow = isDeliveringNow;
        this.deeplink = deeplink;
        this.orderUrl = orderUrl;
        this.orderDeeplink = orderDeeplink;
        this.hasTableBooking = hasTableBooking;
        this.eventsUrl = eventsUrl;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getSwitchToOrderMenu() {
        return switchToOrderMenu;
    }

    public void setSwitchToOrderMenu(int switchToOrderMenu) {
        this.switchToOrderMenu = switchToOrderMenu;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public int getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(int averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public Boolean getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(Boolean hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public Boolean getIsDeliveringNow() {
        return isDeliveringNow;
    }

    public void setIsDeliveringNow(Boolean isDeliveringNow) {
        this.isDeliveringNow = isDeliveringNow;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getOrderDeeplink() {
        return orderDeeplink;
    }

    public void setOrderDeeplink(String orderDeeplink) {
        this.orderDeeplink = orderDeeplink;
    }

    public Boolean getHasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(Boolean hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public Boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }

//    public static Comparator<Restaurant> FruitNameComparator
//            = new Comparator<Restaurant>() {
//
//        public int compare(Restaurant restaurant1, Restaurant restaurant2) {
//
//            String fruitName1 = restaurant1.get.toUpperCase();
//            String fruitName2 = restaurant2.getFruitName().toUpperCase();
//
//            //ascending order
//            return fruitName1.compareTo(fruitName2);
//
//            //descending order
//            //return fruitName2.compareTo(fruitName1);
//        }
//
//    };

}