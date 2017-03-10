package com.cindasoft.demo1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by king on 16-8-23.
 */



public class YouduResponse {
    public static  class Status{
        private int code;
        private String message;
        private String createdAt;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createAt) {
            this.createdAt = createAt;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    @JsonIgnoreProperties(value = { "orgId","mobile","phone","email","position"})
    public static class YouduUserInfo {
        private String account;
        private String chsName;
        private String engName;
        private Integer gender;
        private Integer orgId;
        private String mobile;
        private String phone;
        private String email;
        private String position;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getChsName() {
            return chsName;
        }

        public void setChsName(String chsName) {
            this.chsName = chsName;
        }

        public String getEngName() {
            return engName;
        }

        public void setEngName(String engName) {
            this.engName = engName;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getOrgId() {
            return orgId;
        }

        public void setOrgId(Integer orgId) {
            this.orgId = orgId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
    private Status status;
    private YouduUserInfo userInfo;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public YouduUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(YouduUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
