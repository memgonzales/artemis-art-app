//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[MainActivity](index.md)/[onWindowFocusChanged](on-window-focus-changed.md)

# onWindowFocusChanged

[androidJvm]\
open override fun [onWindowFocusChanged](on-window-focus-changed.md)(hasFocus: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Called when the current Window of the activity gains or loses focus.

Since the splash screen features an animated logo, this function is overridden to keep track of when the splash screen activity gains focus (that is, upon launching) and subsequently trigger the animation of the logo.

## Parameters

androidJvm

| | |
|---|---|
| hasFocus | whether the window of this activity has focus |
