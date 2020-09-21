package com.thenneem.omnitrail.rest;

import com.google.gson.annotations.SerializedName;

public class UploadResult {
    Integer status;
    Data data;

    public UploadResult(Integer status, Data data) {
        this.status = status;
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data{
        @SerializedName("fileurl")
        String fileUrl;

        public Data(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileUrl() {
            return fileUrl;
        }
    }
}
