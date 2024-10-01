package top.yunmouren.electron.Client;

import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.graphics.Canvas;
import icyllis.modernui.graphics.drawable.Drawable;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;

public class TestGui extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, DataSet savedInstanceState) {
        var view = new View(container.getContext());
        view.setBackground(new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(0xFF0000);
            }
        });
        view.setOnCreateContextMenuListener((contextMenu, view1,contextMenuInfo) -> {
            contextMenu.add("test");
        });

        return view;
    }
}
