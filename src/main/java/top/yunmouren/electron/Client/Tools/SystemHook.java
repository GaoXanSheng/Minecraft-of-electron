package top.yunmouren.electron.Client.Tools;

import static top.yunmouren.electron.Client.Tools.IPC.process;

public class SystemHook {
    public SystemHook(){
        this.ShutdownHook();
    }

    public void ShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            process.destroyForcibly();
        }));
    }
}
