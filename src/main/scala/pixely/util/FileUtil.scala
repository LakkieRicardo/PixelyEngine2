package pixely.util

import scala.io._
import java.io.InputStream

object FileUtil {

    def readFileText(fileLocation: String): String = {
        var result = ""
        Source.fromFile(fileLocation).getLines().foreach{(line) => {
            result += line + "\n"
        }}
        return result
    }

    def readResourceText(resourceName: String): String = {
        var result = ""
        Source.fromResource(resourceName).getLines().foreach{(line) => {
            result += line + "\n"
        }}
        return result
    }

}