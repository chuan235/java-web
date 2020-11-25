package top.gmfcj.common.base;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public static AjaxResult error() {
        return error(1, "操作失败");
    }

    public static AjaxResult error(String msg) {
        return error(500, msg);
    }

    public static AjaxResult error(int code, String msg) {
        AjaxResult json = new AjaxResult();
        json.put((String) "code", code);
        json.put((String) "msg", msg);
        return json;
    }

    public static AjaxResult success(String msg) {
        AjaxResult json = new AjaxResult();
        json.put((String) "msg", msg);
        json.put((String) "code", 0);
        return json;
    }

    public AjaxResult(AjaxResult.Type type, String msg, Object data) {
        super.put("code", type.value);
        super.put("msg", msg);
        if (data != null) {
            super.put("data", data);
        }

    }

    public static AjaxResult success(Object data) {
        return success("操作成功", data);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(AjaxResult.Type.SUCCESS, msg, data);
    }

    public static AjaxResult success() {
        return success("success");
    }

    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public boolean isSuccess() {
        Object code = this.get("code");
        if (this.get("code") != null) {
            return 0 == (Integer) code;
        } else {
            return false;
        }
    }

    public AjaxResult setData(Object data) {
        return this.put("data", data);
    }

    public Object getData() {
        return this.get("data");
    }

    public boolean isError() {
        return !this.isSuccess();
    }

    public String getMsg() {
        return (String) this.get("msg");
    }

    public static enum Type {
        SUCCESS(0),
        WARN(301),
        ERROR(500);

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
