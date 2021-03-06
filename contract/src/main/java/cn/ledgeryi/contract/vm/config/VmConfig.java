package cn.ledgeryi.contract.vm.config;

import lombok.Setter;
public class VmConfig {

    private static boolean vmTraceCompressed = false;

    @Setter
    private static boolean vmTrace = false;

    @Setter
    private static boolean ALLOW_TVM_CONSTANTINOPLE = true;

    public static final int MAX_FEE_LIMIT = 1_000_000_000;

    private VmConfig() {
    }

    public static VmConfig getInstance() {
        return SystemPropertiesInstance.INSTANCE;
    }

    private static class SystemPropertiesInstance {
        private static final VmConfig INSTANCE = new VmConfig();
    }

    public static boolean vmTrace() {
        return vmTrace;
    }

    public static boolean vmTraceCompressed() {
        return vmTraceCompressed;
    }

    public static boolean allowTvmConstantinople() {
        return ALLOW_TVM_CONSTANTINOPLE;
    }
}
