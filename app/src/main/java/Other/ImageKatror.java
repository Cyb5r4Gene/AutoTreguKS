package Other;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ImageKatror extends AppCompatImageView {

    public ImageKatror(Context context) {
        super(context);
    }

    public ImageKatror(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageKatror(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
