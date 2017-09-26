package com.castleglobal.assignment.Objects;

/**
 * Created by naveenkumar on 24/09/17.
 */

public class UserRating {

    private String aggregateRating;
    private String ratingText;
    private String ratingColor;
    private String votes;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserRating() {
    }

    /**
     *
     * @param ratingColor
     * @param votes
     * @param aggregateRating
     * @param ratingText
     */
    public UserRating(String aggregateRating, String ratingText, String ratingColor, String votes) {
        super();
        this.aggregateRating = aggregateRating;
        this.ratingText = ratingText;
        this.ratingColor = ratingColor;
        this.votes = votes;
    }

    public String getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(String aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

}