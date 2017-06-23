package com.subgarden.arguments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.subgarden.arguments.MainActivity;
import com.subgarden.arguments.arguments.Arguments;
import com.subgarden.arguments.arguments.ClickInfo;
import com.subgarden.arguments.arguments.SourceParams;

/**
 * @author Fredrik Larsen <fredrik@subgarden.com>
 */
public abstract class BaseFragment extends Fragment {

    private Arguments mArgs;

    protected SourceParams mSourceParams;
    protected ClickInfo mClickInfo;

    /**
     * Returns the navigation arguments for this fragment.
     *
     * Subclasses should override this method and return the actual subclass of Arguments.
     */
    public abstract Arguments getNavigationArgs();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException("Arguments are null");
        }
        mSourceParams = (SourceParams) args.getSerializable(
                MainActivity.KEY_SOURCE_PARAMS);
        mClickInfo = (ClickInfo) args.getSerializable(MainActivity.KEY_CLICK_INFO);

        Toast.makeText(getContext(), "BaseFragment.onCreate called", Toast.LENGTH_SHORT).show();
    }

    public void setNavigationArgs(Arguments arguments) {
        Bundle bundle = MainActivity.buildArgs(arguments, new SourceParams(), new ClickInfo());
        // Arguments can only be set before the fragment is active. This limitation makes sense
        // for initially creating fragments from arguments, but not when updating them and restoring
        // from state.
        if (getArguments() == null) {
            setArguments(bundle);
        } else {
            getArguments().clear();
            getArguments().putAll(bundle);
        }

        mArgs = arguments;
    }

    protected final <T extends Arguments> T getNavigationArgs(Class<T> clazz) {
        if (mArgs == null) {
            Bundle bundle = getArguments().getBundle(MainActivity.KEY_ARGS);
            try {
                mArgs = clazz.cast(Class.forName(clazz.getName())
                                        .getConstructor(Bundle.class)
                                        .newInstance(bundle));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No constructor found for the parameter [class android.os.Bundle]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clazz.cast(mArgs);
    }

}
