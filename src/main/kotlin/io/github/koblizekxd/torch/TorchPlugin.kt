package io.github.koblizekxd.torch

import io.github.koblizekxd.torch.minecraft.MinecraftProject
import io.github.koblizekxd.torch.tasks.DownloadMinecraft
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.com.google.api.client.json.Json

class TorchPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.create("downloadMinecraft", DownloadMinecraft::class.java)
    }
    fun minecraft(proj: MinecraftProject.() -> Unit) {
        val mc = MinecraftProject()
        proj(mc)
        val x: Json
        // Stuff is stored in the mc variable
    }
}