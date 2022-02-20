package com.mc2022.template;



public class ModelNews {
    private String mTextTitle;
    private String mTextBody;
    private String mStartS = "Start Service";
    private String mStopS = "Stop Service";

    public String getmTextBody() {
        return mTextBody;
    }

    public String getmTextTitle() {
        return mTextTitle;
    }

    public String getmStartS() {
        return mStartS;
    }

    public String getmStopS() {
        return mStopS;
    }

    public void setmTextBody(String mTextBody) {
        this.mTextBody = mTextBody;
    }

    public void setmTextTitle(String mTextTitle) {
        this.mTextTitle = mTextTitle;
    }
}
