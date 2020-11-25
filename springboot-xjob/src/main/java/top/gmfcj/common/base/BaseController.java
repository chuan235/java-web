package top.gmfcj.common.base;


import java.util.List;

public class BaseController {

    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? this.success() : this.error();
    }

    protected AjaxResult toAjax(List<?> list) {
        return AjaxResult.success(list);
    }

    protected AjaxResult toAjax(boolean result) {
        return result ? this.success() : this.error();
    }

    public AjaxResult success() {
        return AjaxResult.success();
    }

    public AjaxResult error() {
        return AjaxResult.error();
    }

    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }
}
