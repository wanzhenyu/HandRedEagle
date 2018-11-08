//package unicom.hand.redeagle.zhfy.sdk;
//
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//

//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.preference.PreferenceManager;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.yealink.base.util.FileUtils;
//import com.yealink.callscreen.CallUtils;
//import com.yealink.callscreen.ExternalInterface;
//import com.yealink.callscreen.TalkManager;
//import com.yealink.common.AppWrapper;
//import com.yealink.common.CallManager;
//import com.yealink.common.CallManager.CallAdapter;
//import com.yealink.common.CloudManager;
//import com.yealink.common.ContactManager;
//import com.yealink.common.ContactManager.OnCloudPhoneBookChangeListener;
//import com.yealink.common.DebugLog;
//import com.yealink.common.NativeInit;
//import com.yealink.common.NativeInit.NativeInitListener;
//import com.yealink.common.SettingsManager;
//import com.yealink.common.SettingsManager.AccountStateListener;
//import com.yealink.common.YmsConferenceManager;
//import com.yealink.common.data.CallSession;
//import com.yealink.common.data.CallSetting;
//import com.yealink.common.data.CloudProfile;
//import com.yealink.common.data.Contact;
//import com.yealink.common.data.H323Profile;
//import com.yealink.common.data.Meeting;
//import com.yealink.common.data.OrgNode;
//import com.yealink.common.data.SearchExtParams;
//import com.yealink.common.data.SipProfile;
//import com.yealink.sdk.Constant;
//import com.yealink.sdk.ContactListener;
//import com.yealink.sdk.RegistListener;
//import com.yealink.utils.ZipUtil;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import ylLogic.LogicInterface;
//import ylLogic.dataOrgTreeNode;
//
//public class VideoApi extends YmsConferenceManager.MeetingListener implements AccountStateListener, NativeInitListener, OnCloudPhoneBookChangeListener {
//    private static final String TAG = "VideoApi";
//    private static final String KEY_SDCARD_IS_CLEARED = "KEY_SDCARD_IS_CLEARED";
//    private static VideoApi mInstance = null;
//    private static List<RegistListener> mRegistListeners;
//    private static List<ContactListener> mContactListeners;
//    private static List<com.yealink.sdk.MeetingListener> mMeetingListeners;
//    private static IncomingListener mIncomingListener;
//    private static Boolean isRegister = false;
//    private static Application context;
//    private Handler mHandler;
//    private CallAdapter mCallAdapter = new CallAdapter() {
//        public void onIncomingCall(CallSession session) {
//            if(VideoApi.mIncomingListener != null) {
//                VideoApi.mIncomingListener.onIncoming();
//            }
//
//            TalkManager.getInstance().postTalkEvent(27, (Bundle)null);
//        }
//    };
//
//    private VideoApi() {
//        mRegistListeners = new ArrayList();
//        mContactListeners = new ArrayList();
//        mMeetingListeners = new ArrayList();
//        NativeInit.registerListener(this);
//    }
//
//    public static synchronized VideoApi instance() {
//        if(mInstance == null) {
//            mInstance = new VideoApi();
//        }
//
//        return mInstance;
//    }
//
//    public static Boolean getIsRegister() {
//        if (!isRegister){
//            try {
//            Toast.makeText(context,"请认证通过后再使用此sdk", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return isRegister;
//    }
//
//
//    public synchronized void init(Application mContext, String myPackage) {
//        context = mContext;
//        if (myPackage.equals("com.henanunicom.dqzhfw")){
//            isRegister = true;
//        }else {
//            isRegister = false;
//        }
//
//        if (isRegister) {
//            AppWrapper.getInstance().init(mContext);
//            this.mHandler = new Handler();
//            File dir = new File(Environment.getExternalStorageDirectory() + "/yealink");
//            if (!this.isSdcardDirCleared()) {
//                FileUtils.delete(dir);
//                this.setSdcardDirCleared();
//            }
//
//            if ((!dir.exists() || !dir.isDirectory()) && !dir.mkdir()) {
//                Log.e("VideoApi", "mkdir yealink failed!!");
//            }
//
//            DebugLog.i("VideoApi", "Dev Model : " + Build.MODEL);
//            (new VideoApi.AsynInitTask(null)).execute(new Void[0]);
//        }else {
//            Toast.makeText(mContext,"请认证通过后再使用此sdk", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean isSdcardDirCleared() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AppWrapper.getApp());
//        return prefs.contains("KEY_SDCARD_IS_CLEARED");
//    }
//
//    private void setSdcardDirCleared() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AppWrapper.getApp());
//        prefs.edit().putBoolean("KEY_SDCARD_IS_CLEARED", true).apply();
//    }
//
//    public synchronized void addRegistListener(RegistListener lsnr) {
//
//        if (getIsRegister()){
//            if(!mRegistListeners.contains(lsnr)) {
//                mRegistListeners.add(lsnr);
//            }
//        }
//
//
//    }
//
//    public synchronized void removeRegistListener(RegistListener lsnr) {
//        if (getIsRegister()) {
//            mRegistListeners.remove(lsnr);
//        }
//    }
//
//    public synchronized void addContactListener(ContactListener lsnr) {
//        if (getIsRegister()) {
//            if (!mContactListeners.contains(lsnr)) {
//                mContactListeners.add(lsnr);
//            }
//        }
//    }
//
//    public synchronized void removeContactListener(ContactListener lsnr) {
//        if (getIsRegister()) {
//            mContactListeners.remove(lsnr);
//        }
//    }
//
//    public synchronized void addMeetingListener(com.yealink.sdk.MeetingListener lsnr) {
//        if (getIsRegister()) {
//            if (!mMeetingListeners.contains(lsnr)) {
//                mMeetingListeners.add(lsnr);
//            }
//        }
//    }
//
//    public synchronized void removeMeetingListener(com.yealink.sdk.MeetingListener lsnr) {
//        if (getIsRegister()) {
//            mMeetingListeners.remove(lsnr);
//        }
//    }
//
//    public static void setIncomingListener(IncomingListener mIncomingListeners) {
//        if (getIsRegister()) {
//            mIncomingListener = mIncomingListeners;
//        }
//    }
//
//    public void registerSip(SipProfile sipProfile) {
//        if (getIsRegister()) {
//            SettingsManager.getInstance().setSipProfile(0, sipProfile);
//        }
//    }
//
//    public void registerH323(H323Profile h323Profile) {
//        if (getIsRegister()) {
//            SettingsManager.getInstance().setH323Profile(h323Profile);
//        }
//    }
//
//    public void registerCloudByAccount(String userName, String pwd, String server) {
//        if (getIsRegister()) {
//            CloudManager.getInstance().loginByAccount(userName, pwd, false, server);
//        }
//    }
//
//    public void registerCloudByPincode(String pinCode, String server) {
//        if (getIsRegister()) {
//            CloudManager.getInstance().loginByPinCode(pinCode, server);
//        }
//    }
//
//    public void registerYms(String strNumber, String strPassword, String strServer, String strProxyServer) {
//        if (getIsRegister()) {
//        LogicInterface.premiseLoginByUserAndPassword(strNumber, strPassword, false, strServer, strProxyServer);
//    }}
//
//    public void unregistCloud() {
//        if (getIsRegister()) {
//            CloudManager.getInstance().loginOut();
//        }
//    }
//
//    public CloudProfile getCloudProfile() {
//            return SettingsManager.getInstance().getCloudProfile();
//    }
//
//    public SipProfile getSipProfile() {
//            return SettingsManager.getInstance().getSipProfile(0);
//    }
//
//    public CallSetting getCallSetting() {
//
//        return SettingsManager.getInstance().getCallSetting();
//    }
//
//    public boolean setCallSetting(CallSetting cs) {
//        return SettingsManager.getInstance().setCallSetting(cs);
//    }
//
//    public H323Profile getH323Profile() {
//        return SettingsManager.getInstance().getH323Profile();
//    }
//
//    public void call(Context context, String number) {
//        if (getIsRegister()) {
//            CallUtils.startCall(context, number, 0);
//        }
//    }
//
//    public void call(Context context, String number, boolean isVideo) {
//        if (getIsRegister()) {
//            CallUtils.startCall(context, number, isVideo, 0);
//        }
//    }
//
//    public void call(Context context, Meeting meeting) {
//        if (getIsRegister()) {
//            if (meeting != null) {
//                this.call(context, meeting.getNumber(), meeting.getFoucusUri(), meeting.getTitle(), meeting.getMeetingId());
//            }
//        }
//    }
//
//    public void call(Context context, String strConferenceNumber, String strUri, String strSubject, String meetingId) {
//        if (getIsRegister()) {
//            CallUtils.joinMeeting(context, strConferenceNumber, strUri, strSubject, meetingId);
//        }
//    }
//
//    public void incoming(Context context) {
//        if (getIsRegister()) {
//            CallUtils.incoming(context);
//        }
//    }
//
//    public void meetInvite(String[] listMember) {
//        if (getIsRegister()) {
//            CallSession cs = CallManager.getInstance().getCurrentSession();
//            if (cs != null) {
//                YmsConferenceManager.getInstance().inviteMember(cs.getId(), listMember, false);
//            }
//        }
//
//    }
//
//    public void meetNow(Context context, ArrayList<Contact> contacts) {
//        if (getIsRegister()) {
//            CallUtils.startMeeting(context, contacts, true);
//        }
//    }
//
//    public void setExtInterface(ExternalInterface extInterface) {
//        if (getIsRegister()) {
//            TalkManager.getInstance().setExtInterface(extInterface);
//        }
//    }
//
//    public void onAccountStateChanged(int account, int state, int protocol) {
//        if (getIsRegister()) {
//        Iterator var4;
//        RegistListener lsnr;
//        if(account == 0) {
//            var4 = mRegistListeners.iterator();
//
//            while(var4.hasNext()) {
//                lsnr = (RegistListener)var4.next();
//                lsnr.onSipRegist(state);
//            }
//        } else if(account == 0) {
//            var4 = mRegistListeners.iterator();
//
//            while(var4.hasNext()) {
//                lsnr = (RegistListener)var4.next();
//                lsnr.onH323Regist(state);
//            }
//        } else if(account == 1) {
//            var4 = mRegistListeners.iterator();
//
//            while(var4.hasNext()) {
//                lsnr = (RegistListener)var4.next();
//                lsnr.onCloudRegist(state);
//            }
//        }}
//
//    }
//
//    public void onCloudPhoneBookChange() {
//        if (getIsRegister()) {
//        Iterator var1 = mContactListeners.iterator();
//
//        while(var1.hasNext()) {
//            ContactListener lsnr = (ContactListener)var1.next();
//            lsnr.onCloudUpdate();
//        }}
//
//    }
//
//    public void onCloudPhoneBookEnableChanged(boolean enable) {
//        if (getIsRegister()) {
//            Iterator var2 = mContactListeners.iterator();
//
//            while (var2.hasNext()) {
//                ContactListener lsnr = (ContactListener) var2.next();
//                lsnr.onCloudUpdate();
//            }
//        }
//    }
//
//    public OrgNode getOrgRoot(boolean excludeVMR) {
//            dataOrgTreeNode node = ylLogic.Contact.getOrgNodeInfo("");
//            return node != null && !TextUtils.isEmpty(node.m_strId) ? ContactManager.convertDataOrgTreeNode(node, (OrgNode) null, excludeVMR) : null;
//    }
//
//    public List<OrgNode> getOrgChildNode(boolean excludeVMR, String orgId) {
//
//        ArrayList childrenList = new ArrayList();
//        dataOrgTreeNode node = ylLogic.Contact.getOrgNodeInfo(orgId);
//        if(node != null && node.m_listChildren != null) {
//            dataOrgTreeNode[] var5 = node.m_listChildren;
//            int var6 = var5.length;
//
//            for(int var7 = 0; var7 < var6; ++var7) {
//                dataOrgTreeNode child = var5[var7];
//                childrenList.add(ContactManager.convertDataOrgTreeNode(child, (OrgNode)null, excludeVMR));
//            }
//        }
//
//        return childrenList;
//    }
//
//    public List<OrgNode> searchOrgContact(String searchKey, boolean excludeVMR) {
//        ArrayList resultList = new ArrayList();
//        SearchExtParams params = (new SearchExtParams()).disableGetName().disableGetLeaves();
//        dataOrgTreeNode node = ylLogic.Contact.searchOrgTree(searchKey, "", params.toJson());
//        if(node != null && node.m_listChildren != null) {
//            dataOrgTreeNode[] var6 = node.m_listChildren;
//            int var7 = var6.length;
//
//            for(int var8 = 0; var8 < var7; ++var8) {
//                dataOrgTreeNode child = var6[var8];
//                OrgNode childNode = ContactManager.convertDataOrgTreeNode(child, (OrgNode)null, excludeVMR);
//                resultList.add(childNode);
//                resultList.addAll(childNode.getChildren());
//            }
//        }
//
//        return resultList;
//    }
//
//    public List<Contact> searchCloudContact(String searchKey, boolean excludeVMR) {
//        return ContactManager.getInstance().search(searchKey, true, excludeVMR);
//    }
//
//    public String compressLogFile() {
//        ArrayList src = new ArrayList();
//        src.add(Constant.YEALINK_LOGCAT_LOG_PATH);
//        src.add(Constant.YEALINK_NATIVE_LOG_PATH);
//        String destPath = Constant.YEALINK_LOGCAT_ZIP_PATH;
//        return ZipUtil.zip(src, destPath)?destPath:"";
//    }
//
//    public List<Meeting> getMeetingList() {
//        return YmsConferenceManager.getInstance().getMeetingScheduleInfoList();
//    }
//
//    public Meeting getMeetingDesc(String meetingId, String scheduleId) {
//        return YmsConferenceManager.getInstance().getMeetingDesc(meetingId, scheduleId);
//    }
//
//    public void onInitFinish() {
//        if (getIsRegister()) {
//            SettingsManager.getInstance().registerAccountListener(this);
//            ContactManager.getInstance().registerListener(this);
//            CallManager.getInstance().registerCallListener(this.mCallAdapter, new Handler(Looper.getMainLooper()));
//            YmsConferenceManager.getInstance().registerMeetingListener(this);
//        }
//    }
//
//    public void onNativeError(int errorCode) {
//    }
//
//    public void onYMSMeetingChanged(String msgID, String meetID, String scheduleID) {
//        if (getIsRegister()) {
//            Iterator var4 = mMeetingListeners.iterator();
//
//            while (var4.hasNext()) {
//                com.yealink.sdk.MeetingListener lsnr = (com.yealink.sdk.MeetingListener) var4.next();
//                lsnr.meetingUpdate(msgID, meetID, scheduleID);
//            }
//        }
//    }
//
//    private class AsynInitTask extends AsyncTask<Void, Void, Void> {
//        private AsynInitTask(Object o) {
//        }
//
//        protected Void doInBackground(Void... params) {
//            Log.i("VideoApi", "start AsynInitTask");
//            NativeInit.startInit(VideoApi.this.mHandler);
//            return null;
//        }
//    }
//}
