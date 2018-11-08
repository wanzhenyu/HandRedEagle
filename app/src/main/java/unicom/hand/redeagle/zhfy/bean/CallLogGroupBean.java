package unicom.hand.redeagle.zhfy.bean;

import com.yealink.common.data.CallLogGroup;

import java.io.Serializable;

/**
 * Created by linana on 2018-08-14.
 */

public class CallLogGroupBean implements Serializable {

    public CallLogGroup bean;

    public CallLogGroup getBean() {
        return bean;
    }

    public void setBean(CallLogGroup bean) {
        this.bean = bean;
    }
}
