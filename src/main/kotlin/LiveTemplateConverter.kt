import com.google.gson.Gson
import java.io.File
import java.nio.charset.StandardCharsets

/**
 * generate the (static) live templates,
 *
 * convert the abbreviations.json etc. file adapted from vscode to
 * the xml file used in intellij idea's live template system,
 * ref: https://plugins.jetbrains.com/docs/intellij/providing-live-templates.html
 *
 * the input source mainly comes from
 * https://github.com/leanprover/vscode-lean4/blob/master/lean4-unicode-input/src/abbreviations.json
 * and some adjust for inputting more easily in intellij idea.
 */
class LiveTemplateConverter(private val basePath: String, fileName: String) {

    private val source: String = "$basePath/src/main/resources/liveTemplates/$fileName"

    fun generate(space: Boolean = false, context: String = "Lean4", output: String = "Lean4.xml") {
        println("generating $source with space: $space, context: $context")
        val abbrev: Map<String, String> = fromJson(File(source).readText(StandardCharsets.UTF_8))
        val tplSet = generateContent(abbrev, space, context)
        output(tplSet, output, context)
    }

    private fun generateContent(
        abbrev: Map<String, String>,
        space: Boolean = false,
        context: String = "Lean4"
    ): List<String> {
        val prefix = "\\"
        val tplSet = mutableListOf<String>()

        for ((k0, v0) in abbrev) {
            // since we cannot expand the live template automatically, we do not need to handle this double backslash
            // though sometimes we do want it automatically expanded...
            if (k0 == "\\") continue

            var v = v0
            var d = v

            if (v.contains("\$CURSOR")) {
                v = v.replace("\$CURSOR", "\$END\$")
                d = d.replace("\$CURSOR", "")
            }

            if (space) {
                v += " "
                d += " with space"
            }

            val k = prefix + escapeXml(k0)
            val tpl =
                """    <template name="$k" value="$v" shortcut="SPACE" description="$d" toReformat="false" toShortenFQNames="true">
        <context>
          <option name="$context" value="true" />
        </context>
      </template>"""
            tplSet.add(tpl)
        }

        return tplSet
    }

    private fun output(tplSet: List<String>, fileName: String = "Lean4.xml", group: String = "Lean4") {
        val fileContent = """<templateSet group="$group">
${tplSet.joinToString("\n")}
</templateSet>
        """

        val filePath = "$basePath/src/main/resources/liveTemplates/$fileName"
        File(filePath).writeText(fileContent, Charsets.UTF_8)
    }

    private fun escapeXml(input: String): String {
        return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            // TODO this should be escape but the original python script does not do it, weird
            //.replace(">", "&gt;")
            .replace("\"", "&quot;")
        // TODO this should be escape but the original python script does not do it, weird
        //.replace("'", "&apos;")
    }
}

inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}
