package ${project_namespace}.harmony.widget.pinnedheader;

import ${project_namespace}.R;
import ${project_namespace}.harmony.widget.pinnedheader.util.ComponentUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AbsListView.SelectionBoundsAdjuster;

public class SelectionItemView extends ViewGroup /*implements SelectionBoundsAdjuster */{
		
	// Will be used with adjustListItemSelectionBounds().
	private int mSelectionBoundsMarginLeft;
	private int mSelectionBoundsMarginRight;
	
	// Horizontal divider between contact views.
    private boolean mHorizontalDividerVisible;
    private Drawable mHorizontalDividerDrawable;
    private int mHorizontalDividerHeight;
    //private int mVerticalDividerMargin;
    
    // Style values for layout and appearance
    private final int mPreferredHeight;
    private final int mHeaderTextColor;
    private final int mHeaderTextIndent;
    private final int mHeaderTextSize;
    private final int mHeaderUnderlineHeight;
    private final int mHeaderUnderlineColor;
    //private final int mCountViewTextSize;
    //private final int mCountViewTextColor;
    private final int mTextIndent;
    private Drawable mActivatedBackgroundDrawable;    
    //private ColorStateList mSecondaryTextColor;
    
    private boolean mActivatedStateSupported;
    	
	private Rect mBoundsWithoutHeader = new Rect();
	
	protected final Context mContext;
	
	// Header layout data
    private boolean mHeaderVisible;
    private View mHeaderDivider;
    private int mHeaderBackgroundHeight;
    private TextView mHeaderTextView;
    
	// List item objects	
	private ViewGroup innerLayout;
    private int innerLayoutHeight;
    
    public SelectionItemView(Context context) {
    	this(context, null);
    }
    
    public SelectionItemView(Context context, AttributeSet attrs) {
    	this(context, attrs, 0);
    }
	
	public SelectionItemView(Context context, AttributeSet attrs, int layout) {
		super(context, attrs);
		
		this.mContext = context;
				
		// Read all style values
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ContactListItemView);
        
        mActivatedBackgroundDrawable = a.getDrawable(
                R.styleable.ContactListItemView_activated_background);
        mHorizontalDividerDrawable = a.getDrawable(
                R.styleable.ContactListItemView_list_item_divider);     
        
        mHeaderTextIndent = a.getDimensionPixelOffset(
                R.styleable.ContactListItemView_list_item_header_text_indent, 0);
        mHeaderTextColor = a.getColor(
                R.styleable.ContactListItemView_list_item_header_text_color, Color.BLACK);
        mHeaderTextSize = a.getDimensionPixelSize(
                R.styleable.ContactListItemView_list_item_header_text_size, 12);
        mHeaderBackgroundHeight = a.getDimensionPixelSize(
                R.styleable.ContactListItemView_list_item_header_height, 30);
        mHeaderUnderlineHeight = a.getDimensionPixelSize(
                R.styleable.ContactListItemView_list_item_header_underline_height, 1);
        mHeaderUnderlineColor = a.getColor(
                R.styleable.ContactListItemView_list_item_header_underline_color, 0);
        
		this.mPreferredHeight = 0;
		this.mTextIndent = 0;
		
//	    this.mVerticalDividerMargin = 0;
//	    this.mCountViewTextSize = 0;
//	    this.mCountViewTextColor = 0;	    
	    
		if (this.mHorizontalDividerDrawable != null) {
			this.mHorizontalDividerHeight = this.mHorizontalDividerDrawable.getIntrinsicHeight();
			this.mHorizontalDividerVisible = true;
		}
	    
	    if (mActivatedBackgroundDrawable != null) {
            mActivatedBackgroundDrawable.setCallback(this);
	    }
	    
	    //Force ActivatedStateSupport to true
	    this.setActivatedStateSupported(true);
	    
	    if (layout != 0) {
	        this.innerLayout = (ViewGroup) inflate(context, layout, null);
		    this.addView(this.innerLayout);
	    }
	}
	
	protected ViewGroup getInnerLayout(){
		return this.innerLayout;
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // We will match parent's width and wrap content vertically, but make sure
        // height is no less than listPreferredItemHeight.
        final int specWidth = resolveSize(0, widthMeasureSpec);
        final int preferredHeight;
        
        if (mHorizontalDividerVisible) {
            preferredHeight = mPreferredHeight + mHorizontalDividerHeight;
        } else {
            preferredHeight = mPreferredHeight;
        }

        innerLayoutHeight = 0;

        // Width each TextView is able to use.
        final int effectiveWidth;

        effectiveWidth = specWidth - getPaddingLeft() - getPaddingRight();

        // Go over all visible text views and measure actual width of each of them.
        // Also calculate their heights to get the total height for this entire view.

        if (isVisible(this.innerLayout)){
        	this.innerLayout.measure(
                MeasureSpec.makeMeasureSpec(effectiveWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        	this.innerLayoutHeight = this.innerLayout.getMeasuredHeight();
        }
        
        // Calculate height including padding.
        int height = this.innerLayoutHeight;

        // Make sure the height is at least as high as the photo
        height = Math.max(height, getPaddingBottom() + getPaddingTop());

        // Add horizontal divider height
        if (mHorizontalDividerVisible) {
            height += mHorizontalDividerHeight;
        }

        // Make sure height is at least the preferred height
        height = Math.max(height, preferredHeight);

        // Add the height of the header if visible
        if (mHeaderVisible) {
            mHeaderTextView.measure(
                    MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mHeaderBackgroundHeight, MeasureSpec.EXACTLY));
//            if (mCountView != null) {
//                mCountView.measure(
//                        MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.AT_MOST),
//                        MeasureSpec.makeMeasureSpec(mHeaderBackgroundHeight, MeasureSpec.EXACTLY));
//            }
            mHeaderBackgroundHeight = Math.max(mHeaderBackgroundHeight,
                    mHeaderTextView.getMeasuredHeight());
            height += (mHeaderBackgroundHeight + mHeaderUnderlineHeight);
        }

        setMeasuredDimension(specWidth, height);
    }
	
	@Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int height = bottom - top;
        final int width = right - left;

        // Determine the vertical bounds by laying out the header first.
        int topBound = 0;
        int bottomBound = height;
        int leftBound = getPaddingLeft();
        int rightBound = width - getPaddingRight();

        // Put the header in the top of the contact view (Text + underline view)
        if (mHeaderVisible) {
            mHeaderTextView.layout(leftBound + mHeaderTextIndent,
                    0,
                    rightBound,
                    mHeaderBackgroundHeight);
//            if (mCountView != null) {
//                mCountView.layout(rightBound - mCountView.getMeasuredWidth(),
//                        0,
//                        rightBound,
//                        mHeaderBackgroundHeight);
//            }
            mHeaderDivider.layout(leftBound,
                    mHeaderBackgroundHeight,
                    rightBound,
                    mHeaderBackgroundHeight + mHeaderUnderlineHeight);
            topBound += (mHeaderBackgroundHeight + mHeaderUnderlineHeight);
        }

        // Put horizontal divider at the bottom
        if (mHorizontalDividerVisible) {
            mHorizontalDividerDrawable.setBounds(
                    leftBound,
                    height - mHorizontalDividerHeight,
                    rightBound,
                    height);
            bottomBound -= mHorizontalDividerHeight;
        }

        mBoundsWithoutHeader.set(0, topBound, width, bottomBound);

        if (mActivatedStateSupported /*&& isActivated()*/) {
            mActivatedBackgroundDrawable.setBounds(mBoundsWithoutHeader);
        }

        // Add indent between left-most padding and texts.
        leftBound += mTextIndent;

        // Layout the call button.
//        mVerticalDividerVisible = false;

        // Center text vertically
        final int totalTextHeight = this.innerLayoutHeight;
        
        int textTopBound = (bottomBound + topBound - totalTextHeight) / 2;

        if (isVisible(this.innerLayout)) {
        	this.innerLayout.layout(leftBound,
                    textTopBound,
                    rightBound,
                    textTopBound + this.innerLayoutHeight);
            textTopBound += this.innerLayoutHeight;
        }
    }
	
	/**
     * Sets section header or makes it invisible if the title is null.
     */
    public void setSectionHeader(String title) {
        if (!TextUtils.isEmpty(title)) {
            if (mHeaderTextView == null) {
                mHeaderTextView = new TextView(mContext);
                mHeaderTextView.setTextColor(mHeaderTextColor);
                mHeaderTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHeaderTextSize);
                mHeaderTextView.setTypeface(mHeaderTextView.getTypeface(), Typeface.BOLD);
                mHeaderTextView.setGravity(Gravity.CENTER_VERTICAL);
                addView(mHeaderTextView);
            }
            if (mHeaderDivider == null) {
                mHeaderDivider = new View(mContext);
                mHeaderDivider.setBackgroundColor(mHeaderUnderlineColor);
                addView(mHeaderDivider);
            }
            setMarqueeText(mHeaderTextView, title);
            mHeaderTextView.setVisibility(View.VISIBLE);
            mHeaderDivider.setVisibility(View.VISIBLE);
            
            if (ComponentUtils.isIceCreamSandwich())
            	mHeaderTextView.setAllCaps(true);
            
            mHeaderVisible = true;
        } else {
            if (mHeaderTextView != null) {
                mHeaderTextView.setVisibility(View.GONE);
            }
            if (mHeaderDivider != null) {
                mHeaderDivider.setVisibility(View.GONE);
            }
            mHeaderVisible = false;
        }
    }
    
	@Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mActivatedStateSupported) {
            mActivatedBackgroundDrawable.setState(getDrawableState());
        }
    }
	
	@Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mActivatedBackgroundDrawable || super.verifyDrawable(who);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (mActivatedStateSupported) {
            mActivatedBackgroundDrawable.jumpToCurrentState();
        }
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if (mActivatedStateSupported && isActivated()) {
            mActivatedBackgroundDrawable.draw(canvas);
        }
        if (mHorizontalDividerVisible) {
            mHorizontalDividerDrawable.draw(canvas);
        }
//        if (mVerticalDividerVisible) {
//            mVerticalDividerDrawable.draw(canvas);
//        }

        super.dispatchDraw(canvas);
    }
    
    public void setDividerVisible(boolean visible) {
        mHorizontalDividerVisible = visible && this.mHorizontalDividerDrawable != null;
    }

    @Override
    public void requestLayout() {
        // We will assume that once measured this will not need to resize
        // itself, so there is no need to pass the layout request to the parent
        // view (ListView).
        forceLayout();
    }
    
    protected boolean isVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }
	
	/*@Override
	public void adjustListItemSelectionBounds(Rect bounds) {
		bounds.top += mBoundsWithoutHeader.top;
	    bounds.bottom = bounds.top + mBoundsWithoutHeader.height();
	    bounds.left += mSelectionBoundsMarginLeft;
	    bounds.right -= mSelectionBoundsMarginRight;
	}*/
	
	private TruncateAt getTextEllipsis() {
        return TruncateAt.MARQUEE;
    }
	
	public void setActivatedStateSupported(boolean flag) {
		if (this.mActivatedBackgroundDrawable != null)
			this.mActivatedStateSupported = flag;
    }
	
	protected void setMarqueeText(TextView textView, CharSequence text) {
        if (getTextEllipsis() == TruncateAt.MARQUEE) {
            // To show MARQUEE correctly (with END effect during non-active state), we need
            // to build Spanned with MARQUEE in addition to TextView's ellipsize setting.
            final SpannableString spannable = new SpannableString(text);
            spannable.setSpan(TruncateAt.MARQUEE, 0, spannable.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannable);
        } else {
            textView.setText(text);
        }
    }
	
	public void setSelectionBoundsHorizontalMargin(int left, int right) {
        mSelectionBoundsMarginLeft = left;
        mSelectionBoundsMarginRight = right;
    }
}
