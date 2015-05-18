package com.example.easyhome;

//this is an interface that handles the asynctask callbacks
public interface AsyncTaskCompleteListener<T>
{
 public void onTaskComplete(T result);
}