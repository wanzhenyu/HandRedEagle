package unicom.hand.redeagle.zhfy.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import unicom.hand.redeagle.zhfy.AppApplication;
import unicom.hand.redeagle.R;
import unicom.hand.redeagle.zhfy.Common;
import unicom.hand.redeagle.zhfy.bean.ItemBean;
import unicom.hand.redeagle.zhfy.bean.MyCityBean2;
import unicom.hand.redeagle.zhfy.utils.GsonUtil;
import unicom.hand.redeagle.zhfy.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewHolder")
public class ListViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<MyCityBean2> mList;
	private List<MyCityBean2> mList2;
	DbUtils db;
	private Boolean isadd = false;
	private String sqlString = "";// 查询标识

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public ListViewAdapter(Context c, Boolean myisadd) {
		mContext = c;
		mList = new ArrayList<MyCityBean2>();
		mList2 = new ArrayList<MyCityBean2>();
		db = DbUtils.create(c, Common.DB_NAME);
		isadd = myisadd;
	}


	public void setDate(List<MyCityBean2> list) {
		mList = list;
		notifyDataSetChanged();
	}

	private CalInterface calInterface;

	public interface CalInterface {
		void onVedeoCallClick(MyCityBean2 bean);
	}

	public void setCalInterface(CalInterface calInterface) {
		this.calInterface = calInterface;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		View view = View.inflate(mContext, R.layout.list_item_view, null);
		RelativeLayout rl = (RelativeLayout) view
				.findViewById(R.id.RelativeLayout);

//		if (position == 2 && mList.get(position).getCompany().equals("平顶山市第一人民医院")) {
//			mList.get(2).setCode("7109002");
//			mList.get(2).setCompany("平顶山市第一人民医院外科");
//			mList.get(2).setCalledName("5052");
//			mList.get(2).setCalledNum("5052");
//			mList.get(2).setCallingNum("5116");
//			mList.get(2).setCalledPassword("5116");
//			mList.get(2).setHasChildren(0);
//		}
		TextView text = (TextView) rl.findViewById(R.id.name);
		text.setText(mList.get(position).getCompany());
		if (mList.get(position).getHasChildren()!=null&&1 == mList.get(position).getHasChildren()) {
			text.setTextColor(Color.parseColor("#D41D29"));
		} else {
			text.setTextColor(Color.BLACK);
		}

		ImageView image = (ImageView) rl.findViewById(R.id.video);

		ImageView star_1 = (ImageView) rl.findViewById(R.id.star_1);
		ImageView star_2 = (ImageView) rl.findViewById(R.id.star_2);
		ImageView star_3 = (ImageView) rl.findViewById(R.id.star_3);
		ImageView star_4 = (ImageView) rl.findViewById(R.id.star_4);
		ImageView star_5 = (ImageView) rl.findViewById(R.id.star_5);
		ImageView btn_add = (ImageView) rl.findViewById(R.id.btn_add);
		if (isadd) {
			if (!StringUtils.isNullOrEmpty(mList.get(position).getCallingNum())) {
				btn_add.setVisibility(View.VISIBLE);
			} else {
				btn_add.setVisibility(View.INVISIBLE);
			}
			image.setVisibility(View.INVISIBLE);
			btn_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
//					if ((sqlString.equals("face2face")
//							&& 2 < mList.get(position).getPosition() && mList
//							.get(position).getPosition() < 5)
//							|| mList.get(position).getHasChildren() == 1) {
					if ((sqlString.equals("face2face")
							&& 2 < mList.get(position).getLayer() && mList
							.get(position).getLayer() < 5)
							|| mList.get(position).getHasChildren() == 1) {
						String[] arry = new String[] { "添加该单位及其子单位到首页",
								"仅添加该单位到首页" };// 性别选择
						AlertDialog.Builder builder = new AlertDialog.Builder(
								mContext);// 自定义对话框
						builder.setTitle("请选择添加到首页的方式");
						builder.setSingleChoiceItems(arry, 0,
								new DialogInterface.OnClickListener() {// 2默认的选中

									@Override
									public void onClick(DialogInterface dialog,
														int which) {// which是被选中的位置
										// showToast(which+"");
										Type dataType = new TypeToken<List<ItemBean>>() {
										}.getType();
										ArrayList<ItemBean> tempdata;
										tempdata = GsonUtil
												.getGson()
												.fromJson(
														AppApplication.preferenceProvider
																.getjson(),
														dataType);
										ItemBean bean = new ItemBean();
										bean.setName(mList.get(position)
												.getCompany());

										bean.setIsSelect(true);
										if (sqlString.equals("medical")) {
											bean.setIcon(R.drawable.item_yl);
										}else if (sqlString.equals("law")) {
											bean.setIcon(R.drawable.item_fl);
										}else if (sqlString.equals("agriculture")) {
											bean.setIcon(R.drawable.item_njfw);
										}else  {
											bean.setIcon(R.drawable.item_xxc);
										}
										bean.setSqlString(sqlString);
										bean.setIsMain(true);
										bean.setIsLongBoolean(false);
										bean.setIsAdd(false);
										if (which == 0) {
											bean.setHasChildren(1);
										} else {
											bean.setHasChildren(0);
										}

										bean.setCode(mList.get(position)
												.getCode());
										tempdata.add(bean);
										AppApplication.preferenceProvider

												.setjson(GsonUtil
														.getJson(tempdata));
										dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
										Toast.makeText(mContext, "添加成功",
												Toast.LENGTH_SHORT).show();
									}
								});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										dialog.cancel();
									}
								});
						builder.show();// 让弹出框显示
					} else {
						Type dataType = new TypeToken<List<ItemBean>>() {
						}.getType();
						ArrayList<ItemBean> tempdata;
						tempdata = GsonUtil.getGson().fromJson(
								AppApplication.preferenceProvider.getjson(),
								dataType);
						ItemBean bean = new ItemBean();
						bean.setName(mList.get(position).getCompany());
						if (sqlString.equals("medical")) {
							bean.setIcon(R.drawable.item_yl);
						}else if (sqlString.equals("law")) {
							bean.setIcon(R.drawable.item_fl);
						}else if (sqlString.equals("agriculture")) {
							bean.setIcon(R.drawable.item_njfw);
						}else  {
							bean.setIcon(R.drawable.item_xxc);
						}
						bean.setIsSelect(true);
						bean.setSqlString(sqlString);
						bean.setIsMain(true);
						bean.setIsLongBoolean(false);
						bean.setIsAdd(false);
						bean.setHasChildren(0);
						bean.setCode(mList.get(position).getCode());
						tempdata.add(bean);
						AppApplication.preferenceProvider.setjson(GsonUtil
								.getJson(tempdata));
						Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT)
								.show();
					}

				}
			});
		} else {
			btn_add.setVisibility(View.INVISIBLE);

			if (!StringUtils.isNullOrEmpty(mList.get(position).getCallingNum())) {
//				image.setVisibility(View.VISIBLE);
				image.setBackgroundResource(R.drawable.video);
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						calInterface.onVedeoCallClick(mList.get(position));
					}
				});
			} else {
				image.setBackgroundResource(R.drawable.icon_callunable);
//				image.setVisibility(View.INVISIBLE);
			}
		}

		setStarImage(star_1, star_2, star_3, star_4, star_5, mList
				.get(position).getStars() + "");

		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (1 == mList.get(position).getHasChildren()) {
					try {
						mList2 = db.findAll(Selector
								.from(MyCityBean2.class)
								.where("parentCode", "=",
										mList.get(position).getCode())
								.and(WhereBuilder.b("name", "!=",
										mList.get(position).getCompany()))
								.orderBy("sort", false));
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (1 == mList.get(position).getExpanding()) {
						mList.get(position).setExpanding(0);
						mList.addAll(position + 1, mList2);
						notifyDataSetChanged();
					} else {
						for (int i = 0; i < mList2.size(); i++) {
							mList.remove(position + 1);
							mList.get(position).setExpanding(1);
							notifyDataSetChanged();
						}
					}
				}
			}
		});

		return rl;
	}

	private void setStarImage(ImageView star_1, ImageView star_2,
                              ImageView star_3, ImageView star_4, ImageView star_5, String star) {
		// TODO Auto-generated method stub
		if (star.equals("5")) {
			star_1.setBackgroundResource(R.drawable.star);
			star_2.setBackgroundResource(R.drawable.star);
			star_3.setBackgroundResource(R.drawable.star);
			star_4.setBackgroundResource(R.drawable.star);
			star_5.setBackgroundResource(R.drawable.star);
		} else if (star.equals("4")) {
			star_1.setBackgroundResource(R.drawable.star);
			star_2.setBackgroundResource(R.drawable.star);
			star_3.setBackgroundResource(R.drawable.star);
			star_4.setBackgroundResource(R.drawable.star);
			star_5.setVisibility(View.INVISIBLE);
		} else if (star.equals("3")) {
			star_1.setBackgroundResource(R.drawable.star);
			star_2.setBackgroundResource(R.drawable.star);
			star_3.setBackgroundResource(R.drawable.star);
			star_4.setVisibility(View.INVISIBLE);
			star_5.setVisibility(View.INVISIBLE);
		} else if (star.equals("2")) {
			star_1.setBackgroundResource(R.drawable.star);
			star_2.setBackgroundResource(R.drawable.star);
			star_4.setVisibility(View.INVISIBLE);
			star_5.setVisibility(View.INVISIBLE);
			star_3.setVisibility(View.INVISIBLE);
		} else if (star.equals("1")) {
			star_1.setBackgroundResource(R.drawable.star);
			star_4.setVisibility(View.INVISIBLE);
			star_5.setVisibility(View.INVISIBLE);
			star_3.setVisibility(View.INVISIBLE);
			star_2.setVisibility(View.INVISIBLE);
		} else if (star.equals("0")) {
			star_4.setVisibility(View.INVISIBLE);
			star_5.setVisibility(View.INVISIBLE);
			star_3.setVisibility(View.INVISIBLE);
			star_2.setVisibility(View.INVISIBLE);
			star_1.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public MyCityBean2 getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}