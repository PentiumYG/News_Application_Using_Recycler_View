package com.mc2022.template;



public class ModelNews {
    private String mTextTitle;
    private String mTextBody;
    private String mImageUrl;
    private String mSno;



    public ModelNews(String mTextTitle, String mTextBody, String mImageUrl, String mSno) {
        this.mTextTitle = mTextTitle;
        this.mTextBody = mTextBody;
        this.mImageUrl = mImageUrl;
        this.mSno = mSno;
    }

    public String getmSno() { return mSno; }

    public void setmSno(String mSno) { this.mSno = mSno; }

    public String getmTextBody() {
        return mTextBody;
    }

    public String getmTextTitle() {
        return mTextTitle;
    }

    public String getmImageUrl() { return mImageUrl; }

    public void setmImageUrl(String mImageUrl) { this.mImageUrl = mImageUrl; }

    public void setmTextBody(String mTextBody) {
        this.mTextBody = mTextBody;
    }

    public void setmTextTitle(String mTextTitle) {
        this.mTextTitle = mTextTitle;
    }
}
