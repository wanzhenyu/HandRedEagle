package unicom.hand.redeagle.zhfy.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by linana on 2018-03-16.
 */

public class SerializableMap implements Serializable {
    private Map<String,MyNodeBean> map;

    public Map<String, MyNodeBean> getMap() {
        return map;
    }

    public void setMap(Map<String, MyNodeBean> map) {
        this.map = map;
    }
}
