package app.monikoik.com.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;


public class First extends FragmentActivity {

    private UiLifecycleHelper uiHelper;

    private static final int SPLASH = 0;
    private static final int MAIN = 1;
    private static final int SETTINGS = 2;
    private static final int FRAGMENT_COUNT = SETTINGS +1;

    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    private MenuItem settings;
    private boolean isResumed = false;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this,callback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SplashFragment splashFragment = (SplashFragment) fragmentManager.findFragmentById(R.id.splashFragment);
        fragments[SPLASH] = splashFragment;
        fragments[MAIN] = fragmentManager.findFragmentById(R.id.mainFragment);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for(int i = 0; i < fragments.length; i++){
            transaction.hide(fragments[i]);
        }
        transaction.commit();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if(isResumed){
            FragmentManager manager = getSupportFragmentManager();
            int backStack = manager.getBackStackEntryCount();
            for (int i = 0; i < backStack; i++){
                manager.popBackStack();
            }

            if(state.equals(SessionState.OPENED)){
                showFragment(MAIN, false);
            } else if(state.isClosed()){
                showFragment(SPLASH, false);
            }
        }
    }

    private void showFragment(int fragmentIndex, boolean addToStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for(int i = 0; i<fragments.length; i++){
            if(i == fragmentIndex){
                transaction.show(fragments[i]);
            }else {
                transaction.hide(fragments[i]);
            }
        }
        if(addToStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        isResumed = true;
    }

    @Override
    public void onPause(){
        super.onResume();
        isResumed = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    //private TextView userName;

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();

        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        //ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }*/
}
