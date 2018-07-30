package com.rawat.ashish.game.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserDetails {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("is_verified")
        @Expose
        private String isVerified;
        @SerializedName("ads_num")
        @Expose
        private String adsNum;
        @SerializedName("ads_views")
        @Expose
        private String adsViews;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("referral_code")
        @Expose
        private String referralCode;
        @SerializedName("refer_cash")
        @Expose
        private String referCash;
        @SerializedName("earned_cash")
        @Expose
        private String earnedCash;
        @SerializedName("net_balance")
        @Expose
        private String netBalance;
        @SerializedName("stage")
        @Expose
        private String stage;
        @SerializedName("is_active")
        @Expose
        private Object isActive;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("country_code")
        @Expose
        private String countryCode;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(String isVerified) {
            this.isVerified = isVerified;
        }

        public String getAdsNum() {
            return adsNum;
        }

        public void setAdsNum(String adsNum) {
            this.adsNum = adsNum;
        }

        public String getAdsViews() {
            return adsViews;
        }

        public void setAdsViews(String adsViews) {
            this.adsViews = adsViews;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public String getReferCash() {
            return referCash;
        }

        public void setReferCash(String referCash) {
            this.referCash = referCash;
        }

        public String getEarnedCash() {
            return earnedCash;
        }

        public void setEarnedCash(String earnedCash) {
            this.earnedCash = earnedCash;
        }

        public String getNetBalance() {
            return netBalance;
        }

        public void setNetBalance(String netBalance) {
            this.netBalance = netBalance;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public Object getIsActive() {
            return isActive;
        }

        public void setIsActive(Object isActive) {
            this.isActive = isActive;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

    }
}
