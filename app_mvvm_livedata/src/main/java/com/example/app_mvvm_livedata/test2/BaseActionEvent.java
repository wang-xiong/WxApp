package com.example.app_mvvm_livedata.test2;

/**
 * 用于向View层传递Action的Model
 */
public class BaseActionEvent extends BaseEvent{

    public static final int SHOW_LOADING_DIALOG = 1;
    public static final int DISMISS_LOADING_DIALOG = 2;
    public static final int SHOW_TOAST = 3;
    public static final int FINISH = 4;
    public static final int FINISH_WITH_RESULT_OK = 5;

    public BaseActionEvent(int action) {
        super(action);
    }

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
