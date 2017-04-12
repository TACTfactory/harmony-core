<@header?interpret />
package ${project_namespace}.harmony.widget;

import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * This listener is used in conjonction with the Universal Android Image Loader
 * library. It display a neat undefined progress bar when your image is loading.
 * (Don't use it in list adapters. )
 */
public class ProgressImageLoaderListener implements ImageLoadingListener {

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        ViewGroup parent = ((ViewGroup) view.getParent());
        FrameLayout currentProgress = this.getAssociatedProgress(view);

        if (currentProgress != null) {
            parent.removeView(currentProgress);
        }

        ProgressBar progressBar = new ProgressBar(view.getContext());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.CENTER));


        FrameLayout newFrameLayout = new FrameLayout(view.getContext());
        newFrameLayout.addView(progressBar);

        newFrameLayout.setLayoutParams(view.getLayoutParams());
        newFrameLayout.setTag(this.getProgressTag(view));

        parent.addView(newFrameLayout, parent.indexOfChild(view));
        view.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view,
            FailReason failReason) {
        this.removeProgress(view);

        view.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoadingComplete(String imageUri, View view,
            Bitmap loadedImage) {
        this.removeProgress(view);

        view.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        this.removeProgress(view);

        view.setVisibility(View.VISIBLE);
    }

    /**
     * Remove the progress FrameLayout associated to the given View.
     * @param view The view
     */
    protected void removeProgress(View view) {
        FrameLayout progress = this.getAssociatedProgress(view);
        if (progress != null) {
            ((ViewGroup) progress.getParent()).removeView(progress);
        }
    }

    /**
     * Gets the FrameLayout containing the progress associated to the given
     * view.
     * @param view The view
     * @return The FrameLayout
     */
    protected FrameLayout getAssociatedProgress(View view) {
        FrameLayout result = null;
        ViewGroup parent = ((ViewGroup) view.getParent());
        result = ((FrameLayout) parent.findViewWithTag(
                        this.getProgressTag(view)));


        return result;
    }

    /**
     * Generate a tag for the progress associated to this view.
     *
     * The tag must be unique for each View.
     *
     * @param view The view to generate the tag from.
     * @return The tag
     */
    protected String getProgressTag(View view) {
        return view.toString()  + "_progress";
    }
}
