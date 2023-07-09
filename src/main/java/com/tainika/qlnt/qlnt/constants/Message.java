package com.tainika.qlnt.qlnt.constants;

public interface Message {
    enum LOG {
        ACTION_SUCCESS(0, "%s action is successed!: {%s}"),
        ACTION_FAIL(1, "%s action is failed: %s"),
        ACTION_ERROR(2, "%s action can't be completed because has an error: %s");

        private int code;
        private String name;

        LOG(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public java.lang.String getName() {
            return name;
        }
    }

    interface ACTION {
        String SIGN_UP = "Sign up";
        String SIGN_IN = "Sign in";
        String GET_ALL = "Get all";
        String GET_DETAIL = "Get detail";

        String SEARCH = "Search";

        String CREATE = "Create";
        String READ = "Read";
        String UPDATE = "Update";
        String DELETE = "Delete";
    }

    interface ALERT {
        String USER_EXISTED = "User existed";
        String EMAIL_EXISTED = "Email existed";
        String UNKNOWN_REASON = "Unknown reason";
        String NO_RESULT = "No results are returned";
    }
}
