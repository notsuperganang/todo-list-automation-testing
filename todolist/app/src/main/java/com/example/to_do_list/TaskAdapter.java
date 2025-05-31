package com.example.to_do_list;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;
    private TaskAdapterListener listener;

    public interface TaskAdapterListener {
        void onTaskDeleted(Task task);
        void onTaskStatusChanged(Task task);
    }

    public TaskAdapter(Context context, List<Task> tasks, TaskAdapterListener listener) {
        super(context, R.layout.task_item, tasks);
        this.context = context;
        this.tasks = tasks;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.task_item, parent, false);

            holder = new ViewHolder();
            holder.checkBox = view.findViewById(R.id.cb_task_done);
            holder.textView = view.findViewById(R.id.tv_task_text);
            holder.deleteButton = view.findViewById(R.id.btn_delete_task);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Task task = tasks.get(position);

        // Set task text
        holder.textView.setText(task.getText());

        // Set checkbox status
        holder.checkBox.setChecked(task.isDone());

        // Apply strikethrough if task is done
        if (task.isDone()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView.setAlpha(0.6f);
        } else {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.textView.setAlpha(1.0f);
        }

        // Set checkbox listener
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
            if (listener != null) {
                listener.onTaskStatusChanged(task);
            }
            notifyDataSetChanged();
        });

        // Set delete button listener
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskDeleted(task);
            }
        });

        return view;
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView textView;
        Button deleteButton;
    }
}