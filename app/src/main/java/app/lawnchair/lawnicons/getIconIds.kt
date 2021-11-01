package app.lawnchair.lawnicons

import android.content.Context
import org.xmlpull.v1.XmlPullParser

fun getIconIds(context: Context): List<Int> {
    val iconIds = mutableListOf<Int>()
    val resources = context.resources
    val packageName = context.packageName

    try {
        val xmlId = resources.getIdentifier("grayscale_icon_map", "xml", packageName)
        if (xmlId != 0) {
            val parser = resources.getXml(xmlId)
            val depth = parser.depth
            var type: Int
            while (
                (parser.next().also { type = it } != XmlPullParser.END_TAG || parser.depth > depth) &&
                type != XmlPullParser.END_DOCUMENT
            ) {
                if (type != XmlPullParser.START_TAG) continue
                if ("icon" == parser.name) {
                    val pkg = parser.getAttributeValue(null, "package")
                    val iconId = parser.getAttributeResourceValue(null, "drawable", 0)
                    if (iconId != 0 && pkg.isNotEmpty()) {
                        iconIds += iconId
                    }
                }
            }
        }
    } catch (e: Exception) { }

    return iconIds
}