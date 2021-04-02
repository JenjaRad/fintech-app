package com.eugene.web.forms;

public class Success<T> extends Result {
    private final T value;
    private String message;

    public Success(T value) {
        this.value = value;
    }
  public void setMessage(String message){
        this.message = message;
  }
    public T getValue(){
        return value;
    }
}
