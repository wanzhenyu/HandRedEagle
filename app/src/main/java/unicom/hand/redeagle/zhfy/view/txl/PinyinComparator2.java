package unicom.hand.redeagle.zhfy.view.txl;

import unicom.hand.redeagle.zhfy.bean.TxlBean;

import java.util.Comparator;


/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator2 implements Comparator<TxlBean> {

	public int compare(TxlBean o1, TxlBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
