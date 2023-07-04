package io.github.koblizekxd.torch

import io.github.koblizekxd.torch.minecraft.MinecraftProject
import io.github.koblizekxd.torch.tasks.DownloadMinecraft
import org.gradle.api.Plugin
import org.gradle.api.Project

class TorchPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.group = "torch"
        project.tasks.create("downloadMinecraft", DownloadMinecraft::class.java)
    }
    fun minecraft(proj: MinecraftProject.() -> Unit) {
        val mc = MinecraftProject()
        proj(mc)
        // Stuff is stored in the mc variable
        mappings = mc.mappings
        version = mc.version
    }

    companion object {
        lateinit var mappings: String
        lateinit var version: String
    }
}