import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view: View? = this.currentFocus
    if (view != null) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardClosed(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightChange = rootView.height - visibleBounds.height()
    val threshold = dp2px(50F, this)
    return heightChange <= threshold
}

fun Activity.isKeyboardOpen(): Boolean {
    return !this.isKeyboardClosed()
}

fun dp2px(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}