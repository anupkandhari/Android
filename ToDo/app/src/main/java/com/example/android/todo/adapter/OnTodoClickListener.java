package com.example.android.todo.adapter;


import com.example.android.todo.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
}
