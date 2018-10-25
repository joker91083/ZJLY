package com.otitan.base;


/**
 * 执行的命令回调, 用于ViewModel与xml之间的数据绑定
 */
public class BindingCommand {
    private BindingAction execute;

    public BindingCommand(BindingAction execute) {
        this.execute = execute;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (execute != null) {
            execute.call();
        }
    }
}
