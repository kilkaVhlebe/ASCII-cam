#ASCII art converter project
##Description
It's my test project for my college and training ASCII converting for my future project.
##Installation
```git clone https://github.com/tugaserya/ASCII-cam.git```
Waiting for build.
##Running the app
I developing this app on Android Studio, so i just use emulator and my own phone.
##Code explanation
Here I will describe only the mechanism for converting an image to ASCII art and a few related things.
The original image is scaled across the phone screen to values suitable for display (the divisors are selected by enumerating the values):
```
val displayMetrics = DisplayMetrics()

windowManager.defaultDisplay.getMetrics(displayMetrics)
val height = displayMetrics.heightPixels
val width = displayMetrics.widthPixels

val resizeBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, width/20, height/17, false)
val grayscaleBitmap: Bitmap = imageConverter.setGrayscaleBitmap(resizeBitmap)
```
then it is colored in gray tones to increase contrast:
```
fun setGrayscaleBitmap(bitmap: Bitmap): Bitmap {
        val grayscaleBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()

        paint.setColorFilter(filter)
        val canvas = Canvas(grayscaleBitmap)
        canvas.drawBitmap(grayscaleBitmap, 0f, 0f, paint)

        return grayscaleBitmap
    }
```
Then a matrix is created in which one character is equal to one pixel. Symbols are selected based on the average pixel brightness:
```
fun convertToASCII(bitmap: Bitmap): Array<Array<String>> {
        val width = bitmap.width
        val height = bitmap.height
        val asciiArt = Array(height) { Array(width) { "" } }
        val symbolsArr = arrayOf("@", "#", "S", "%", "?", "*", "+", ";", ":", ",", ".")

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = bitmap.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)
                when ((blue + green + red) / 3.0) {
                    in 0.0..23.18 -> asciiArt[y][x] = symbolsArr[0]
                    in 23.19..46.3618 -> asciiArt[y][x] = symbolsArr[1]
                    in 46.3619..69.5418 -> asciiArt[y][x] = symbolsArr[2]
                    in 69.5418..92.7218 -> asciiArt[y][x] = symbolsArr[3]
                    in 92.7219..115.9018 -> asciiArt[y][x] = symbolsArr[4]
                    in 115.9019..139.0818 -> asciiArt[y][x] = symbolsArr[5]
                    in 139.0819..162.2618 -> asciiArt[y][x] = symbolsArr[6]
                    in 162.2619..185.4418 -> asciiArt[y][x] = symbolsArr[7]
                    in 185.4419..208.6218 -> asciiArt[y][x] = symbolsArr[8]
                    in 208.6219..231.8018 -> asciiArt[y][x] = symbolsArr[9]
                    in 231.8019..255.0 -> asciiArt[y][x] = symbolsArr[10]
                }
            }
        }

        return asciiArt
    }
```
This is actually all the magic.
#P.S
I know that there are other more complex methods, but they are not suitable for my tasks in the future.
If you have a new ideas how to improve this project, i would like to see your ideas.

![img.png](img.png)
(not from my app)
