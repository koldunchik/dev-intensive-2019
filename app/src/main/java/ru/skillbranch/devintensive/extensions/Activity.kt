import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.extensions.dp2px
import kotlin.math.roundToLong

fun Activity.isKeyboardClosed(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightChange = rootView.height - visibleBounds.height()
    val threshold = this.dp2px(50F).roundToLong()
    return heightChange <= threshold
}

fun Activity.isKeyboardOpen(): Boolean {
    return !this.isKeyboardClosed()
}

fun Activity.hideKeyboard(){
    val focus = this.currentFocus
    focus?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }
}