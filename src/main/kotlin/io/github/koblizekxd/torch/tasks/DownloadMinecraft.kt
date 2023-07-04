package io.github.koblizekxd.torch.tasks

import io.github.koblizekxd.torch.TorchPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

abstract class DownloadMinecraft : DefaultTask() {
    @TaskAction
    fun downloadSource() {

    }
    @TaskAction
    fun downloadJsonSource() {
        lateinit var url: String

        if (TorchPlugin.version == "1.19.2") {
            url = "https://piston-meta.mojang.com/v1/packages/ed548106acf3ac7e8205a6ee8fd2710facfa164f/1.19.2.json"
        } else throw RuntimeException("Unsupported version or null: ${TorchPlugin.version}")


    }

}