package top.yunmouren.electron.Client.interfaceModels;

import icyllis.modernui.annotation.NonNull;
import icyllis.modernui.annotation.Nullable;
import icyllis.modernui.core.Context;
import icyllis.modernui.fragment.Fragment;
import icyllis.modernui.util.DataSet;
import icyllis.modernui.view.LayoutInflater;
import icyllis.modernui.view.View;
import icyllis.modernui.view.ViewGroup;

abstract public class CustomGUI extends Fragment {
    public abstract void onAttach(@NonNull Context context);
    public abstract void onCreate(@Nullable DataSet savedInstanceState);
    @Nullable
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable DataSet savedInstanceState);


}
