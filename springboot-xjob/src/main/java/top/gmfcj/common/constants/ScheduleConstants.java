package top.gmfcj.common.constants;


public interface ScheduleConstants {

    String TASK_CLASS_NAME = "__TASK_CLASS_NAME__";
    String TASK_PROPERTIES = "__TASK_PROPERTIES__";

    String MISFIRE_DEFAULT = "0";
    String MISFIRE_IGNORE_MISFIRES = "1";
    String MISFIRE_FIRE_AND_PROCEED = "2";
    String MISFIRE_DO_NOTHING = "3";

    public static enum Status {
        NORMAL("0"),
        PAUSE("1");
        private String value;
        private Status(String value) {
            this.value = value;
        }
        public String getValue() {
            return this.value;
        }
    }
}
