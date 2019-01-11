package ee.era.hangman.actions;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

class GameActionSupport extends ActionSupport implements SessionAware {

    private static final String START_TIME = new Date().toString();
    protected Map<String, Object> session;

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getStartTime() {
        return START_TIME;
    }
}
