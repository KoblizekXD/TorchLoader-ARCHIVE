package io.github.koblizekxd.torch.tasks

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.koblizekxd.torch.TorchPlugin
import io.github.koblizekxd.torch.util.Download
import org.apache.commons.lang3.SystemUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GradleDownloadMinecraft : DefaultTask() {
    private lateinit var json: JsonObject

    init {
        group = "torch"
        description = "Downloads main minecraft jar and json file through gradle"
        dependsOn.add(TorchPlugin.downloadMinecraftJson)
    }
    @TaskAction
    private fun downloadSource() {
        json = Gson().fromJson(File(temporaryDir.path + "/minecraft-json.json").readText(), JsonObject::class.java)
        println("[Torch] Downloading minecraft client jar...")
        val url = json.getAsJsonObject("downloads")
            .getAsJsonObject("client")
            .getAsJsonPrimitive("url")
            .asString
        Download(url, temporaryDir, "client.jar")
        println("[Torch] Finished downloading minecraft client jar")
        downloadLibraries()
    }
    private fun canUse(json: JsonObject): Boolean {
        val os = json.getAsJsonArray("rules") ?: return true
        val osname = os[0].asJsonObject.getAsJsonObject("os")
            .getAsJsonPrimitive("name").asString
        return if (SystemUtils.IS_OS_WINDOWS && osname == "windows") {
            true
        } else if (SystemUtils.IS_OS_LINUX && osname == "linux") {
            true
        } else SystemUtils.IS_OS_MAC && osname == "osx"
    }
    private fun isNative(json: JsonObject): Boolean {
        return json.has("rules")
    }
    private fun downloadLibraries() {
        println("[Torch] Preparing to download libraries...")
        val libraries = json.getAsJsonArray("libraries")

        for (i in 0 until libraries.size()) {
            val url = libraries[i].asJsonObject.getAsJsonPrimitive("name")
                .asString
            if (!canUse(libraries[i].asJsonObject)) continue
            println("[Torch] Downloading ${libraries[i].asJsonObject.getAsJsonPrimitive("name").asString}...")
            TorchPlugin.dependencies[url] = isNative(libraries[i].asJsonObject)
            println("[Torch] Done")
        }
    }
}