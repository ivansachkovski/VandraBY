package com.example.vandraby.listeners;

import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.example.vandraby.callbacks.DragAndDropSightCallback;

import org.jetbrains.annotations.NotNull;

public class DragAndDropSightListener implements View.OnDragListener {

    private final DragAndDropSightCallback onExitCallback;

    private float startX;
    private float finishX;

    public DragAndDropSightListener(DragAndDropSightCallback onExitCallback) {
        this.onExitCallback = onExitCallback;
    }

    @Override
    public boolean onDrag(View view, @NotNull DragEvent event) {

        switch (event.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:

                startX = event.getX();
                // view.setVisibility(View.INVISIBLE);
                // Toast.makeText(view.getContext(), "ACTION_DRAG_STARTED, " + pressedX, Toast.LENGTH_SHORT).show();

                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                // Toast.makeText(view.getContext(), "ACTION_DRAG_ENTERED", Toast.LENGTH_SHORT).show();

                return true;

            case DragEvent.ACTION_DROP:

                // Toast.makeText(view.getContext(), "ACTION_DROP, " +  + event.getX(), Toast.LENGTH_SHORT).show();
                finishX = event.getX();
                // view.setVisibility(View.VISIBLE);

                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Toast.makeText(view.getContext(), "ACTION_DRAG_EXITED", Toast.LENGTH_SHORT).show();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Toast.makeText(view.getContext(), "ACTION_DRAG_LOCATION", Toast.LENGTH_SHORT).show();

                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                // Toast.makeText(view.getContext(), "ACTION_DRAG_ENDED, " + event.getX(), Toast.LENGTH_SHORT).show();
                onExitCallback.onExitDragAndDrop(startX, finishX);

                return true;

            default:

                // TODO::remove this log
                Toast.makeText(view.getContext(), "Unknown command, " + event.getAction(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
