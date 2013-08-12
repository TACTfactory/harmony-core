<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
package ${project_namespace}.harmony.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnTouchListener;


public class PinchZoomImageView extends ImageView implements OnTouchListener {
	//private static final String TAG = "PinchZoomImageView";
	
	@SuppressWarnings("unused")
	private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;
	private static final int MAX_SCALE_FACTOR = 3;

    // The 3 states (events) which the user is trying to perform
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int currentMode = NONE;
    
 	// These PointF objects are used to record the point(s) the user is touching
 	private PointF currentMidPoint = new PointF();

 	private float minScale;
 	private float maxScale;
 	private boolean wasScaleTypeSet;
 	
 	private float lastEventX = 0f;
 	private float lastEventY = 0f;
 	
 	private float lastDist = 0f;
 	
 	private float minX = 0f;
 	private float minY = 0f;
 	private float maxX = 0f;
 	private float maxY = 0f;
 	
 	
    
	public PinchZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initializeComponent();
	}
	
	public PinchZoomImageView(Context context, AttributeSet attrs, 
			int defStyle) {
		super(context, attrs, defStyle);
		this.initializeComponent();
	}
    
	public PinchZoomImageView(Context context) {
		super(context);
		this.initializeComponent();
	}	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    
		this.maxX = this.getRight();
		this.maxY = this.getHeight();
		
	    float initialScale = getMatrixScale(this.getImageMatrix());

	    if (initialScale < 1.0f) {// Image is bigger than screen
	        maxScale = MAX_SCALE_FACTOR;
	    } else {
	        maxScale = MAX_SCALE_FACTOR * initialScale;
	    }
	    
	    minScale = initialScale;
	    
	    this.maxX = this.getWidth();
	    this.maxY = this.getHeight();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// The new matrix;
		Matrix newMatrix = new Matrix();
		newMatrix.set(this.getImageMatrix());
		
		
		//newMatrix.set(this.defaultMatrix);
	    ImageView view = (ImageView) v;
	    // We set scale only after onMeasure was called 
	    // and automatically fit image to screen
	    if (!wasScaleTypeSet) {
	        view.setScaleType(ImageView.ScaleType.MATRIX);
	        wasScaleTypeSet = true;
	    }

	    float scale;
	    //this.dumpEvent(event);

	    switch (event.getAction() & MotionEvent.ACTION_MASK) {
		    case MotionEvent.ACTION_DOWN:
		        currentMode = DRAG;
		        break;
		        
		    // first and second finger down
		    case MotionEvent.ACTION_POINTER_DOWN: 
		    	midPoint(this.currentMidPoint, event);
		    	currentMode = ZOOM;
		        break;
	
		    
		    case MotionEvent.ACTION_MOVE:
		        if (currentMode == DRAG) {
		        	float movX = (event.getX() - lastEventX);
	        		float movY = (event.getY() - lastEventY);
	        		
	        		newMatrix.postTranslate(movX , 
	        				movY);
	        	    view.setImageMatrix(newMatrix);
			            
		        } else if (currentMode == ZOOM) {
		            // pinch zooming
		            float newDist = this.spacing(event);
		            
		            scale = newDist / this.lastDist;
	            	
		            newMatrix.postScale(
		            		scale,
		            		scale,
		            		this.currentMidPoint.x,
		            		this.currentMidPoint.y);
		            
		            view.setImageMatrix(newMatrix);
		        }
		        break;
		        
		    case MotionEvent.ACTION_UP: // first finger lifted
		    case MotionEvent.ACTION_POINTER_UP: // second finger lifted
		        currentMode = NONE;
		        
		        float currentScale = this.getMatrixScale(newMatrix);
		        
		        if (currentScale > this.maxScale) {
		        	newMatrix.postScale(
		        			this.maxScale / currentScale,
		        			this.maxScale / currentScale,
		        			this.currentMidPoint.x ,
		        			this.currentMidPoint.y);
		        } else if (currentScale < this.minScale) {
		        	newMatrix.postScale(
		        			this.minScale / currentScale,
		        			this.minScale / currentScale,
		        			this.currentMidPoint.x ,
		        			this.currentMidPoint.y);
		        }
		        
		        float width =
		        		currentScale * this.getDrawable().getIntrinsicWidth();
		        float height =
		        		currentScale * this.getDrawable().getIntrinsicHeight();
		        
		        float currentLeft = this.getMatrixXTrans(newMatrix);
		        float currentTop = this.getMatrixYTrans(newMatrix);
		        float currentRight = currentLeft + width;
		        float currentBottom = currentTop + height;
		        
		        if (width < (this.maxX - this.minX)) {
		        	float newXLeft =
		        			((this.maxX - this.minX) / 2) - (width / 2);
		        	newMatrix.postTranslate(newXLeft - currentLeft, 0);
		        } else if (currentLeft > this.minX) {
		        	newMatrix.postTranslate(this.minX - currentLeft, 0);
		        } else if (currentRight < this.maxX) {
		        	newMatrix.postTranslate(this.maxX - currentRight, 0);
		        }
		        
		        if (height < (this.maxY - this.minY)) {
		        	float newYTop =
		        			((this.maxY - this.minY) / 2) - (height / 2);
		        	newMatrix.postTranslate(0, newYTop - currentTop);
		        } else if (currentTop > this.minY) {
		        	newMatrix.postTranslate(0, this.minY - currentTop);
		        } else if (currentBottom < this.maxY) {
		        	newMatrix.postTranslate(0, this.maxY - currentBottom);
		        }
		        
			    view.setImageMatrix(newMatrix);
		        
		        break;
	    }
	    
	    if (currentMode == ZOOM) {
	    	this.lastDist = this.spacing(event);
	    }
	    this.lastEventX = event.getX();
	    this.lastEventY = event.getY();

	    return true;

	}
	
	/**
	 * Returns scale factor of the Matrix
	 * @param matrix
	 * @return
	 */
	private float getMatrixScale(Matrix matrix) {
		float[] tmpValues = new float[9];
	    matrix.getValues(tmpValues);
	    return tmpValues[Matrix.MSCALE_X];
	}
	
	private float getMatrixXTrans(Matrix matrix) {
		float[] tmpValues = new float[9];
		matrix.getValues(tmpValues);
		return tmpValues[Matrix.MTRANS_X];
	}
	
	private float getMatrixYTrans(Matrix matrix) {
		float[] tmpValues = new float[9];
		matrix.getValues(tmpValues);
		return tmpValues[Matrix.MTRANS_Y];
	}
	
	/**
	 * Method: spacing Parameters: MotionEvent Returns: float Description:
	 * checks the spacing between the two fingers on touch
	 */
	@SuppressLint("FloatMath")
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
  	 * Description: calculates the midpoint between the two fingers
  	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
  
	private void initializeComponent() {
		// Enable touchable image (zoom, drag, ...) :
		//this.setOnTouchListener(this);
	}
}

